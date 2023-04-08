package com.example.useraccount;

/*
 * The purpose of this class is to hold the information
 * of the job posting
 */
public class JobPostings {
    private String employerFirstName;
    private String employerLastName;
    private String title;
    private String description;
    private String company;
    private int id;
    private String status;

    private String salary;
    private String deadline;
    private String jobLocation;

    public JobPostings() {
        employerFirstName = "";
        employerLastName = "";
        title = "";
        description = "";
        company = "";
        status = "";
        id = 0;
        salary = "";
        deadline = "";
        jobLocation = "";
    }

    public JobPostings(String employerFirstName, String employerLastName, int id, String title, String description, String company, String status,
                       String salary, String deadline, String jobLocation) {
        this.employerFirstName = employerFirstName;
        this.employerLastName = employerLastName;
        this.title = title;
        this.description = description;
        this.company = company;
        this.id = id;
        this.status = status;
        this.salary = salary;
        this.deadline = deadline;
        this.jobLocation = jobLocation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployerFirstName() {
        return employerFirstName;
    }

    public void setEmployerFirstName(String employerFirstName) {
        this.employerFirstName = employerFirstName;
    }

    public String getEmployerLastName() {
        return employerLastName;
    }

    public void setEmployerLastName(String employerLastName) {
        this.employerLastName = employerLastName;
    }
}
