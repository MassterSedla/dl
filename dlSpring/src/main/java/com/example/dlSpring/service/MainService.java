package com.example.dlSpring.service;

import com.example.dlSpring.dto.MainPageDto;
import com.example.dlSpring.dto.SwitchDto;
import com.example.dlSpring.model.EquipmentAtSwitch;
import com.example.dlSpring.repository.EquipmentAtSwitchRepository;
import com.example.dlSpring.repository.EquipmentRepository;
import com.example.dlSpring.repository.SwitchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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

    @Transactional
    public void deleteEquipment(Long switchId, int port) {
        equipmentAtSwitchRepository.deleteBySwitchIdAndPort(switchId, port);
    }

    public Set<String> listOfType() {
        return equipmentRepository.findAllType();
    }

    public Set<String> listOfModel(String type) {
        return equipmentRepository.findAllModelByType(type);
    }

    public Integer getEquipmentIdByTypeAndModel(String type, String model) {
        return equipmentRepository.findEquipmentIdByTypeAndModel(type, model);
    }

    public void occupyPort(MainPageDto mainPageDto) {
        List<String> list = mainPageDto.getList();
        equipmentAtSwitchRepository.save(
                new EquipmentAtSwitch(
                        equipmentRepository.findById(Long.valueOf(list.get(0))).get(),
                        switchRepository.findById(Long.valueOf(list.get(1))).get(),
                        Integer.parseInt(list.get(2))
                )
        );
    }
}
