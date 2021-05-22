package com.Apocrypha.Apocrypha.controller;

import com.Apocrypha.Apocrypha.dtos.AddSkillDto;
import com.Apocrypha.Apocrypha.exceptions.SkillAlreadyExistException;
import com.Apocrypha.Apocrypha.service.AdminProfessionOrientationService;
import com.Apocrypha.Apocrypha.utils.SuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/Proffession")
public class AdminProfessionOrientationController {

    private final AdminProfessionOrientationService adminProfessionOrientationService;

    @Autowired
    public AdminProfessionOrientationController(AdminProfessionOrientationService adminProfessionOrientationService){
        this.adminProfessionOrientationService = adminProfessionOrientationService;
    }

        @PostMapping("/addSkill")
                public ResponseEntity addSkill(@RequestBody AddSkillDto addSkillDto) {
            try{
                adminProfessionOrientationService.addSkill(addSkillDto);
                return new ResponseEntity<>(new SuccessDto(),HttpStatus.OK);
            }
            catch (SkillAlreadyExistException e){
                return new ResponseEntity<>("Skill already exists or it's Null", HttpStatus.CONFLICT);
            }
        }
       @PatchMapping("/changeNumberCalc")
            public ResponseEntity changeNumberCalc(@RequestBody AddSkillDto addSkillDto) {
            try{
                    adminProfessionOrientationService.changeNumberCalc(addSkillDto);
                return new ResponseEntity<>(new SuccessDto(),HttpStatus.OK);
            }
            catch (SkillAlreadyExistException e){
                return new ResponseEntity<>("Skill does not exists",HttpStatus.CONFLICT);
            }
       }
    }