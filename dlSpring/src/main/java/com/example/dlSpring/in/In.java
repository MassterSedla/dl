package com.example.dlSpring.in;


import com.example.dlSpring.model.*;
import com.example.dlSpring.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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
        Switch aSwitch = new Switch(1, "first", 1, 200, "fcewpvkoewfjioesafhoifeuoihfeauhoi");
        aSwitch = switchRepository.save(aSwitch);
        Equipment equipment = new Equipment("name", "camera", "first model", 10, "fjoliewsfuhijoeoifjhwioejfw");
        equipment = equipmentRepository.save(equipment);
        EquipmentAtSwitch equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch, 1);
        equipmentAtSwitchRepository.save(equipmentAtSwitch);

        equipment = new Equipment("name", "camera", "second model", 10, "fjoliewsfuhijoeoifjhwioejfw");
        equipment = equipmentRepository.save(equipment);
        equipmentAtSwitch = new EquipmentAtSwitch(equipment, aSwitch, 2);
        equipmentAtSwitchRepository.save(equipmentAtSwitch);
    }
}