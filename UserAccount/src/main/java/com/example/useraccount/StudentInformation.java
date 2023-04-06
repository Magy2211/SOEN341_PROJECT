package com.example.useraccount;


public class StudentInformation {
    private String firstName;
    private String lastName;
    private String email;
    private String fieldOfStudy;
    private String resumeBase64;
    private String coverLetterBase64;
    private String transcriptBase64;
    private String profilePicture;

    public StudentInformation(){
        firstName = "";
        lastName = "";
        email = "";
        fieldOfStudy = "";
        resumeBase64 = "";
        transcriptBase64 = "";
        coverLetterBase64 = "";
        profilePicture = "";
    }
    
    public StudentInformation(String firstName, String lastName, String email, String fieldOfStudy){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fieldOfStudy = fieldOfStudy;
    }

    public StudentInformation(String firstName, String lastName, String email, String fieldOfStudy, String resumeBase64, String coverLetterBase64, String transcriptBase64, String profilePicture){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fieldOfStudy = fieldOfStudy;
        this.resumeBase64 = resumeBase64;
        this.transcriptBase64 = transcriptBase64;
        this.coverLetterBase64 = coverLetterBase64;
        this.profilePicture = profilePicture;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public void setResumeBase64(String resumeBase64) {
        this.resumeBase64 = resumeBase64;
    }

    public void setCoverLetterBase64(String coverLetterBase64) {
        this.coverLetterBase64 = coverLetterBase64;
    }

    public void setTranscriptBase64(String transcriptBase64) {
        this.transcriptBase64 = transcriptBase64;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public String getResumeBase64() {
        return resumeBase64;
    }

    public String getCoverLetterBase64() {
        return coverLetterBase64;
    }

    public String getTranscriptBase64() {
        return transcriptBase64;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
