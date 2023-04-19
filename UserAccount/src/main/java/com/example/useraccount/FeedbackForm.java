package com.example.useraccount;

public class FeedbackForm {

    private String subject;
    private int rating;
    private String email;

    public FeedbackForm() {
        super();
        this.subject = "";
        this.rating = -1;
        this.email = "";
    }

    public FeedbackForm(String subject, int rating, String email) {
        super();
        this.subject = subject;
        this.rating = rating;
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
