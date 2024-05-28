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
    void deleteByaSwitch_IdAndPort(Long switchId, int port);

    EquipmentAtSwitch findByaSwitch_IdAndPort(Long switchId, int port);

    @Query("select e.comments from EquipmentAtSwitch e where e.aSwitch.id = :aSwitch_id and e.port = :port")
    String findCommentsByaSwitch_IdAndPort(Long aSwitch_id, int port);

}
