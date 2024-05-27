package com.example.dlSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {
    private Long id;
    private Long switchId;
    private int port;
    private String equipmentIp;
    private String equipmentMac;
}