package com.paloit.drools.test;

import com.paloit.drools.model.Proposal;
import com.paloit.drools.model.fact.ProposalCheckResult;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateProposalTypeTest {

    private KieSession kSession;


    @Before
    public void setup() {

        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        kSession = kContainer.newKieSession();
    }

    @Test
    public void testInvalidProposalType() {
        Proposal proposal = new Proposal();
        proposal.setProposalType("INVALID_TYPE");

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        assertFalse(result.isValidProposalType());
    }

    @Test
    public void testValidProposalType() {
        Proposal proposal = new Proposal();
        proposal.setProposalType("PROPOSAL_CARREFOUR");

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        assertTrue(result.isValidProposalType());
    }
}
