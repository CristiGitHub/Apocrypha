package com.Apocrypha.Apocrypha.repository;

import com.Apocrypha.Apocrypha.models.AvailableSkills;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AvailableSkillsRepository extends JpaRepository<AvailableSkills, Long> {
    Boolean existsBySkillName(String skillName);

    @Query("Select u FROM AvailableSkills u WHERE u.skillName = :skillName")
    AvailableSkills findAvailableSkillsBySkillName(@Param("skillName") String skillName);

    @Transactional
    @Modifying
    @Query("UPDATE AvailableSkills SET numberCalc=:numberCalc where skillName = :skillName")
    void  updateAvailableSkillsNumberCalc(@Param("numberCalc") String numberCalc , @Param("skillName") String skillName);

}
