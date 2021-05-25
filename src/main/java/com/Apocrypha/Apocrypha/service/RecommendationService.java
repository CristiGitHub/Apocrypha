package com.Apocrypha.Apocrypha.service;

import com.Apocrypha.Apocrypha.dtos.AddCourseRecomendationDto;
import com.Apocrypha.Apocrypha.exceptions.RecommendationAlreadyExists;
import com.Apocrypha.Apocrypha.exceptions.UserDoesNotExists;
import com.Apocrypha.Apocrypha.exceptions.UserHasNoSkills;
import com.Apocrypha.Apocrypha.models.*;
import com.Apocrypha.Apocrypha.repository.*;
import com.Apocrypha.Apocrypha.vos.UsersValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final JobRecommendationRepository jobRecommendationRepository;
    private final CourseRecommendationRepository courseRecommendationRepository;
    private final UniversityRecommendationRepository universityRecommendationRepository;
    private final SkillOfUserRepository skillOfUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository, UserRepository userRepository,
                                 JobRecommendationRepository jobRecommendationRepository, CourseRecommendationRepository courseRecommendationRepository
            , UniversityRecommendationRepository universityRecommendationRepository, SkillOfUserRepository skillOfUserRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.jobRecommendationRepository = jobRecommendationRepository;
        this.courseRecommendationRepository = courseRecommendationRepository;
        this.universityRecommendationRepository = universityRecommendationRepository;
        this.skillOfUserRepository = skillOfUserRepository;

    }

    public void createCourse(AddCourseRecomendationDto addCourseRecomendationDto, String username) throws UserDoesNotExists, RecommendationAlreadyExists {
        if (!userRepository.existsByUsername(username))
            throw new UserDoesNotExists(username);
        if (recommendationRepository.existsByNameOfRecomandation(addCourseRecomendationDto.getNameOfRecomandation()))
            throw new RecommendationAlreadyExists(addCourseRecomendationDto.getNameOfRecomandation());
        User user = userRepository.findByUsername(username);
        CourseRecommendation courseRecommendation = CourseRecommendation.builder()
                .authorOfCourse(user)
                .contentOfCourse(addCourseRecomendationDto.getContentOfCourse())
                .awardPoints(addCourseRecomendationDto.getAwardPoints())
                .ItsConfirmed(false)
                .creationDate(new Date())
                .description(addCourseRecomendationDto.getDescription())
                .nameOfRecomandation(addCourseRecomendationDto.getNameOfRecomandation())
                .recomandedFormula(addCourseRecomendationDto.getRecomandedFormula())
                .build();
        courseRecommendationRepository.save(courseRecommendation);


    }

    public List<String> getAll() throws RecommendationAlreadyExists {
        List<Recommendation> recommendationList = new ArrayList<Recommendation>();
        for (JobRecommendation job : jobRecommendationRepository.findAll()) {
            recommendationList.add(job);
        }
        for (UniversityRecommendation univ : universityRecommendationRepository.findAll()) {
            recommendationList.add(univ);
        }
        for (CourseRecommendation cours : courseRecommendationRepository.findAll()) {
            recommendationList.add(cours);
        }
        if (recommendationList.size() == 0)
            throw new RecommendationAlreadyExists("NU EXISTA");
        List<String> result = new ArrayList<String>();
        for (Recommendation recommendation : recommendationList) {
            result.add(recommendation.getNameOfRecomandation());
        }
        return result;
    }

    public List<String> getAllRecommendations(String username) throws UserDoesNotExists, UserHasNoSkills, RecommendationAlreadyExists {
        if (!userRepository.existsByUsername(username))
            throw new UserDoesNotExists(username);
        User user = userRepository.findByUsername(username);
        List<SkillOfUser> skillOfUser = user.getSkillOfUsers();
        if (skillOfUser.size() == 0)
            throw new UserHasNoSkills(username);

        String valueToParse = "";
        for (SkillOfUser skills : skillOfUser) {
            valueToParse += skills.getAvailableSkills().getNumberCalc();
            valueToParse += ",";

        }
        UsersValues usersValues = parseStringOfFormula(valueToParse);
        List<Recommendation> recommendationList = new ArrayList<Recommendation>();
        for (JobRecommendation job : jobRecommendationRepository.findAll()) {
            recommendationList.add(job);
        }
        for (UniversityRecommendation univ : universityRecommendationRepository.findAll()) {
            recommendationList.add(univ);
        }
        for (CourseRecommendation cours : courseRecommendationRepository.findAll()) {
            recommendationList.add(cours);
        }

        List<Map.Entry<Recommendation, Double>> lista = new ArrayList<Map.Entry<Recommendation, Double>>();
        for (Recommendation recommendation : recommendationList) {
            if (isItRecommended(usersValues, parseStringOfFormula(recommendation.getRecomandedFormula()))) {
                if (recommendation instanceof JobRecommendation) {
                    JobRecommendation jb = (JobRecommendation) recommendation;
                    if (skillOfUserRepository.existsByUserAndAvailableSkills(user, jb.getMostImportantSkill()))
                        lista.add(Map.entry(recommendation, recommendationCal(usersValues, parseStringOfFormula(recommendation.getRecomandedFormula()))));

                } else if (recommendation instanceof CourseRecommendation) {
                    CourseRecommendation cor = (CourseRecommendation) recommendation;
                    if (cor.getItsConfirmed())
                        lista.add(Map.entry(recommendation, recommendationCal(usersValues, parseStringOfFormula(recommendation.getRecomandedFormula()))));
                } else
                    lista.add(Map.entry(recommendation, recommendationCal(usersValues, parseStringOfFormula(recommendation.getRecomandedFormula()))));
            }
        }
        Collections.sort(
                lista
                , new Comparator<Map.Entry<Recommendation, Double>>() {
                    public int compare(Map.Entry<Recommendation, Double> a, Map.Entry<Recommendation, Double> b) {
                        return Double.compare(a.getValue(), b.getValue());
                    }
                }
        );
        List<String> result = new ArrayList<String>();
        for (Map.Entry<Recommendation, Double> e : lista) {
            result.add(e.getKey().getNameOfRecomandation());
        }
        if (result.size() == 0)
            throw new RecommendationAlreadyExists("nus");
        return result;
    }

    public UsersValues parseStringOfFormula(String formula) {
        int m = 0, f = 0, i = 0;
        if (formula.contains(",")) {
            String[] token = formula.split(",");
            for (String st : token) {
                Pair<Integer, Type> pair = parseLilString(st);
                switch (pair.getSecond()) {
                    case INF:
                        i += pair.getFirst();
                    case MATH:
                        m += pair.getFirst();
                    case PHI:
                        f += pair.getFirst();
                }

            }
        } else {
            Pair<Integer, Type> pair = parseLilString(formula);
            switch (pair.getSecond()) {
                case INF:
                    i += pair.getFirst();
                case MATH:
                    m += pair.getFirst();
                case PHI:
                    f += pair.getFirst();
            }
        }
        return UsersValues.builder().Informatics(i).Mathematics(m).Physics(f).build();
    }

    public Pair<Integer, Type> parseLilString(String lilString) {
        Type type = Type.Nul;
        if (lilString.contains("I"))
            type = Type.INF;
        if (lilString.contains("M"))
            type = Type.MATH;
        if (lilString.contains("F"))
            type = Type.PHI;
        lilString = lilString.replaceAll("\\D+", "");
        int value = Integer.parseInt(lilString);
        return Pair.of(value, type);
    }

    public Boolean isItRecommended(UsersValues usersValues, UsersValues recommendationValues) {
        if (usersValues.getInformatics() < recommendationValues.getInformatics())
            return false;
        if (usersValues.getMathematics() < recommendationValues.getMathematics())
            return false;
        return usersValues.getPhysics() >= recommendationValues.getPhysics();
    }

    public Double recommendationCal(UsersValues usersValues, UsersValues recommendationValues) {
        return Math.sqrt(
                (usersValues.getInformatics() - recommendationValues.getInformatics()) * (usersValues.getInformatics() - recommendationValues.getInformatics())
                        + (usersValues.getMathematics() - recommendationValues.getMathematics()) * (usersValues.getMathematics() - recommendationValues.getMathematics())
                        + (usersValues.getPhysics() - recommendationValues.getPhysics()) * (usersValues.getPhysics() - recommendationValues.getPhysics())
        );
    }

    private enum Type {
        MATH, INF, PHI, Nul
    }
}
