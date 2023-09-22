package com.paloit.drools.test;

import com.paloit.drools.model.Proposal;
import com.paloit.drools.model.Customer;
import com.paloit.drools.model.Contact;
import com.paloit.drools.model.fact.ProposalCheckResult;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateEmailFormatTest {

    private KieSession kSession;

    @Before
    public void setup() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        kSession = kContainer.newKieSession();
    }

    @Test
    public void testValidEmail() {
        Proposal proposal = createProposalWithCustomerEmail("test@example.com");

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        assertTrue(result.isValidEmailFormat());
    }

    @Test
    public void testEmptyEmail() {
        Proposal proposal = createProposalWithCustomerEmail("");

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        assertFalse(result.isValidEmailFormat());
    }

    @Test
    public void testWhitespaceEmail() {
        Proposal proposal = createProposalWithCustomerEmail("  ");

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        assertFalse(result.isValidEmailFormat());
    }

    @Test
    public void testInvalidEmailFormat() {
        Proposal proposal = createProposalWithCustomerEmail("invalid_email");

        ProposalCheckResult result = new ProposalCheckResult();
        kSession.insert(proposal);
        kSession.insert(result);

        kSession.fireAllRules();

        assertFalse(result.isValidEmailFormat());
    }

    private Proposal createProposalWithCustomerEmail(String email) {
        Proposal proposal = new Proposal();
        Customer customer = new Customer();
        Contact contact = new Contact();
        contact.setEmail(email);
        customer.setContact(contact);
        proposal.setCustomer(customer);
        return proposal;
    }
}
