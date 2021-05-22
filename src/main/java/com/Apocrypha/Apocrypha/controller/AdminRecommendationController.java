package com.Apocrypha.Apocrypha.controller;

import com.Apocrypha.Apocrypha.dtos.AddCourseRecomendationDto;
import com.Apocrypha.Apocrypha.dtos.AddJobDto;
import com.Apocrypha.Apocrypha.dtos.AddSkillDto;
import com.Apocrypha.Apocrypha.dtos.AddUniversityDto;
import com.Apocrypha.Apocrypha.exceptions.*;
import com.Apocrypha.Apocrypha.repository.RecommendationRepository;
import com.Apocrypha.Apocrypha.service.AdminRecommendationService;
import com.Apocrypha.Apocrypha.utils.SuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/admin/Recommendation")
public class AdminRecommendationController {
    private final AdminRecommendationService adminRecommendationService;

    @Autowired
    public AdminRecommendationController(AdminRecommendationService adminRecommendationService)
    {
        this.adminRecommendationService =adminRecommendationService;
    }

    @PostMapping("/addJob")
    public ResponseEntity addJob(@RequestBody AddJobDto addJobDto) {
        try{
            adminRecommendationService.addJob(addJobDto);
            return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
        }
        catch (SkilldoesNotExists e){
            return new ResponseEntity<>("Skill does not exists!", HttpStatus.CONFLICT);
        }
        catch (RecommendationAlreadyExists e){
            return new ResponseEntity<>("The name of Job is taken!", HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/addUniversity")
    public ResponseEntity addUniversity(@RequestBody AddUniversityDto addUniversityDto){
        try {
            adminRecommendationService.addUniversity(addUniversityDto);
            return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
        }
        catch (RecommendationAlreadyExists e){
            return new ResponseEntity<>("The University already exists!", HttpStatus.CONFLICT);
        }
        catch (ParseException e){
            return new ResponseEntity<>("The data format is wrong!", HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/addCourse/{username}")
    public ResponseEntity addCourse(@RequestBody AddCourseRecomendationDto addCourseRecomendationDto , @PathVariable String username){
        try{
            adminRecommendationService.createCourse(addCourseRecomendationDto,username);
            return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
        }
        catch (UserDoesNotExists e){
            return new ResponseEntity<>("The user does not exists", HttpStatus.CONFLICT);
        }
        catch (RecommendationAlreadyExists e){
            return new ResponseEntity<>("The course already exists", HttpStatus.CONFLICT);
        }
    }
    @PatchMapping("/{courseName}")
    public ResponseEntity confirmCourse(@PathVariable String courseName){
        try{
            adminRecommendationService.confirmACourse(courseName);
            return new ResponseEntity<>(new SuccessDto() ,HttpStatus.OK);
        } catch (RecommendationAlreadyExists recommendationAlreadyExists) {
            return new ResponseEntity<>("There are no courses with this name", HttpStatus.CONFLICT);
        } catch (ThisIsNotACourseException e) {
            return new ResponseEntity<>("This is not a course ", HttpStatus.CONFLICT);
        } catch (TheCourseIsAlreadyConfirmedException e) {
            return new ResponseEntity<>("The course already confirmed", HttpStatus.CONFLICT);
        }

    }
    @DeleteMapping("/{recommendation}")
    public ResponseEntity deleteRecommendation(@PathVariable String recommendation){
        try{
            adminRecommendationService.deleteRecommendation(recommendation);
            return new ResponseEntity<>(new SuccessDto() ,HttpStatus.OK);
        }
        catch (RecommendationAlreadyExists e){
            return new ResponseEntity<>("The recommendation does not exists!" , HttpStatus.CONFLICT);
        }
    }
}
