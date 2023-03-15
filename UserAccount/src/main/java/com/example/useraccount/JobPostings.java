package com.example.useraccount;

public class JobPostings {
    private String title;
    private String description;
    private String company;

    public JobPostings(){
        title = "";
        description = "";
        company = "";
    }
    public JobPostings(String title, String description, String company){
        this.title = title;
        this.description = description;
        this.company = company;
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

    public String getTitle(){
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany() {
        return company;
    }
}
