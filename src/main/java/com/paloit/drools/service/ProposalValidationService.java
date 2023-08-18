package com.paloit.drools.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.util.List;

import com.paloit.drools.model.Customer;
import com.paloit.drools.model.Proposal;
import com.paloit.drools.model.Identity;
import com.paloit.drools.model.fact.ProposalCheckResult;

public class ProposalValidationService {

    public ProposalCheckResult validateProposal(Proposal proposal, ProposalCheckResult result ) {

        if (proposal.getCustomer() != null) {

            List<Identity> identities = proposal.getCustomer().getIdentity();

            if (identities != null) {
                for (Identity identity : identities) {
                    if ("CPF".equals(identity.getDocumentType())
                            && (identity.getDocumentNumber() == null
                            || identity.getDocumentNumber().length() != 11)) {
                        result.setValidDocumentNumber(false);
                        result.addValidationError("CPF document length not valid for identity: " + identity.getDocumentNumber());
                        break;
                    }
                }
            }

            int age = calculateAge(proposal.getCustomer().getBirthDate());
            if (age < 18 || age > 65) {
                result.setValidAgeRange(false);
                result.addValidationError("Customer age not within the valid range. Age: " + age);
            }

            if (proposal.getCustomer().getContact() != null && !isValidEmail(proposal.getCustomer().getContact().getEmail())) {
                result.setValidEmailFormat(false);
                result.addValidationError("Customer email format is invalid: " + proposal.getCustomer().getContact().getEmail());
            }
        }

        if ("002".equals(proposal.getProductCode()) && !"PROPOSAL_CARREFOUR".equals(proposal.getProposalType())) {
            result.setValidProductCode(false);
            result.addValidationError("Product code and proposal type mismatch.");
        }

        if (proposal.getStartsAtDate() != null && proposal.getEndsAtDate() != null) {
            LocalDate start = LocalDate.parse(proposal.getStartsAtDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            LocalDate end = LocalDate.parse(proposal.getEndsAtDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            if (start.isAfter(end)) {
                result.setValidProposal(false);
                result.addValidationError("Proposal start date is after the end date.");
            }
        }

        if (proposal.getCommission() != null && (proposal.getCommission().getPercentage() < 1 || proposal.getCommission().getPercentage() > 100)) {
            result.setValidProposal(false);
            result.addValidationError("Commission percentage not within the valid range. Percentage: " + proposal.getCommission().getPercentage());
        }

        if (result.isValidProductCode() && result.isValidEmailFormat() && result.isValidDocumentNumber() && result.isValidAgeRange()) {
            result.setValidProposal(true);
        }

        return result;
    }

    private int calculateAge(String birthDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("(?i)^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,4}$");
    }
}
