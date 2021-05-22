package com.Apocrypha.Apocrypha.controller;

import com.Apocrypha.Apocrypha.dtos.AddSkillDto;
import com.Apocrypha.Apocrypha.dtos.UserAddSkillDto;
import com.Apocrypha.Apocrypha.exceptions.RecommendationAlreadyExists;
import com.Apocrypha.Apocrypha.exceptions.SkilldoesNotExists;
import com.Apocrypha.Apocrypha.exceptions.UserDoesNotExists;
import com.Apocrypha.Apocrypha.exceptions.UserHasAlreadyThatSkill;
import com.Apocrypha.Apocrypha.service.ProfessionOrientationService;
import com.Apocrypha.Apocrypha.utils.SuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Profession")
public class ProfessionOrientationController {

    private final ProfessionOrientationService professionOrientationService;

    @Autowired
    public ProfessionOrientationController(ProfessionOrientationService professionOrientationService){
        this.professionOrientationService = professionOrientationService;
    }

    @PostMapping("/addSkill")
    public ResponseEntity addSkill(@RequestBody UserAddSkillDto userAddSkillDto){
        try{
            professionOrientationService.userAddSkill(userAddSkillDto);
            return new ResponseEntity<>(new SuccessDto(),HttpStatus.OK);
        }
        catch (UserDoesNotExists e){
            return new ResponseEntity<>("User does not exists!", HttpStatus.CONFLICT);
        }
        catch (SkilldoesNotExists e){
            return new ResponseEntity<>("Skill does not exists!" , HttpStatus.CONFLICT);
        }
        catch (UserHasAlreadyThatSkill e){
            return new ResponseEntity<>("User has already that skill!" , HttpStatus.CONFLICT);
        }
    }
    @DeleteMapping("/{skill}")
    public ResponseEntity deleteSkill(@PathVariable String skill ,@RequestBody String username) {
        try {
            professionOrientationService.deleteSkillOfUser(username, skill);
            return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
        } catch (UserDoesNotExists e) {
            return new ResponseEntity<>("User does not exists!", HttpStatus.CONFLICT);
        } catch (SkilldoesNotExists e) {
            return new ResponseEntity<>("Skill does not exists!", HttpStatus.CONFLICT);
        } catch (UserHasAlreadyThatSkill e) {
            return new ResponseEntity<>("User does not have that skill!", HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/{username}")
    public ResponseEntity getSkillOfUser(@PathVariable String username){
        try{
            List<String> result = professionOrientationService.getAllSkillsOfUser(username);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
     catch (UserDoesNotExists e) {
        return new ResponseEntity<>("User does not exists!", HttpStatus.CONFLICT);
    } catch (SkilldoesNotExists e) {
        return new ResponseEntity<>("Skill does not exists!", HttpStatus.CONFLICT);
    }
    }
    @GetMapping("/Courses/{username}")
    public ResponseEntity getCourseOfUser(@PathVariable String username){
        try{
            List<String> result = professionOrientationService.getCoursesOfUser(username);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (UserDoesNotExists e) {
            return new ResponseEntity<>("User does not exists!", HttpStatus.CONFLICT);
        } catch (RecommendationAlreadyExists e) {
            return new ResponseEntity<>("There are no courses published by this user", HttpStatus.CONFLICT);
        }
    }

}
