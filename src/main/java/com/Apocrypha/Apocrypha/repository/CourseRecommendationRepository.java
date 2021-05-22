package com.Apocrypha.Apocrypha.repository;

import com.Apocrypha.Apocrypha.models.CourseRecommendation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRecommendationRepository extends JpaRepository<CourseRecommendation ,Long> {
    List<CourseRecommendation> findAll();
    @Transactional
    @Modifying
    @Query("UPDATE CourseRecommendation SET ItsConfirmed=:ItsConfirmed where nameOfRecomandation = :nameOfRecomandation")
    void  updateAvailableSkillsNumberCalc(@Param("ItsConfirmed") boolean ItsConfirmed , @Param("nameOfRecomandation") String nameOfRecomandation);
    Optional<CourseRecommendation> findCourseRecommendationByNameOfRecomandation(String courseName);
}
