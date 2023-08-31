package com.paloit.drools.model.fact;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ProposalCheckResult {

    private boolean validProductCode = true;
    private boolean validEmailFormat = true;
    private boolean validDocumentNumber = true;
    private boolean validAgeRange = true;
    private boolean validProposalType = true;
    private boolean validSalesChannel = true;
    private boolean validProposal = true;
    private boolean validAttempts = true;

    private final List<String> validationErrors = new ArrayList<>();

    public void addValidationError(String error) {
        validationErrors.add(error);
    }

    public boolean validProposalAttributes() {
        return validProductCode &&
                validEmailFormat &&
                validDocumentNumber &&
                validAgeRange &&
                validProposalType &&
                validSalesChannel &&
                validProposal &&
                validAttempts;
    }
}
