package com.example.user.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    public UserDTO(Long id, String name, String email) {
    }

    public Object getName() {
        return null;
    }

    public boolean getId() {
        return false;
    }
}
