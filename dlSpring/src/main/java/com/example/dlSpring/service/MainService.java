package com.example.dlSpring.service;

import com.example.dlSpring.dto.CommentDto;
import com.example.dlSpring.dto.EquipmentDto;
import com.example.dlSpring.dto.SwitchDto;
import com.example.dlSpring.model.Equipment;
import com.example.dlSpring.model.EquipmentAtSwitch;
import com.example.dlSpring.repository.EquipmentAtSwitchRepository;
import com.example.dlSpring.repository.EquipmentRepository;
import com.example.dlSpring.repository.SwitchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Сервисный класс для управления основными операциями,
 * связанными с оборудованием и коммутаторами.
 */
@Service
@RequiredArgsConstructor
public class MainService {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentAtSwitchRepository equipmentAtSwitchRepository;
    private final SwitchRepository switchRepository;

    /**
     * Получает список всех зданий, где установлены коммутаторы.
     * @return набор уникальных названий зданий.
     */
    public Set<String> listOfBuildings() {
        return switchRepository.findAllBuilding();
    }

    /**
     * Получает список всех комнат в заданном здании.
     * @param building название здания.
     * @return набор уникальных названий комнат.
     */
    public Set<String> listOfRooms(String building) {
        return switchRepository.findAllRoomsByBuilding(building);
    }

    /**
     * Получает список всех номеров коммутаторов в заданной комнате.
     * @param building название здания.
     * @param room название комнаты.
     * @return набор уникальных номеров коммутаторов.
     */
    public Set<String> listOfNumber(String building, String room) {
        return switchRepository.findAllNumberByBuildingAndRoom(building, room);
    }

    /**
     * Получает информацию о коммутаторе по заданным параметрам здания, комнаты и номера.
     * @param building название здания.
     * @param room название комнаты.
     * @param number номер коммутатора.
     * @return DTO с информацией о коммутаторе.
     */
    public SwitchDto getSwitch(String building, String room, String number) {
        return new SwitchDto(switchRepository.findByBuildingAndRoomAndNumber(building, room, number));
    }

    /**
     * Удаляет оборудование с указанного порта коммутатора.
     * @param switchId идентификатор коммутатора.
     * @param port номер порта.
     */
    public void deleteFromSwitch(Long switchId, int port) {
        equipmentAtSwitchRepository.deleteByaSwitch_IdAndPort(switchId, port);
    }

    /**
     * Получает список всех типов оборудования.
     * @return набор уникальных типов оборудования.
     */
    public Set<String> listOfType() {
        return equipmentRepository.findAllType();
    }

    /**
     * Получает список всех компаний-производителей оборудования заданного типа.
     * @param type тип оборудования.
     * @return набор уникальных компаний-производителей.
     */
    public Set<String> listOfCompany(String type) {
        return equipmentRepository.findAllCompanyByType(type);
    }

    /**
     * Получает список всех моделей оборудования заданного типа и компании.
     * @param type тип оборудования.
     * @param company компания-производитель.
     * @return набор уникальных моделей оборудования.
     */
    public Set<String> listOfModel(String type, String company) {
        return equipmentRepository.findAllModelByTypeAndCompany(type, company);
    }

    /**
     * Получает идентификатор оборудования по его типу, компании и модели.
     * @param type тип оборудования.
     * @param company компания-производитель.
     * @param model модель оборудования.
     * @return идентификатор оборудования.
     */
    public Long getEquipmentId(String type, String company, String model) {
        return equipmentRepository.findEquipmentIdByTypeAndCompanyAndModel(type, company, model);
    }

    /**
     * Занимает указанный порт коммутатора заданным оборудованием.
     * @param equipmentDto DTO с информацией об оборудовании и порте.
     */
    public void occupyPort(EquipmentDto equipmentDto) {
        Equipment equipment = equipmentRepository.findById(equipmentDto.getId()).get();
        EquipmentAtSwitch equipmentAtSwitch = equipmentAtSwitchRepository
                .findByaSwitch_IdAndPort(equipmentDto.getSwitchId(), equipmentDto.getPort());
        // Если указанныый порт данного коммутатора занят, редактируем информацию под equipmentDto
        if (equipmentAtSwitch != null) {
            equipmentAtSwitch.setComments("");
            equipmentAtSwitch.setEquipmentMac(equipmentDto.getEquipmentMac());
            equipmentAtSwitch.setEquipmentIp(equipmentDto.getEquipmentIp());
            equipmentAtSwitch.setEquipment(equipment);
            equipmentAtSwitchRepository.save(equipmentAtSwitch);
        // Иначе создаем новую связку
        } else {
            equipmentAtSwitchRepository.save(new EquipmentAtSwitch(equipment,
                    switchRepository.findById(equipmentDto.getSwitchId()).get(),
                    equipmentDto.getPort(), equipmentDto.getEquipmentIp(), equipmentDto.getEquipmentMac()));
        }
    }

    /**
     * Добавляет или обновляет комментарий для оборудования на указанном порте коммутатора.
     * @param commentDto DTO с информацией о комментарии.
     */
    public void makeComment(CommentDto commentDto) {
        EquipmentAtSwitch equipmentAtSwitch = equipmentAtSwitchRepository
                .findByaSwitch_IdAndPort(commentDto.getSwitch_id(), commentDto.getPort());
        // Если указанныый порт данного коммутатора занят, редактируем комментарий
        if (equipmentAtSwitch != null) {
            // Если на этом порте нет оборудования и комментарий в commentDto пустой, удаляем связку
            if (equipmentAtSwitch.getEquipment() == null && commentDto.getComment().isEmpty()) {
                equipmentAtSwitchRepository.delete(equipmentAtSwitch);
            // Иначе ставим комментарий из commentDto
            } else {
                equipmentAtSwitch.setComments(commentDto.getComment());
                equipmentAtSwitchRepository.save(equipmentAtSwitch);
            }
        // Иначе создаем новую связку с комментарием из commentDto
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

    /**
     * Получает комментарий для оборудования на указанном порте коммутатора.
     * @param switchId идентификатор коммутатора.
     * @param port номер порта.
     * @return комментарий.
     */
    public String getComment(Long switchId, int port) {
        return equipmentAtSwitchRepository.findCommentsByaSwitch_IdAndPort(switchId, port);
    }
}
