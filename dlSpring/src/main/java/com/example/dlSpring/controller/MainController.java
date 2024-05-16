package com.example.dlSpring.controller;

import com.example.dlSpring.dto.MainPageDto;
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

    @GetMapping("/page/{building}/{floor}")
    public ResponseEntity<MainPageDto> getRooms(@PathVariable String building, @PathVariable int floor) {
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setList(mainService.listOfRooms(building, floor));
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<MainPageDto> getAllBuildings() {
        MainPageDto mainPageDto = new MainPageDto();
        mainPageDto.setList(mainService.listOfBuildings());
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }
}