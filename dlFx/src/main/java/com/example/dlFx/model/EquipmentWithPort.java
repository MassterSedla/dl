package com.example.dlFx.model;

import com.example.dlFx.dto.MainPageDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentWithPort {
    private Long id;
    private int port;
    private String type;
    private int equipmentTrafficLoad;
    private int equipmentPowerLoad;
    private String comment;

}
