package com.example.dlSpring.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String room;
    private int permissibleTrafficLoad;
    private int permissiblePowerLoad;
    private int numberOfPort;
    @OneToMany(mappedBy = "aSwitch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EquipmentAtSwitch> equipmentAtSwitches;

    public Switch(int number, String type, String building, String room, int permissibleTrafficLoad,
                  int permissiblePowerLoad, int numberOfPort, List<EquipmentAtSwitch> equipmentAtSwitches) {
        this.number = number;
        this.type = type;
        this.building = building;
        this.room = room;
        this.permissibleTrafficLoad = permissibleTrafficLoad;
        this.permissiblePowerLoad = permissiblePowerLoad;
        this.numberOfPort = numberOfPort;
        this.equipmentAtSwitches = equipmentAtSwitches;
    }

    public Switch(int number, String type, String building, String room,
                  int permissibleTrafficLoad, int permissiblePowerLoad, int numberOfPort) {
        this.number = number;
        this.type = type;
        this.building = building;
        this.room = room;
        this.permissibleTrafficLoad = permissibleTrafficLoad;
        this.permissiblePowerLoad = permissiblePowerLoad;
        this.numberOfPort = numberOfPort;
    }

    @Override
    public String toString() {
        return "Switch{" +
                "id=" + id +
                ", number=" + number +
                ", type='" + type + '\'' +
                ", building='" + building + '\'' +
                ", room='" + room + '\'' +
                ", permissibleTrafficLoad=" + permissibleTrafficLoad +
                ", permissiblePowerLoad=" + permissiblePowerLoad +
                ", numberOfPort=" + numberOfPort +
                '}';
    }
}
