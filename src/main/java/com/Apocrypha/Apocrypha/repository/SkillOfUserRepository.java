package com.Apocrypha.Apocrypha.repository;

import com.Apocrypha.Apocrypha.models.AvailableSkills;
import com.Apocrypha.Apocrypha.models.SkillOfUser;
import com.Apocrypha.Apocrypha.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.DELETE;
import java.util.List;
import java.util.Optional;

@Repository
public interface SkillOfUserRepository extends JpaRepository<SkillOfUser, Long> {
    Boolean existsByUserAndAvailableSkills(User user , AvailableSkills availableSkills);
    @Transactional
    @DELETE
    void deleteSkillOfUserByAvailableSkillsAndUser(AvailableSkills availableSkills , User user);
}
