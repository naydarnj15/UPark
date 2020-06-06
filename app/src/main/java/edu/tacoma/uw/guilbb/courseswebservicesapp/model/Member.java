package edu.tacoma.uw.guilbb.courseswebservicesapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for building a member object to put into the database
 */
public class Member implements Serializable {

//    private String first_name;
//    private String last_name;
    private String email;
    private String password;
//    private String username;

//    public static final String FIRST_NAME = "first";
//    public static final String LAST_NAME = "last";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
//    public static final String USERNAME = "username";

    public Member(String theEmail, String thePassword){
        email = theEmail;
        password = thePassword;
    }

    public String getmEmail() {
        return email;
    }

//    public String getFirstName() {
//        return first_name;
//    }

//    public String getLastName() {
//        return last_name;
//    }

    public String getmPassword() {
        return password;
    }

//    public String getmUsername() {
//        return username;
//    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setFirstName(String first_name) {
//        this.first_name = first_name;
//    }

//    public void setLastName(String last_name) {
//        this.last_name = last_name;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public static List<Member> parseCourseJson(String memberJson) throws JSONException {
        List<Member> memberList = new ArrayList<>();
        if(memberJson != null){

            JSONArray arr = new JSONArray(memberJson);

            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Member member = new Member( obj.getString(Member.EMAIL), obj.getString(Member.PASSWORD));
                memberList.add(member);
            }
        }
        return memberList;
    }



}
