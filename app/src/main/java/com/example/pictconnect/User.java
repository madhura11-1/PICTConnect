package com.example.pictconnect;

public class User {

    String registration_id;
    String email;
    String password;
    String name;
    String department;
    int year;
    String linkedin_url;
    String github_url;
    String role;

    public User(String registration_id, String email, String password, String name, String department, int year, String linkedin_url, String github_url, String role) {
        this.registration_id = registration_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.department = department;
        this.year = year;
        this.linkedin_url = linkedin_url;
        this.github_url = github_url;
        this.role = role;
    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLinkedin_url() {
        return linkedin_url;
    }

    public void setLinkedin_url(String linkedin_url) {
        this.linkedin_url = linkedin_url;
    }

    public String getGithub_url() {
        return github_url;
    }

    public void setGithub_url(String github_url) {
        this.github_url = github_url;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
