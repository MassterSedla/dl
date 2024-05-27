package com.example.dlSpring.repository;

import com.example.dlSpring.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EquipmentRepository  extends JpaRepository<Equipment, Long> {
    @Query("select e.type from Equipment e")
    Set<String> findAllType();

    @Query("select e.company from Equipment e where e.type = :type")
    Set<String> findAllCompanyByType(String type);

    @Query("select e.model from Equipment e where e.type = :type and e.company = :company")
    Set<String> findAllModelByTypeAndCompany(String type, String company);

    @Query("select e.id from Equipment e where e.type = :type and e.company = :company and e.model = :model")
    Long findEquipmentIdByTypeAndCompanyAndModel(String type, String company, String model);


}
