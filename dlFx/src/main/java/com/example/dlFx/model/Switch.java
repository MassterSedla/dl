package com.example.dlFx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Switch {
    private Long id;
    private int number;
    private String type;
    private String building;
    private String room;
    private int permissibleTrafficLoad;
    private int permissiblePowerLoad;
    private int numberOfPort;
    private List<EquipmentWithPort> equipments;

    public String getAvailablePorts() {
        long availablePorts = numberOfPort - countAvailablePorts();
        return availablePorts + "/" + numberOfPort;
    }

    public String getOccupiedPorts() {
        return countAvailablePorts() + "/" + numberOfPort;
    }

    public String getTrafficLoad() {
        int trafficLoad = permissibleTrafficLoad - equipments.stream()
                .flatMapToInt(e -> IntStream.of(Integer.parseInt(e.getEquipmentTrafficLoad()))).sum();
        return trafficLoad + "/" + permissibleTrafficLoad + "Mbps";
    }

    public String getPowerLoad() {
        int powerLoad = permissiblePowerLoad - equipments.stream()
                .flatMapToInt(e -> IntStream.of(Integer.parseInt(e.getEquipmentPowerLoad()))).sum();
        return powerLoad + "/" + permissiblePowerLoad + "w";
    }

    private long countAvailablePorts() {
        return equipments.stream().filter(e -> e.getId() != -1).count();
    }

    public void clean() {
        equipments.forEach(EquipmentWithPort::clean);
    }
}
