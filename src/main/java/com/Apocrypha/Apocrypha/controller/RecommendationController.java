package com.Apocrypha.Apocrypha.controller;

import com.Apocrypha.Apocrypha.dtos.AddCourseRecomendationDto;
import com.Apocrypha.Apocrypha.exceptions.RecommendationAlreadyExists;
import com.Apocrypha.Apocrypha.exceptions.UserDoesNotExists;
import com.Apocrypha.Apocrypha.exceptions.UserHasNoSkills;
import com.Apocrypha.Apocrypha.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("")
    public ResponseEntity getAllRecomendation(@RequestBody String username) {
        try {
            List<String> result = recommendationService.getAllRecommendations(username);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RecommendationAlreadyExists e) {
            return new ResponseEntity<>("You have no recomendations!", HttpStatus.CONFLICT);
        } catch (UserHasNoSkills e) {
            return new ResponseEntity<>("Please add some Personalize your profile fisrt of all!", HttpStatus.CONFLICT);
        } catch (UserDoesNotExists e) {
            return new ResponseEntity<>("User does not exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/All")
    public ResponseEntity getAll() {
        try {
            List<String> result = recommendationService.getAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RecommendationAlreadyExists e) {
            return new ResponseEntity<>("There is no content!", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/AddCourse/{username}")
    public ResponseEntity createCourse(@RequestBody AddCourseRecomendationDto addCourseRecomendationDto, @PathVariable String username) {
        try {
            recommendationService.createCourse(addCourseRecomendationDto, username);
            return new ResponseEntity<>("The course will be examined and added in short bit!", HttpStatus.OK);
        } catch (UserDoesNotExists e) {
            return new ResponseEntity<>("The user does not exists", HttpStatus.CONFLICT);
        } catch (RecommendationAlreadyExists e) {
            return new ResponseEntity<>("The course Alrready exists!", HttpStatus.CONFLICT);
        }
    }

}
