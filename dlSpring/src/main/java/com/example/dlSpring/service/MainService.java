package com.example.dlSpring.service;

import com.example.dlSpring.dto.SwitchDto;
import com.example.dlSpring.model.Switch;
import com.example.dlSpring.repository.EquipmentAtSwitchRepository;
import com.example.dlSpring.repository.EquipmentRepository;
import com.example.dlSpring.repository.SwitchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentAtSwitchRepository equipmentAtSwitchRepository;
    private final SwitchRepository switchRepository;

    public Set<String> listOfBuildings() {
        return switchRepository.findAllBuilding();
    }

    public Set<String> listOfRooms(String building) {
        return switchRepository.findAllRoomsByBuilding(building);
    }

    public Set<String> listOfNumber(String building, String room) {
        return switchRepository.findAllNumberByBuildingAndRoom(building, room);
    }

    public SwitchDto getSwitch(String building, String room, int number) {
        return new SwitchDto(switchRepository.findByBuildingAndRoomAndNumber(building, room, number));
    }

}
