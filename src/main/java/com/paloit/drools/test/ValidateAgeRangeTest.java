package com.paloit.drools.test;

import com.paloit.drools.model.Proposal;
import com.paloit.drools.model.Customer;
import com.paloit.drools.model.fact.ProposalCheckResult;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateAgeRangeTest {

    private KieSession kSession;

    @Before
    public void setup() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        kSession = kContainer.newKieSession();
    }

    @Test
    public void testUnderAge() {
        Proposal proposal = createProposalWithBirthDate(LocalDate.now().minusYears(17));

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        assertFalse(result.isValidAgeRange());
    }

    @Test
    public void testValidAge() {
        Proposal proposal = createProposalWithBirthDate(LocalDate.now().minusYears(25));

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        assertTrue(result.isValidAgeRange());
    }

    @Test
    public void testOverAge() {
        Proposal proposal = createProposalWithBirthDate(LocalDate.now().minusYears(70));

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        assertFalse(result.isValidAgeRange());
    }

    @Test
    public void testNullBirthDate() {
        Proposal proposal = createProposalWithBirthDate(null);

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        // Given the current rule, it doesn't address the null birth date scenario, so the result will be true.
        assertTrue(result.isValidAgeRange());
    }

    private Proposal createProposalWithBirthDate(LocalDate birthDate) {
        Proposal proposal = new Proposal();
        Customer customer = new Customer();
        if (birthDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String birthDateStr = formatter.format(birthDate);
            customer.setBirthDate(birthDateStr);
        }
        proposal.setCustomer(customer);
        return proposal;
    }
}
