package com.paloit.drools.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@ToString
public class Proposal {

    private String registerType;
    private String productCode;
    private String proposalType;
    private String plan;
    private String contractNumber;
    private String saleDate;
    private BigDecimal premiumValue;
    private Store store;
    private String salesChannel;
    private List<Beneficiary> beneficiaries;
    private Commission commission;
    private Capitalization capitalization;
    private String startsAtDate;
    private String endsAtDate;
    private int installments;
    private Customer customer;
    private Address address;



    @Getter
    @Setter
    @ToString
    public static class Store {
        private int storeCode;
        private String storeId;
        private List<Seller> sellers;
    }

    @Getter
    @Setter
    @ToString
    public static class Seller {
        private String name;
        private String type;
        private List<Identity> identityList;
    }

    @Getter
    @Setter
    @ToString
    public static class Beneficiary {
        private String name;
        private List<Identity> identity;
        private String relationship;
        private int percentage;
        private Contact contacts;
    }

    @Getter
    @Setter
    @ToString
    public static class Contact {
        private String email;
        private List<Phone> phoneList;

    }

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
    public static class Commission {
        private int percentage;
        private BigDecimal value;
    }

    @Getter
    @Setter
    @ToString
    public static class Capitalization {
        private int batchNumber;
        private int luckyNumber;
        private int serialNumber;
        private BigDecimal amount;
        private String productKey;
    }
}
