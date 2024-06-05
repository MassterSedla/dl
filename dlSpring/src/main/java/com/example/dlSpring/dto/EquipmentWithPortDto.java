package com.example.dlSpring.dto;

import com.example.dlSpring.model.Equipment;
import com.example.dlSpring.model.EquipmentAtSwitch;
import lombok.Data;

/**
 * DTO для передачи информации об оборудовании и порте, к которому оно подключено.
 */
@Data
public class EquipmentWithPortDto {
    private Long id;
    private int port;
    private String type;
    private int equipmentTrafficLoad;
    private int equipmentPowerLoad;
    private String comment;


    /**
     * Конструктор, который инициализирует DTO на основе объекта EquipmentAtSwitch.
     *
     * @param equipmentAtSwitch объект, представляющий связь между оборудованием и коммутатором.
     */
    public EquipmentWithPortDto(EquipmentAtSwitch equipmentAtSwitch) {
        this.port = equipmentAtSwitch.getPort();
        this.comment = equipmentAtSwitch.getComments();
        Equipment equipment = equipmentAtSwitch.getEquipment();
        if (equipment != null) {
            this.id = equipment.getId();
            this.type = equipment.getType();
            this.equipmentTrafficLoad = equipment.getEquipmentTrafficLoad();
            this.equipmentPowerLoad = equipment.getEquipmentPowerLoad();
        } else {
            id = -1L;
        }
    }
}
