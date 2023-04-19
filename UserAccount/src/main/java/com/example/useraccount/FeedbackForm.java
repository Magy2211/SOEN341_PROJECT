package com.example.useraccount;

public class FeedbackForm {
	
	private String subject;
	private int rating;
	
	public FeedbackForm() {
		super();
		this.subject = "";
		this.rating = -1;
	}
	
	public FeedbackForm(String subject, int rating) {
		super();
		this.subject = subject;
		this.rating = rating;
	}
	
	public String getSubject() {
		return subject;
	}
	public int getRating() {
		return rating;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	

}
