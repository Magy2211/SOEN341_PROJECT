package com.example.useraccount;

import java.util.LinkedList;
import java.util.List;

public class AdminInformation {
	
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	List<String> permissions;
	
	public static final String AUTHENTICATION_CODE = "qwerty1234";
	
	public AdminInformation(String firstName, String lastName, String email, String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		permissions = new LinkedList<String>();
		permissions.add("Access all user profile information");
		permissions.add("Access user feedback forms");
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public List<String> getPermissions() {
		return permissions;
	}
	
	public String permissionsToString() {
		return String.join(System.lineSeparator(), permissions);
	}
	
	public String getAuthenticationCode() {
		return AUTHENTICATION_CODE;
	}
}
