package com.example.dlSpring.repository;

import com.example.dlSpring.model.Role;
import com.example.dlSpring.model.Switch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwitchRepository extends JpaRepository<Switch, Long> {
}
