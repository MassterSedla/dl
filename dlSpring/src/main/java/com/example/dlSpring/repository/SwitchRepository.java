package com.example.dlSpring.repository;

import com.example.dlSpring.model.Switch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SwitchRepository extends JpaRepository<Switch, Long> {
    @Query("select s.building from Switch s")
    Set<String> findAllBuilding();

    @Query("select s.room from Switch s where s.building = :building")
    Set<String> findAllRoomsByBuilding(String building);

    @Query("select s.number from Switch s where s.building = :building and s.room = :room")
    Set<String> findAllNumberByBuildingAndRoom(String building, String room);

    Switch findByBuildingAndRoomAndNumber(String building, String room, int number);

}
