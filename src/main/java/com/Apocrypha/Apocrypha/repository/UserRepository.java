package com.Apocrypha.Apocrypha.repository;

import com.Apocrypha.Apocrypha.models.SkillOfUser;
import com.Apocrypha.Apocrypha.models.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmailOrUsername(String email, String username);
    Boolean existsByUsername(String username);
    @Query("Select u from User u where u.username=:username")
    User findByUsername(@Param("username") String username);
    User findById(long id);
}
