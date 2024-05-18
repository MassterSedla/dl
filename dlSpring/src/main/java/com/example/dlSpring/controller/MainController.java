package com.example.dlSpring.controller;

import com.example.dlSpring.dto.MainPageDto;
import com.example.dlSpring.dto.SwitchDto;
import com.example.dlSpring.model.User;
import com.example.dlSpring.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/page")
    public ResponseEntity<MainPageDto> getAllBuildings() {
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setList(mainService.listOfBuildings());
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/page/{building}")
    public ResponseEntity<MainPageDto> getRooms(@PathVariable String building) {
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setList(mainService.listOfRooms(building));
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/page/{building}/{room}")
    public ResponseEntity<MainPageDto> getNumbers(@PathVariable String building, @PathVariable String room) {
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setList(mainService.listOfNumber(building, room));
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/page/{building}/{room}/{number}")
    public ResponseEntity<SwitchDto> getSwitches(@PathVariable String building, @PathVariable String room, @PathVariable int number) {
        return new ResponseEntity<>(mainService.getSwitch(building, room, number), HttpStatus.OK);
    }
}