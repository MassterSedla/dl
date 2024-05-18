package com.example.dlSpring.dto;

import com.example.dlSpring.model.Equipment;
import com.example.dlSpring.model.EquipmentAtSwitch;
import lombok.Data;

@Data
public class EquipmentWithPortDto {
    private Long id;
    private int port;
    private String type;
    private int equipmentTrafficLoad;
    private int equipmentPowerLoad;
    private String comment;

    public EquipmentWithPortDto(EquipmentAtSwitch equipmentAtSwitch) {
        this.port = equipmentAtSwitch.getPort();
        Equipment equipment = equipmentAtSwitch.getEquipment();
        this.id = equipment.getId();
        this.type = equipment.getType();
        this.equipmentTrafficLoad = equipment.getEquipmentTrafficLoad();
        this.equipmentPowerLoad = equipment.getEquipmentPowerLoad();
        comment = "";
    }
}
