package com.paloit.drools.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {

	private String street;
	private String number;
	private String city;
	private String postalCode;
	private String country;
	private String additionalInformation;
	private String neighborhood;
	private String state;

}

