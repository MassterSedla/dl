package com.example.dlSpring.repository;

import com.example.dlSpring.model.Switch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Repository
public interface SwitchRepository extends JpaRepository<Switch, Long> {
    @Query("select s.building, s.floor from Switch s")
    Set<String> findAllBuilding();

    @Query("select s.room from Switch s where s.building = :building and s.floor = :floor")
    Set<String> findAllRoomsByBuildingAndFloor(String building, int floor);

    @Query("select s.number, s.type, s.building, s.room, s.permissiblePowerLoad, s.permissibleTrafficLoad " +
            "from Switch s")
    List<List<String>> findWithoutEquipment();
}
