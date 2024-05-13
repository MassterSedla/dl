package com.example.dlFx.dto;

import com.example.dlFx.dto.main.MainDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedUserDto implements MainDto {

    private String name;
    private String password;
}
