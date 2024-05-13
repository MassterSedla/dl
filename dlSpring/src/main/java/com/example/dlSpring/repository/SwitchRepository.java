package com.example.dlSpring.repository;

import com.example.dlSpring.model.Switch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwitchRepository extends JpaRepository<Switch, Long> {
    @Query("select s.room from Switch s where s.building = :building")
    List<String> findAllRoomsByBuilding(String building);

    @Query("select s.number, s.type, s.building, s.room, s.permissiblePowerLoad, s.permissibleTrafficLoad " +
            "from Switch s")
    List<List<String>> findWithoutEquipment();
}
