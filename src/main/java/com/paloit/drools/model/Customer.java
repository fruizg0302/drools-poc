package com.paloit.drools.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class Customer {
    private String fullName;
    private String birthDate;
    private String nationality;
    private String maritalStatus;
    private String gender;
    private String occupation;
    private Address address;
    private Contact contact;
    private List<Identity> identity;  // Make sure to import the new Identity class
    private List<DpsCustomer> dpsCustomer;

    @Getter
    @Setter
    @ToString
    public static class Phone {
        private int ddd;
        private String number;
        private String type;
    }

    @Getter
    @Setter
    @ToString
    public static class DpsCustomer {
        private String question;
        private String answer;
        private String describedAnswer;
    }
}
