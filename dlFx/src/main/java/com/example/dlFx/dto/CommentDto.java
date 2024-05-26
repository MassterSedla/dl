package com.example.dlFx.dto;

import com.example.dlFx.dto.main.MainDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto implements MainDto {
    private long switch_id;
    private int port;
    private String comment;
}