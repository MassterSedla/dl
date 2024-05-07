package com.example.dlFx.dto;

import com.example.dlFx.dto.main.MainDto;

public class AuthorizedUserDto implements MainDto {

    private String name;
    private String password;

    public AuthorizedUserDto() {
    }

    public AuthorizedUserDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
