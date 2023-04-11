package com.example.useraccount;

import java.util.LinkedList;
import java.util.List;

/*
 * The purpose of this class is to hold the admin information
 */
public class AdminInformation {

    // Authentication code to allow admin profile creation
    public static final String AUTHENTICATION_CODE = "qwerty1234";
    
    // Admin profile permissions
    public static final String USER_PROFILE_ACCESS = "Access all user profile information";

	public static final String USER_FEEDBACK_ACCESS = "Access user feedback forms";
    
    public static final String DELETE_USER_PROFILE = "Delete user profiles";
    
    public static final String DELETE_JOB_POSTING = "Delete job postings";
    
    // List of permissions
    List<String> permissions;
    
    // Personal information
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public AdminInformation() {
        firstName = "";
        lastName = "";
        email = "";
        role = "";
        permissions = new LinkedList<String>();
    }


    public AdminInformation(String firstName, String lastName, String email, String role) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        
        permissions = new LinkedList<String>();
        
        try {
			initializePermissions(role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    
    public String getAuthenticationCode() {
        return AUTHENTICATION_CODE;
    }
    
    public static String getUserProfileAccess() {
 		return USER_PROFILE_ACCESS;
 	}


 	public static String getUserFeedbackAccess() {
 		return USER_FEEDBACK_ACCESS;
 	}


 	public static String getDeleteUserProfile() {
 		return DELETE_USER_PROFILE;
 	}


 	public static String getDeleteJobPosting() {
 		return DELETE_JOB_POSTING;
 	}
 	
    /*
     * Allow to display the list of permissions in a column
     */
    public String permissionsToString() {
        return String.join(System.lineSeparator(), permissions);
    }
    
    /*
     * Initialize the permissions given to a role
     */
    public void initializePermissions(String role) throws Exception {
    	if(role.equals("Administrator")) {
    		 permissions.add(USER_PROFILE_ACCESS);
    	     permissions.add(USER_FEEDBACK_ACCESS);
    	     permissions.add(DELETE_USER_PROFILE);
    	     permissions.add(DELETE_JOB_POSTING);
    	}
    	else
    		throw new Exception("Specified administrator role does not match 'Administrator' ");
    }  
}
