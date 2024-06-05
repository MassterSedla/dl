package com.example.dlSpring.controller;

import com.example.dlSpring.dto.CommentDto;
import com.example.dlSpring.dto.EquipmentDto;
import com.example.dlSpring.dto.MainPageDto;
import com.example.dlSpring.dto.SwitchDto;
import com.example.dlSpring.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Контроллер для обработки запросов, связанных с основным функционалом приложения.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    /**
     * Обрабатывает GET-запрос на '/api/page' для получения списка всех зданий.
     *
     * @return ResponseEntity с DTO, содержащим список зданий, и статусом OK.
     */
    @GetMapping("/page")
    public ResponseEntity<MainPageDto> getAllBuildings() {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfBuildings())
        ), HttpStatus.OK);
    }

    /**
     * Обрабатывает GET-запрос на '/api/page/{building}' для получения списка комнат в указанном здании.
     *
     * @param building название здания.
     * @return ResponseEntity с DTO, содержащим список комнат, и статусом OK.
     */
    @GetMapping("/page/{building}")
    public ResponseEntity<MainPageDto> getRooms(@PathVariable String building) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfRooms(redactUrl(building)))
        ), HttpStatus.OK);
    }

    /**
     * Обрабатывает GET-запрос на '/api/page/{building}/{room}' для получения списка номеров в указанной комнате.
     *
     * @param building название здания.
     * @param room название комнаты.
     * @return ResponseEntity с DTO, содержащим список номеров, и статусом OK.
     */
    @GetMapping("/page/{building}/{room}")
    public ResponseEntity<MainPageDto> getNumbers(@PathVariable String building, @PathVariable String room) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfNumber(redactUrl(building), room))
        ), HttpStatus.OK);
    }

    /**
     * Обрабатывает GET-запрос на '/api/page/{building}/{room}/{number}' для получения информации о коммутаторе.
     *
     * @param building название здания.
     * @param room название комнаты.
     * @param number номер коммутатора.
     * @return ResponseEntity с DTO коммутатора и статусом OK.
     */
    @GetMapping("/page/{building}/{room}/{number}")
    public ResponseEntity<SwitchDto> getSwitches(@PathVariable String building, @PathVariable String room, @PathVariable String number) {
        return new ResponseEntity<>(mainService.getSwitch(redactUrl(building), room, number), HttpStatus.OK);
    }

    /**
     * Обрабатывает DELETE-запрос на '/api/page/{switchId}/{port}' для удаления оборудования из порта коммутатора.
     *
     * @param switchId идентификатор коммутатора.
     * @param port номер порта.
     */
    @DeleteMapping("/page/{switchId}/{port}")
    public void deleteFromSwitch(@PathVariable Long switchId, @PathVariable int port) {
        mainService.deleteFromSwitch(switchId, port);
    }

    /**
     * Обрабатывает GET-запрос на '/api/dialogPage' для получения списка всех типов оборудования.
     *
     * @return ResponseEntity с DTO, содержащим список типов оборудования, и статусом OK.
     */
    @GetMapping("/dialogPage")
    public ResponseEntity<MainPageDto> getAllEquipmentType() {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfType())
        ), HttpStatus.OK);
    }

    /**
     * Обрабатывает GET-запрос на '/api/dialogPage/{type}' для получения списка компаний по типу оборудования.
     *
     * @param type тип оборудования.
     * @return ResponseEntity с DTO, содержащим список компаний, и статусом OK.
     */
    @GetMapping("/dialogPage/{type}")
    public ResponseEntity<MainPageDto> getAllEquipmentCompanyByType(@PathVariable String type) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfCompany(redactUrl(type)))
        ), HttpStatus.OK);
    }

    /**
     * Обрабатывает GET-запрос на '/api/dialogPage/{type}/{company}' для получения списка моделей оборудования по типу и компании.
     *
     * @param type тип оборудования.
     * @param company компания-производитель оборудования.
     * @return ResponseEntity с DTO, содержащим список моделей оборудования, и статусом OK.
     */
    @GetMapping("/dialogPage/{type}/{company}")
    public ResponseEntity<MainPageDto> getAllEquipmentModelByTypeAndCompany(@PathVariable String type,
                                                                            @PathVariable String company) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfModel(redactUrl(type), redactUrl(company)))
        ), HttpStatus.OK);
    }

    /**
     * Обрабатывает POST-запрос на '/api/occupyPort/{type}/{company}/{model}' для занятия порта оборудованием.
     *
     * @param type тип оборудования.
     * @param company компания-производитель оборудования.
     * @param model модель оборудования.
     * @param equipmentDto DTO оборудования, включающего в себя информацию о коммутаторе и порте.
     */
    @PostMapping("/occupyPort/{type}/{company}/{model}")
    public void getEquipmentId(@PathVariable String type, @PathVariable String company,
                               @PathVariable String model, @RequestBody EquipmentDto equipmentDto) {
        equipmentDto.setId(mainService.getEquipmentId(redactUrl(type), redactUrl(company), redactUrl(model)));
        mainService.occupyPort(equipmentDto);
    }

    /**
     * Обрабатывает POST-запрос на '/api/makeComment' для добавления комментария к порту коммутатора.
     *
     * @param commentDto DTO комментария, включающего в себя информацию о коммутаторе, порте и комментарии.
     */
    @PostMapping("/makeComment")
    public void makeComment(@RequestBody CommentDto commentDto) {
        mainService.makeComment(commentDto);
    }

    /**
     * Обрабатывает GET-запрос на '/api/comment/{switchId}/{port}' для получения комментария о порте коммутатора.
     *
     * @param switchId идентификатор коммутатора.
     * @param port номер порта.
     * @return ResponseEntity с DTO, содержащим комментарий, и статусом OK.
     */
    @GetMapping("/comment/{switchId}/{port}")
    public ResponseEntity<MainPageDto> getComment(@PathVariable Long switchId, @PathVariable int port) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(Collections.singleton(mainService.getComment(switchId, port)))
        ), HttpStatus.OK);
    }

    /**
     * Вспомогательный метод для замены символов подчеркивания на пробелы в URL.
     *
     * @param url строка URL.
     * @return отредактированная строка URL.
     */
    private String redactUrl(String url) {
        return url.replaceAll("_", " ");
    }
}
