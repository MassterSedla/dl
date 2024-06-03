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
        if (userRepository.findByUsername("user") == null) {
            User userUser = new User("user", "user", "user", "user@mail.ru", passwordEncoder.encode("user"), userRoles);
            userRepository.save(userUser);
        }
        if (userRepository.findByUsername("Ivanov Ivan") == null) {
            User userUser = new User("Ivanov Ivan", "Ivan", "Ivanov", "ivanov@gmail.com", passwordEncoder.encode("ivanov"), userRoles);
            userRepository.save(userUser);
        }

//        Equipment equipment1 = equipmentRepository.save(new Equipment("camera", "company 1", "1 model", 10, 8));
//        Equipment equipment2 = equipmentRepository.save(new Equipment("camera", "company 2","2 model", 12, 7));
//        Equipment equipment3 = equipmentRepository.save(new Equipment("camera", "company 13", "3 model", 14, 9));
//        Equipment equipment4 = equipmentRepository.save(new Equipment("camera", "company 15","4 model", 15, 10));
//
//        Switch switch1 = switchRepository.save(new Switch(4, "first", "1",  "D.2.021", 200, 200, "1111.1111.1111.1111", 24));
//        Switch switch2 = switchRepository.save(new Switch(14, "first", "1",  "D.3.044", 200, 200, "1211.1111.1111.1111",24));
//        Switch switch3 = switchRepository.save(new Switch(13, "first", "2",  "D.1.007", 200, 200, "1111.1111.1111.1111",24));
//        Switch switch4 = switchRepository.save(new Switch(16, "first", "3",  "D.1.027", 200, 200, "1111.1111.1111.1111",24));
//        Switch switch5 = switchRepository.save(new Switch(16, "first", "2",  "C.3.056", 200, 200, "1111.1111.1111.1111",24));
//        Switch switch6 = switchRepository.save(new Switch(11, "first", "2",  "C.1.033", 200, 200, "1111.1111.1111.1111",24));
//        Switch switch7 = switchRepository.save(new Switch(16, "first", "1",  "C.1.034", 200, 200, "1111.1111.1111.1111",24));
//        Switch switch8 = switchRepository.save(new Switch(19, "first", "3",  "B.4.025", 200, 200, "1111.1111.1111.1111",24));
//        Switch switch9 = switchRepository.save(new Switch(17, "first", "2",  "B.3.041", 200, 200, "1111.1111.1111.1111",24));
//        Switch switch0 = switchRepository.save(new Switch(12, "first", "3",  "B.1.013", 200, 200, "1111.1111.1111.1111",24));

    }
}