package com.example.dlFx.dto;

import com.example.dlFx.dto.main.MainDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto implements MainDto {
    private Long switchId;
    private int port;
    private String equipmentIp;
    private String equipmentMac;
}
