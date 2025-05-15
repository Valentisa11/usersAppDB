package com.example.user.dto;

import lombok.Data;

@Data
public class UserCreateDTO {
    private String name;
    private String email;

    public UserCreateDTO(String Alice, String mail) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
