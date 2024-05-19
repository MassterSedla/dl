package com.example.dlFx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role {
    private Long id;
    private String name;
    private Set<User> users;

    public Role(String name) {
        this.name = name;
    }
}
