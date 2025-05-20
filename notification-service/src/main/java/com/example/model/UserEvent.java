package com.example.model;

public class UserEvent {
    private String email;
    private String operation;

    public UserEvent(String mail, String create) {
    }

    public String getEmail() {
        return email;
    }

    public String getOperation() {
        return operation;
    }
}
