package com.example.dlSpring.repository;

import com.example.dlSpring.model.EquipmentAtSwitch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentAtSwitchRepository extends JpaRepository<EquipmentAtSwitch, Long> {
}
