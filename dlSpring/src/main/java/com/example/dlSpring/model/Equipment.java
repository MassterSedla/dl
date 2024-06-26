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
@Table(name = "equipments")
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String company;
    private String model;
    private int equipmentTrafficLoad;
    private int equipmentPowerLoad;
    @OneToMany(mappedBy = "equipment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EquipmentAtSwitch> equipmentAtSwitch;


    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", equipmentTrafficLoad=" + equipmentTrafficLoad +
                ", equipmentPowerLoad=" + equipmentPowerLoad +
                '}';
    }

    public Equipment(String type, String company, String model, int equipmentTrafficLoad, int equipmentPowerLoad) {
        this.type = type;
        this.company = company;
        this.model = model;
        this.equipmentTrafficLoad = equipmentTrafficLoad;
        this.equipmentPowerLoad = equipmentPowerLoad;
    }
}