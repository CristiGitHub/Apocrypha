package com.Apocrypha.Apocrypha.service;

import com.Apocrypha.Apocrypha.dtos.AddSkillDto;
import com.Apocrypha.Apocrypha.exceptions.SkillAlreadyExistException;
import com.Apocrypha.Apocrypha.models.AvailableSkills;
import com.Apocrypha.Apocrypha.repository.AvailableSkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminProfessionOrientationService {
    private final AvailableSkillsRepository availableSkillsRepository;
    @Autowired
    public AdminProfessionOrientationService(AvailableSkillsRepository availableSkillsRepository){
        this.availableSkillsRepository=availableSkillsRepository;
    }

    public void addSkill(AddSkillDto addSkillDto) throws SkillAlreadyExistException{
        if(availableSkillsRepository.existsBySkillName(addSkillDto.getSkillName()) || addSkillDto.getSkillName().equals("") ||
        addSkillDto.getNumberCalc().equals(""))
            throw new SkillAlreadyExistException(addSkillDto.getSkillName());
            // Nu mai adaug momentan inca un Exception pt SkillName / NumberCalc == ""
            AvailableSkills availableSkills = AvailableSkills.builder()
                    .skillName(addSkillDto.getSkillName())
                    .numberCalc(addSkillDto.getNumberCalc())
                    .build();
            availableSkillsRepository.save(availableSkills);

    }
    public void changeNumberCalc(AddSkillDto addSkillDto) throws SkillAlreadyExistException{
        if(!availableSkillsRepository.existsBySkillName(addSkillDto.getSkillName()))
            throw new SkillAlreadyExistException(addSkillDto.getSkillName());
            availableSkillsRepository.updateAvailableSkillsNumberCalc(addSkillDto.getNumberCalc(),addSkillDto.getSkillName());
    }

}
