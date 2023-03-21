package com.example.useraccount;

public class JobPostings {
    private String title;
    private String description;
    private String company;
    private int id;
    private String status;

    public JobPostings(){
        title = "";
        description = "";
        company = "";
        status = "";
        id = 0;
    }
    public JobPostings(int id, String title, String description, String company, String status){
        this.title = title;
        this.description = description;
        this.company = company;
        this.id = id;
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany() {
        return company;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
