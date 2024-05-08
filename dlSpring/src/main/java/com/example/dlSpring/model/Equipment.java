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
@Table(name = "equipments")
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String model;
    private int equipmentLoad;
    private String characteristics;
    @OneToMany
    private List<EquipmentAtSwitch> equipmentAtSwitch;

    public Equipment(String name, String type, String model, int equipmentLoad, String characteristics) {
        this.name = name;
        this.type = type;
        this.model = model;
        this.equipmentLoad = equipmentLoad;
        this.characteristics = characteristics;
    }

    public Equipment(String name, String type, String model, int equipmentLoad, String characteristics, List<EquipmentAtSwitch> equipmentAtSwitch) {
        this.name = name;
        this.type = type;
        this.model = model;
        this.equipmentLoad = equipmentLoad;
        this.characteristics = characteristics;
        this.equipmentAtSwitch = equipmentAtSwitch;
    }
}
