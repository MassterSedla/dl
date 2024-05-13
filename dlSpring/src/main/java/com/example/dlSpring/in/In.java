package com.example.dlSpring.in;


import com.example.dlSpring.model.*;
import com.example.dlSpring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class In implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SwitchRepository switchRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentAtSwitchRepository equipmentAtSwitchRepository;
    private final PasswordEncoder passwordEncoder;


    public void run(String... arg) throws Exception {
        Role roleUser = new Role("ROLE_USER");
        Set<Role> userRoles = new HashSet<>();
        if (roleRepository.findByName("ROLE_USER") == null) {
            roleRepository.save(roleUser);
        }
        userRoles.add(roleUser);
        if (userRepository.findByName("user") == null) {
            User userUser = new User("user", passwordEncoder.encode("user"), userRoles);
            userRepository.save(userUser);
        }
        Switch aSwitch = new Switch(1, "first", "1", 1, "1", 200, 200);
        Switch aSwitch1 = new Switch(2, "first", "2", 1, "2", 200, 200);
        Switch aSwitch2 = new Switch(3, "first", "2", 1, "1", 200, 200);
        Switch aSwitch3 = new Switch(4, "first", "3", 1, "1", 200, 200);
        aSwitch = switchRepository.save(aSwitch);
        switchRepository.save(aSwitch1);
        switchRepository.save(aSwitch2);
        switchRepository.save(aSwitch3);
        Equipment equipment = new Equipment("camera", "first model", 10, 10);
        equipment = equipmentRepository.save(equipment);
        EquipmentAtSwitch equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch, 1);
        equipmentAtSwitchRepository.save(equipmentAtSwitch);

        equipment = new Equipment("camera", "second model", 10, 10);
        equipment = equipmentRepository.save(equipment);
        equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch, 2);
        equipmentAtSwitchRepository.save(equipmentAtSwitch);

        equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch1, 1);
        equipmentAtSwitchRepository.save(equipmentAtSwitch);

        equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch2, 2);
        equipmentAtSwitchRepository.save(equipmentAtSwitch);

        List<Switch> list = switchRepository.findAll();
        list.forEach(s -> {
            int sum = s.getEquipmentAtSwitches().stream().mapToInt(equipmentAtSwitch1 ->
                    equipmentAtSwitch1.getEquipment().getEquipmentPowerLoad()).sum();
            s.setPermissiblePowerLoad(s.getPermissiblePowerLoad() - sum);
        });
        EquipmentAtSwitch q = (EquipmentAtSwitch) aSwitch.getEquipmentAtSwitches();

        System.out.println(switchRepository.findAllRoomsByBuilding("2"));
        System.out.println(equipmentAtSwitch.getASwitch() + "\n" + equipmentAtSwitch.getEquipment());
        System.out.println(list);
    }
}