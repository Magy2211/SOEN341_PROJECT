package com.example.useraccount;

import java.time.*;

public class JobPostings {
    private String title;
    private String description;
    private String company;
    private int id;
    private String status;
    
    private String salary;
    private String deadline;
    private String jobLocation;

    public JobPostings(){
        title = "";
        description = "";
        company = "";
        status = "";
        id = 0;
        salary = "";
        deadline = "";
        jobLocation = "";;
    }
    public JobPostings(int id, String title, String description, String company, String status, 
    		String salary, String deadline, String jobLocation){
        this.title = title;
        this.description = description;
        this.company = company;
        this.id = id;
        this.status = status;
        this.salary = salary;
        this.deadline = deadline;
        this.jobLocation = jobLocation;
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
