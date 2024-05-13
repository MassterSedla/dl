package com.example.dlSpring.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "switches")
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Switch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    private String type;
    private String building;
    private int floor;
    private String room;
    private int permissibleTrafficLoad;
    private int permissiblePowerLoad;
    @OneToMany
    private List<EquipmentAtSwitch> equipmentAtSwitches;

    public Switch(int number, String type, String building, String room, int permissibleTrafficLoad,
                  int floor, int permissiblePowerLoad, List<EquipmentAtSwitch> equipmentAtSwitches) {
        this.number = number;
        this.type = type;
        this.building = building;
        this.floor = floor;
        this.room = room;
        this.permissibleTrafficLoad = permissibleTrafficLoad;
        this.permissiblePowerLoad = permissiblePowerLoad;
        this.equipmentAtSwitches = equipmentAtSwitches;
    }

    public Switch(int number, String type, String building, int floor, String room,
                  int permissibleTrafficLoad, int permissiblePowerLoad) {
        this.number = number;
        this.type = type;
        this.building = building;
        this.floor = floor;
        this.room = room;
        this.permissibleTrafficLoad = permissibleTrafficLoad;
        this.permissiblePowerLoad = permissiblePowerLoad;
    }
}
