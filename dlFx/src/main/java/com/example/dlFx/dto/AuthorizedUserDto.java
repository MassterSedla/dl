package com.example.dlFx.dto;

import com.example.dlFx.dto.main.MainDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizedUserDto implements MainDto {

    private String name;
    private String password;
}
