package com.example.dlSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long switch_id;
    private int port;
    private String comment;
}
