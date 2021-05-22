package com.Apocrypha.Apocrypha.service;

import com.Apocrypha.Apocrypha.dtos.AddCourseRecomendationDto;
import com.Apocrypha.Apocrypha.dtos.AddJobDto;
import com.Apocrypha.Apocrypha.dtos.AddUniversityDto;
import com.Apocrypha.Apocrypha.exceptions.*;
import com.Apocrypha.Apocrypha.models.*;
import com.Apocrypha.Apocrypha.repository.AvailableSkillsRepository;
import com.Apocrypha.Apocrypha.repository.CourseRecommendationRepository;
import com.Apocrypha.Apocrypha.repository.RecommendationRepository;
import com.Apocrypha.Apocrypha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class AdminRecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final AvailableSkillsRepository availableSkillsRepository;
    private final CourseRecommendationRepository courseRecommendationRepository;
    private final UserRepository userRepository;
    @Autowired
    public AdminRecommendationService(RecommendationRepository recommendationRepository , AvailableSkillsRepository availableSkillsRepository
    , UserRepository userRepository , CourseRecommendationRepository courseRecommendationRepository){
        this.recommendationRepository=recommendationRepository;
        this.availableSkillsRepository=availableSkillsRepository;
        this.userRepository=userRepository;
        this.courseRecommendationRepository =courseRecommendationRepository;
    }
    public void addJob(AddJobDto addJobDto) throws SkilldoesNotExists , RecommendationAlreadyExists{
        if(!availableSkillsRepository.existsBySkillName(addJobDto.getMostImportantSkill()))
            throw new SkilldoesNotExists(addJobDto.getMostImportantSkill());
        if(recommendationRepository.existsByNameOfRecomandation(addJobDto.getNameOfRecomandation()))
            throw new RecommendationAlreadyExists(addJobDto.getNameOfRecomandation());
        AvailableSkills availableSkills = getSkillByName(addJobDto.getMostImportantSkill());
        JobRecommendation jobRecommendation = JobRecommendation.builder()
                .jobType(addJobDto.getJobType())
                .recomandedFormula(addJobDto.getRecomandedFormula())
                .mostImportantSkill(availableSkills)
                .Location(addJobDto.getLocation())
                .description(addJobDto.getDescription())
                .nameOfRecomandation(addJobDto.getNameOfRecomandation())
                .creationDate(new Date())
                .build();
        recommendationRepository.save(jobRecommendation);

    }
    public void addUniversity(AddUniversityDto addUniversityDto) throws RecommendationAlreadyExists, ParseException {
        if(recommendationRepository.existsByNameOfRecomandation(addUniversityDto.getNameOfRecomandation()))
            throw new RecommendationAlreadyExists(addUniversityDto.getNameOfRecomandation());

        Date data = new SimpleDateFormat("dd/MM/yyyy").parse(addUniversityDto.getCreationDate());
        UniversityRecommendation universityRecommendation = UniversityRecommendation.builder()
                .titleOfStudy(addUniversityDto.getTitleOfStudy())
                .creationDate(data)
                .description(addUniversityDto.getDescription())
                .Location(addUniversityDto.getLocation())
                .recomandedFormula(addUniversityDto.getRecomandedFormula())
                .nameOfRecomandation(addUniversityDto.getNameOfRecomandation())
                .durationOfStudy(addUniversityDto.getDurationOfStudy())
                .build();
        recommendationRepository.save(universityRecommendation);
    }

    public void createCourse(AddCourseRecomendationDto addCourseRecomendationDto , String username) throws UserDoesNotExists, RecommendationAlreadyExists{
        if(!userRepository.existsByUsername(username))
            throw new UserDoesNotExists(username);
        if(recommendationRepository.existsByNameOfRecomandation(addCourseRecomendationDto.getNameOfRecomandation()))
            throw new RecommendationAlreadyExists(addCourseRecomendationDto.getNameOfRecomandation());
        User user = userRepository.findByUsername(username);
        CourseRecommendation courseRecommendation = CourseRecommendation.builder()
                .authorOfCourse(user)
                .contentOfCourse(addCourseRecomendationDto.getContentOfCourse())
                .awardPoints(addCourseRecomendationDto.getAwardPoints())
                .ItsConfirmed(true)
                .creationDate(new Date())
                .description(addCourseRecomendationDto.getDescription())
                .nameOfRecomandation(addCourseRecomendationDto.getNameOfRecomandation())
                .recomandedFormula(addCourseRecomendationDto.getRecomandedFormula())
                .build();
        recommendationRepository.save(courseRecommendation);


    }
    public void confirmACourse(String couserName) throws RecommendationAlreadyExists,TheCourseIsAlreadyConfirmedException,ThisIsNotACourseException{
        if(!recommendationRepository.existsByNameOfRecomandation(couserName))
            throw new RecommendationAlreadyExists(couserName);
        Optional<CourseRecommendation> courseRecommendationOptional = courseRecommendationRepository.findCourseRecommendationByNameOfRecomandation(couserName);
        if(courseRecommendationOptional.isEmpty())
            throw new ThisIsNotACourseException(couserName);
        if(courseRecommendationOptional.get().getItsConfirmed())
            throw new TheCourseIsAlreadyConfirmedException(couserName);
        courseRecommendationRepository.updateAvailableSkillsNumberCalc(true,couserName);
    }
    public void deleteRecommendation(String name) throws RecommendationAlreadyExists{
        if(!recommendationRepository.existsByNameOfRecomandation(name))
            throw new RecommendationAlreadyExists(name);
        recommendationRepository.deleteRecommendationByNameOfRecomandation(name);
    }



    public AvailableSkills getSkillByName(String name){
        return availableSkillsRepository.findAvailableSkillsBySkillName(name);
    }


}
