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
//        equipmentAtSwitchRepository.save(
//                new EquipmentAtSwitch(null, switchRepository.findById(1L).get(), 18, "dhuiquwidh")
//        );
//        Role roleUser = new Role("ROLE_USER");
//        Set<Role> userRoles = new HashSet<>();
//        if (roleRepository.findByName("ROLE_USER") == null) {
//            roleRepository.save(roleUser);
//        }
//        userRoles.add(roleUser);
//        if (userRepository.findByUsername("user") == null) {
//            User userUser = new User("user", "user", "user", "user@mail.ru", passwordEncoder.encode("user"), userRoles);
//            userRepository.save(userUser);
//        }
//
//
//        Equipment equipment1 = equipmentRepository.save(new Equipment("camera", "1 model", 10, 8));
//        Equipment equipment2 = equipmentRepository.save(new Equipment("camera", "2 model", 12, 7));
//        Equipment equipment3 = equipmentRepository.save(new Equipment("camera", "3 model", 14, 9));
//        Equipment equipment4 = equipmentRepository.save(new Equipment("camera", "4 model", 15, 10));
//
//        Switch switch1 = switchRepository.save(new Switch(4, "first", "1",  "D.2.021", 200, 200, 24));
//        Switch switch2 = switchRepository.save(new Switch(14, "first", "1",  "D.3.044", 200, 200, 24));
//        Switch switch3 = switchRepository.save(new Switch(13, "first", "2",  "D.1.007", 200, 200, 24));
//        Switch switch4 = switchRepository.save(new Switch(16, "first", "3",  "D.1.027", 200, 200, 24));
//        Switch switch5 = switchRepository.save(new Switch(16, "first", "2",  "C.3.056", 200, 200, 24));
//        Switch switch6 = switchRepository.save(new Switch(11, "first", "2",  "C.1.033", 200, 200, 24));
//        Switch switch7 = switchRepository.save(new Switch(16, "first", "1",  "C.1.034", 200, 200, 24));
//        Switch switch8 = switchRepository.save(new Switch(19, "first", "3",  "B.4.025", 200, 200, 24));
//        Switch switch9 = switchRepository.save(new Switch(17, "first", "2",  "B.3.041", 200, 200, 24));
//        Switch switch0 = switchRepository.save(new Switch(12, "first", "3",  "B.1.013", 200, 200, 24));
//
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch1,
//                4
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch1,
//                6
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch1,
//                3
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment4,
//                switch1,
//                1
//        ));
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch2,
//                12
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch2,
//                13
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch2,
//                17
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment4,
//                switch2,
//                19
//        ));
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch3,
//                12
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch3,
//                13
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch3,
//                17
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment4,
//                switch3,
//                19
//        ));
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch4,
//                12
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch4,
//                13
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch4,
//                17
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment4,
//                switch4,
//                19
//        ));
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch5,
//                12
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch5,
//                13
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch5,
//                17
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment4,
//                switch5,
//                19
//        ));
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch6,
//                12
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch6,
//                13
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch6,
//                17
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment4,
//                switch6,
//                19
//        ));
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch7,
//                12
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch7,
//                13
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch7,
//                17
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment4,
//                switch7,
//                19
//        ));
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch8,
//                12
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch8,
//                13
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch8,
//                17
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment4,
//                switch8,
//                19
//        ));
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch9,
//                12
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch9,
//                13
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch9,
//                17
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment4,
//                switch0,
//                19
//        ));
//
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment1,
//                switch0,
//                12
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment2,
//                switch0,
//                13
//        ));
//        equipmentAtSwitchRepository.save(new EquipmentAtSwitch(
//                equipment3,
//                switch0,
//                17
//        ));
    }
}