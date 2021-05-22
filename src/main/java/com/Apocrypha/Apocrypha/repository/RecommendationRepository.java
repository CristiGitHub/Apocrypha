package com.Apocrypha.Apocrypha.repository;

import com.Apocrypha.Apocrypha.models.AvailableSkills;
import com.Apocrypha.Apocrypha.models.JobRecommendation;
import com.Apocrypha.Apocrypha.models.Recommendation;
import com.Apocrypha.Apocrypha.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.DELETE;
import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation , Long> {
Boolean existsByNameOfRecomandation(String nameOfRecomandation);
//    List<Recommendation> findALL();
//    List<Recommendation> findAllByNameOfRecomandationIsNotNull();
//    List<JobRecommendation> findAllBy;
//    ?
@Transactional
@DELETE
void deleteRecommendationByNameOfRecomandation(String NameOfRecomandation);
}
