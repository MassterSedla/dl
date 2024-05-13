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
    private int room; // 2 поля в одно: сооружение + номер помещения; Надо сделать string
    private int permissibleLoad;
    private String characteristics; //не нужно
    @OneToMany
    private List<EquipmentAtSwitch> equipmentAtSwitches;

    public Switch(int number, String type, int room, int permissibleLoad, String characteristics, List<EquipmentAtSwitch> equipmentAtSwitches) {
        this.number = number;
        this.type = type;
        this.room = room;
        this.permissibleLoad = permissibleLoad;
        this.characteristics = characteristics;
        this.equipmentAtSwitches = equipmentAtSwitches;
    }

    public Switch(int number, String type, int room, int permissibleLoad, String characteristics) {
        this.number = number;
        this.type = type;
        this.room = room;
        this.permissibleLoad = permissibleLoad;
        this.characteristics = characteristics;
    }
}
