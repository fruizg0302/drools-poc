package com.paloit.drools.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class Contact {
    private String email;
    private List<Phone> phoneList;

    @Getter
    @Setter
    @ToString
    public static class Phone {
        private int ddd;
        private String number;
        private String type;
    }
}