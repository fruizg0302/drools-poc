package com.paloit.drools.model.fact;

import java.util.ArrayList;
import java.util.List;

public class ProposalCheckResult {

    private boolean validProductCode = true; // Example: true if the product code matches certain criteria
    private boolean validEmailFormat = true; // Example: true if the email format is correct
    private boolean validDocumentNumber = true; // Example: true if the document number matches a certain pattern or is not a duplicate
    private boolean validAgeRange = true; // Example: true if the customer's age falls within a certain range

    // Adding an overall validation flag
    private boolean validProposal = false; // true if all validations are successful
    private final List<String> validationErrors = new ArrayList<>();

    // Standard getters and setters for each attribute

    public boolean isValidProductCode() {
        return validProductCode;
    }

    public void setValidProductCode(boolean validProductCode) {
        this.validProductCode = validProductCode;
    }

    public boolean isValidEmailFormat() {
        return validEmailFormat;
    }

    public void setValidEmailFormat(boolean validEmailFormat) {
        this.validEmailFormat = validEmailFormat;
    }

    public boolean isValidDocumentNumber() {
        return validDocumentNumber;
    }

    public void setValidDocumentNumber(boolean validDocumentNumber) {
        this.validDocumentNumber = validDocumentNumber;
    }

    public boolean isValidAgeRange() {
        return validAgeRange;
    }

    public void setValidAgeRange(boolean validAgeRange) {
        this.validAgeRange = validAgeRange;
    }

    public boolean isValidProposal() {
        return validProposal;
    }

    public void setValidProposal(boolean validProposal) {
        this.validProposal = validProposal;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void addValidationError(String error) {
        validationErrors.add(error);
    }

    public boolean hasValidationErrors() {
        return !validationErrors.isEmpty();
    }
}
