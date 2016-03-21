package com.zmousa.androidbackup.model;

public class Contact {

	private String contactNumber;
	private String contactName;
	private String email;

	public Contact(String contactNumber, String contactName,String email) {
		this.contactNumber = contactNumber;
		this.contactName = contactName;
		this.email=email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

}