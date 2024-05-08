package com.example.dlSpring.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "equipments_at_switches")
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentAtSwitch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Equipment equipment;
    @ManyToOne
    private Switch aSwitch;
    private int port;

    public EquipmentAtSwitch(Equipment equipment, Switch aSwitch, int port) {
        this.equipment = equipment;
        this.aSwitch = aSwitch;
        this.port = port;
    }
}
