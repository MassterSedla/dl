package com.example.dlSpring.repository;

import com.example.dlSpring.model.EquipmentAtSwitch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EquipmentAtSwitchRepository extends JpaRepository<EquipmentAtSwitch, Long> {

    @Modifying
    @Transactional
    @Query("delete from EquipmentAtSwitch s where s.aSwitch.id = :switchId and  s.port = :port")
    void deleteBySwitchIdAndPort(Long switchId, int port);
}
