package com.paloit.drools.controller;

import com.paloit.drools.model.Proposal;
import com.paloit.drools.model.fact.ProposalCheckResult;
import com.paloit.drools.service.ProposalValidationService;
import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.StringJoiner;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    private static final Logger log = LoggerFactory.getLogger(ProposalController.class);

    @Resource
    private KieContainer kieContainer;

    @PostMapping("/validate")
    public ResponseEntity<String> validateProposal(@RequestBody Proposal proposal) {

        KieSession kieSession = kieContainer.newKieSession();

        ProposalCheckResult result = new ProposalCheckResult();

        ProposalValidationService service = new ProposalValidationService();
        service.validateProposal(proposal, result);

        kieSession.insert(proposal);
        kieSession.insert(result);
        int ruleFiredCount = kieSession.fireAllRules();
        kieSession.dispose();

        if (result.isValidProposal()) {
            return ResponseEntity.ok(ruleFiredCount + " set rules fulfilled. Proposal is valid.");
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
