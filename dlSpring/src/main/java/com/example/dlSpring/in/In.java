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
//        Role roleUser = new Role("ROLE_USER");
//        Set<Role> userRoles = new HashSet<>();
//        if (roleRepository.findByName("ROLE_USER") == null) {
//            roleRepository.save(roleUser);
//        }
//        userRoles.add(roleUser);
//        if (userRepository.findByName("user") == null) {
//            User userUser = new User("user", passwordEncoder.encode("user"), userRoles);
//            userRepository.save(userUser);
//        }
//        Switch aSwitch = new Switch(1, "first", "1", 1, "1", 200, 200);
//        Switch aSwitch1 = new Switch(2, "first", "1", 1, "2", 200, 200);
//        Switch aSwitch2 = new Switch(3, "first", "1", 2, "1", 200, 200);
//        Switch aSwitch3 = new Switch(4, "first", "1", 3, "1", 200, 200);
//        aSwitch = switchRepository.save(aSwitch);
//        switchRepository.save(aSwitch1);
//        switchRepository.save(aSwitch2);
//        switchRepository.save(aSwitch3);
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(5, "first", "1", 3, "4", 200, 200)),
//                2
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(1, "first", "2", 4, "4", 200, 200)), 2
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(2, "first", "2", 5, "5", 200, 200)),
//                3
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(4, "first", "2", 2, "3", 200, 200)),
//                1
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(5, "first", "2", 3, "2", 200, 200)),
//                4
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(1, "first", "3", 1, "14", 200, 200)),
//                9
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(2, "first", "3", 4, "23", 200, 200)),
//                2
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(1, "first", "3", 1, "3", 200, 200)),
//                2
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(2, "first", "3", 3, "32", 200, 200)),
//                8
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(1, "first", "3", 2, "65", 200, 200)),
//                3
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(3, "first", "4", 2, "7", 200, 200)),
//                7
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(3, "first", "4", 3, "7", 200, 200)),
//                2
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(2, "first", "4", 2, "9", 200, 200)),
//                1
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(2, "first", "4", 2, "9", 200, 200)),
//                5
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipmentRepository.save(new Equipment("camera", "first model", 10, 10)),
//                switchRepository.save(new Switch(1, "first", "4", 3, "98", 200, 200)),
//                12
//        ));
//        Equipment equipment = new Equipment("camera", "first model", 10, 10);
//        equipment = equipmentRepository.save(equipment);
//        EquipmentAtSwitch equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch, 1);
//        equipmentAtSwitchRepository.save(equipmentAtSwitch);
//
//        equipment = new Equipment("camera", "second model", 10, 10);
//        equipment = equipmentRepository.save(equipment);
//        equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch, 2);
//        equipmentAtSwitchRepository.save(equipmentAtSwitch);
//
//        equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch1, 1);
//        equipmentAtSwitchRepository.save(equipmentAtSwitch);
//
//        equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch2, 2);
//        equipmentAtSwitchRepository.save(equipmentAtSwitch);
//
//
//        Switch fetchedSwitch = switchRepository.findById(aSwitch.getId()).orElseThrow(() -> new RuntimeException("Switch not found"));
////        System.out.println(fetchedSwitch.getEquipmentAtSwitches());  // Проверка equipmentAtSwitches
//        fetchedSwitch.getEquipmentAtSwitches().forEach(e -> System.out.println(e.getEquipment()));
//        System.out.println(switchRepository.findAllRoomsByBuildingAndFloor("1", 1));
//        List<Switch> list = switchRepository.findAll();
////        list.forEach(s -> {
////            int sum = s.getEquipmentAtSwitches().stream().mapToInt(equipmentAtSwitch1 ->
////                    equipmentAtSwitch1.getEquipment().getEquipmentPowerLoad()).sum();
////            s.setPermissiblePowerLoad(s.getPermissiblePowerLoad() - sum);
////        });
////        System.out.println(aSwitch.getEquipmentAtSwitches());
////
////        System.out.println(switchRepository.findAllRoomsByBuilding("2"));
////        System.out.println(aSwitch);
//        System.out.println(list.get(0));
    }
}