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
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/page")
    public ResponseEntity<MainPageDto> getAllBuildings() {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfBuildings())
        ), HttpStatus.OK);
    }

    @GetMapping("/page/{building}")
    public ResponseEntity<MainPageDto> getRooms(@PathVariable String building) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfRooms(building))
        ), HttpStatus.OK);
    }

    @GetMapping("/page/{building}/{room}")
    public ResponseEntity<MainPageDto> getNumbers(@PathVariable String building, @PathVariable String room) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfNumber(building, room))
        ), HttpStatus.OK);
    }

    @GetMapping("/page/{building}/{room}/{number}")
    public ResponseEntity<SwitchDto> getSwitches(@PathVariable String building, @PathVariable String room, @PathVariable int number) {
        return new ResponseEntity<>(mainService.getSwitch(building, room, number), HttpStatus.OK);
    }

    @DeleteMapping("/page/{switchId}/{port}")
    public void deleteFromSwitch(@PathVariable Long switchId, @PathVariable int port) {
        mainService.deleteFromSwitch(switchId, port);
    }

    @GetMapping("/dialogPage")
    public ResponseEntity<MainPageDto> getAllEquipmentType() {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfType())
        ), HttpStatus.OK);
    }

    @GetMapping("/dialogPage/{type}")
    public ResponseEntity<MainPageDto> getAllEquipmentCompanyByType(@PathVariable String type) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfCompany(redactUrl(type)))
        ), HttpStatus.OK);
    }

    @GetMapping("/dialogPage/{type}/{company}")
    public ResponseEntity<MainPageDto> getAllEquipmentModelByTypeAndCompany(@PathVariable String type,
                                                                            @PathVariable String company) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfModel(redactUrl(type), redactUrl(company)))
        ), HttpStatus.OK);
    }

    @PostMapping("/occupyPort/{type}/{company}/{model}")
    public void  getEquipmentId(@PathVariable String type, @PathVariable String company,
                                @PathVariable String model, @RequestBody EquipmentDto equipmentDto) {
        equipmentDto.setId(mainService.getEquipmentId(redactUrl(type), redactUrl(company), redactUrl(model)));
        mainService.occupyPort(equipmentDto);
    }

    @PostMapping("/makeComment")
    public void makeComment(@RequestBody CommentDto commentDto) {
        mainService.makeComment(commentDto);
    }

    @GetMapping("/comment/{switchId}/{port}")
    public ResponseEntity<MainPageDto> getComment(@PathVariable Long switchId, @PathVariable int port) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(Collections.singleton(mainService.getComment(switchId, port)))
        ), HttpStatus.OK);
    }

    private String redactUrl(String url) {
        return url.replaceAll("_", " ");
    }

}