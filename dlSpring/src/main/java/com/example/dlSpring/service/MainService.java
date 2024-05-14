package com.example.dlSpring.service;

import com.example.dlSpring.model.Switch;
import com.example.dlSpring.repository.EquipmentAtSwitchRepository;
import com.example.dlSpring.repository.EquipmentRepository;
import com.example.dlSpring.repository.SwitchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentAtSwitchRepository equipmentAtSwitchRepository;
    private final SwitchRepository switchRepository;

    public List<Switch> switchesToMainPage() {

        return null;
    }

}
