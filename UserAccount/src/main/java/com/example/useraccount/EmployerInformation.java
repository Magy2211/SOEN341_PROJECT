package com.example.useraccount;

/*
 * The purpose of this class is to hold the employer information
 */
public class EmployerInformation {

    private String firstName;
    private String lastName;
    private String company;
    private String email;

    public EmployerInformation() {
        firstName = "";
        lastName = "";
        company = "";
        email = "";
    }

    public EmployerInformation(String firstName, String lastName, String company, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.email = email;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
