package com.example.useraccount;

public class JobPostings {
    private String title;
    private String description;
    private String company;
    private int id;

    public JobPostings(){
        title = "";
        description = "";
        company = "";
    }
    public JobPostings(int id, String title, String description, String company){
        this.title = title;
        this.description = description;
        this.company = company;
        this.id = id;
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
}
