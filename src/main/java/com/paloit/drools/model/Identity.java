package com.paloit.drools.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Identity {
    private String documentNumber;
    private String documentType;
    private String issuingAuthority;
    private String issueDate;

    @Override
    public String toString() {
        return "Identity{" +
                "documentNumber='" + documentNumber + '\'' +
                ", documentType='" + documentType + '\'' +
                '}';
    }
}
