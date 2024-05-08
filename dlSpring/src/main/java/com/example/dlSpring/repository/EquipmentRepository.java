package com.example.dlSpring.repository;

import com.example.dlSpring.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository  extends JpaRepository<Equipment, Long> {
}
