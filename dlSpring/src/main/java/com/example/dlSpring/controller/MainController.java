package com.example.dlSpring.controller;

import com.example.dlSpring.dto.MainPageDto;
import com.example.dlSpring.dto.SwitchDto;
import com.example.dlSpring.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public void deleteEquipment(@PathVariable Long switchId, @PathVariable int port) {
        mainService.deleteEquipment(switchId, port);
    }

    @GetMapping("/dialogPage")
    public ResponseEntity<MainPageDto> getAllEquipmentType() {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfType())
        ), HttpStatus.OK);
    }

    @GetMapping("/dialogPage/{type}")
    public ResponseEntity<MainPageDto> getAllEquipmentByType(@PathVariable String type) {
        return new ResponseEntity<>(new MainPageDto(
                new ArrayList<>(mainService.listOfModel(redactUrl(type)))
        ), HttpStatus.OK);
    }

    @GetMapping("/dialogPage/{type}/{model}")
    public ResponseEntity<MainPageDto> getEquipmentId(@PathVariable String type, @PathVariable String model) {
        return new ResponseEntity<>(new MainPageDto(List.of(String.valueOf(
                mainService.getEquipmentIdByTypeAndModel(redactUrl(type), redactUrl(model)))
        )), HttpStatus.OK);
    }

    @PostMapping("/occupyPort")
    public void occupyPort(@RequestBody MainPageDto mainPageDto) {
        mainService.occupyPort(mainPageDto);
    }

    private String redactUrl(String url) {
        return url.replaceAll("_", " ");
    }
}