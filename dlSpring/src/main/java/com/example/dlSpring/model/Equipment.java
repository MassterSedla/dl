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
    private String type;
    private String model;
    private int equipmentTrafficLoad;
    private int equipmentPowerLoad;
    @OneToMany
    private List<EquipmentAtSwitch> equipmentAtSwitch;

    public Equipment(String type, String model, int equipmentTrafficLoad, int equipmentPowerLoad) {
        this.type = type;
        this.model = model;
        this.equipmentTrafficLoad = equipmentTrafficLoad;
        this.equipmentPowerLoad = equipmentPowerLoad;
    }

    public Equipment(String type, String model, int equipmentTrafficLoad, int equipmentPowerLoad, List<EquipmentAtSwitch> equipmentAtSwitch) {
        this.type = type;
        this.model = model;
        this.equipmentTrafficLoad = equipmentTrafficLoad;
        this.equipmentPowerLoad = equipmentPowerLoad;
        this.equipmentAtSwitch = equipmentAtSwitch;
    }
}