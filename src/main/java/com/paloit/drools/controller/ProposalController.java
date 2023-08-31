package com.paloit.drools.controller;

import com.paloit.drools.model.Proposal;
import com.paloit.drools.model.fact.ProposalCheckResult;
import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.drools.core.base.RuleNameMatchesAgendaFilter;
import org.kie.api.command.Command;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.internal.command.CommandFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    private static final Logger log = LoggerFactory.getLogger(ProposalController.class);

    @Resource
    private KieContainer kieContainer;

    @PostMapping("/validate")
    public ResponseEntity<String> validateProposal(@RequestBody Proposal proposal, @RequestParam(value = "rule", required = false) String regex) {

        KieSession kieSession = kieContainer.newKieSession();
        ProposalCheckResult result = new ProposalCheckResult();

        kieSession.insert(proposal);
        kieSession.insert(result);

        if (regex != null && !regex.isEmpty()) {
            RuleNameEqualsAgendaFilter agendaFilter = new RuleNameEqualsAgendaFilter(regex);
            kieSession.fireAllRules(agendaFilter);
        } else {
            kieSession.fireAllRules();
        }

        log.info("Result after rules fired: {}", result);

        kieSession.dispose();

        if (result.validProposalAttributes()) {
            return ResponseEntity.ok("Set rules fulfilled. Proposal is valid.");
        } else {
            StringJoiner sj = new StringJoiner("\n");
            sj.add("Proposal is invalid for the following reasons:");
            for (String error : result.getValidationErrors()) {
                sj.add("- " + error);
            }
            return ResponseEntity.badRequest().body(sj.toString());
        }
    }

}
