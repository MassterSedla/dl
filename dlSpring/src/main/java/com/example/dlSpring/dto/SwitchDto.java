package com.example.dlSpring.dto;

import com.example.dlSpring.model.Switch;
import lombok.Data;

import java.util.List;

/**
 * DTO для передачи информации о коммутаторе.
 */

@Data
public class SwitchDto {
    private Long id;
    private String number;
    private String type;
    private String building;
    private String room;
    private int permissibleTrafficLoad;
    private int permissiblePowerLoad;
    private int numberOfPort;
    private List<EquipmentWithPortDto> equipments;

    /**
     * Конструктор, который инициализирует DTO на основе объекта Switch.
     *
     * @param aSwitch объект коммутатора, на основе которого создается DTO.
     */
    public SwitchDto(Switch aSwitch) {
        this.id = aSwitch.getId();
        this.number = aSwitch.getNumber();
        this.type = aSwitch.getType();
        this.building = aSwitch.getBuilding();
        this.room = aSwitch.getRoom();
        this.permissibleTrafficLoad = aSwitch.getPermissibleTrafficLoad();
        this.permissiblePowerLoad = aSwitch.getPermissiblePowerLoad();
        this.numberOfPort = aSwitch.getNumberOfPort();
        this.equipments = aSwitch.getEquipmentAtSwitches().stream().map(EquipmentWithPortDto::new).toList();
    }
}
