package com.Apocrypha.Apocrypha.repository;

import com.Apocrypha.Apocrypha.models.UniversityRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRecommendationRepository extends JpaRepository<UniversityRecommendation, Long> {
    List<UniversityRecommendation> findAll();
}
