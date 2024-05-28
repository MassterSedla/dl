package com.example.dlSpring.service;

import com.example.dlSpring.dto.CommentDto;
import com.example.dlSpring.dto.EquipmentDto;
import com.example.dlSpring.dto.MainPageDto;
import com.example.dlSpring.dto.SwitchDto;
import com.example.dlSpring.model.Equipment;
import com.example.dlSpring.model.EquipmentAtSwitch;
import com.example.dlSpring.model.Switch;
import com.example.dlSpring.repository.EquipmentAtSwitchRepository;
import com.example.dlSpring.repository.EquipmentRepository;
import com.example.dlSpring.repository.SwitchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void deleteFromSwitch(Long switchId, int port) {
        equipmentAtSwitchRepository.deleteByaSwitch_IdAndPort(switchId, port);
    }

    public Set<String> listOfType() {
        return equipmentRepository.findAllType();
    }

    public Set<String> listOfCompany(String type) {
        return equipmentRepository.findAllCompanyByType(type);
    }

    public Set<String> listOfModel(String type, String company) {
        return equipmentRepository.findAllModelByTypeAndCompany(type, company);
    }

    public Long getEquipmentId(String type, String company, String model) {
        return equipmentRepository.findEquipmentIdByTypeAndCompanyAndModel(type, company, model);
    }

    public void occupyPort(EquipmentDto equipmentDto) {
        Equipment equipment = equipmentRepository.findById(equipmentDto.getId()).get();
        EquipmentAtSwitch equipmentAtSwitch = equipmentAtSwitchRepository
                .findByaSwitch_IdAndPort(equipmentDto.getSwitchId(), equipmentDto.getPort());
        if (equipmentAtSwitch != null) {
            equipmentAtSwitch.setComments("");
            equipmentAtSwitch.setEquipmentMac(equipmentDto.getEquipmentMac());
            equipmentAtSwitch.setEquipmentIp(equipmentDto.getEquipmentIp());
            equipmentAtSwitch.setEquipment(equipment);
            equipmentAtSwitchRepository.save(equipmentAtSwitch);
        } else {
            equipmentAtSwitchRepository.save(new EquipmentAtSwitch(equipment,
                    switchRepository.findById(equipmentDto.getSwitchId()).get(),
                    equipmentDto.getPort(), equipmentDto.getEquipmentIp(), equipmentDto.getEquipmentMac()));
        }

    }

    public void makeComment(CommentDto commentDto) {
        EquipmentAtSwitch equipmentAtSwitch = equipmentAtSwitchRepository
                .findByaSwitch_IdAndPort(commentDto.getSwitch_id(), commentDto.getPort());
        if (equipmentAtSwitch != null) {
            if (equipmentAtSwitch.getEquipment() == null && commentDto.getComment().isEmpty()) {
                equipmentAtSwitchRepository.delete(equipmentAtSwitch);
            } else {
                equipmentAtSwitch.setComments(commentDto.getComment());
                equipmentAtSwitchRepository.save(equipmentAtSwitch);
            }
        } else {
            equipmentAtSwitchRepository.save(
                    new EquipmentAtSwitch(
                            null,
                            switchRepository.findById(commentDto.getSwitch_id()).get(),
                            commentDto.getPort(),
                            null,
                            null,
                            commentDto.getComment())
                );
        }
    }

    public String getComment(Long switchId, int port) {
        return equipmentAtSwitchRepository.findCommentsByaSwitch_IdAndPort(switchId, port);
    }
}
