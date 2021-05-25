package com.Apocrypha.Apocrypha.service;

import com.Apocrypha.Apocrypha.dtos.UserAddSkillDto;
import com.Apocrypha.Apocrypha.exceptions.RecommendationAlreadyExists;
import com.Apocrypha.Apocrypha.exceptions.SkilldoesNotExists;
import com.Apocrypha.Apocrypha.exceptions.UserDoesNotExists;
import com.Apocrypha.Apocrypha.exceptions.UserHasAlreadyThatSkill;
import com.Apocrypha.Apocrypha.models.AvailableSkills;
import com.Apocrypha.Apocrypha.models.CourseRecommendation;
import com.Apocrypha.Apocrypha.models.SkillOfUser;
import com.Apocrypha.Apocrypha.models.User;
import com.Apocrypha.Apocrypha.repository.AvailableSkillsRepository;
import com.Apocrypha.Apocrypha.repository.SkillOfUserRepository;
import com.Apocrypha.Apocrypha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessionOrientationService {

    private final SkillOfUserRepository skillOfUserRepository;
    private final UserRepository userRepository;
    private final AvailableSkillsRepository availableSkillsRepository;

    @Autowired
    public ProfessionOrientationService(SkillOfUserRepository skillOfUserRepository, UserRepository userRepository,
                                        AvailableSkillsRepository availableSkillsRepository) {
        this.availableSkillsRepository = availableSkillsRepository;
        this.userRepository = userRepository;
        this.skillOfUserRepository = skillOfUserRepository;
    }

    public void userAddSkill(UserAddSkillDto userAddSkillDto) throws UserDoesNotExists, SkilldoesNotExists, UserHasAlreadyThatSkill {
        if (!userRepository.existsByUsername(userAddSkillDto.getUsername()))
            throw new UserDoesNotExists(userAddSkillDto.getUsername());

        if (!availableSkillsRepository.existsBySkillName(userAddSkillDto.getSkillName()))
            throw new SkilldoesNotExists(userAddSkillDto.getSkillName());
        User user = getUserByUsername(userAddSkillDto.getUsername());
        AvailableSkills availableSkills = getAvailableSkillBySkillName(userAddSkillDto.getSkillName());
        if (skillOfUserRepository.existsByUserAndAvailableSkills(user, availableSkills))
            throw new UserHasAlreadyThatSkill(userAddSkillDto.getUsername());
        SkillOfUser skillOfUser = SkillOfUser.builder()
                .availableSkills(availableSkills)
                .user(user)
                .build();
        skillOfUserRepository.save(skillOfUser);
    }

    public void deleteSkillOfUser(String username, String skillname) throws UserDoesNotExists, SkilldoesNotExists, UserHasAlreadyThatSkill {
        if (!userRepository.existsByUsername(username))
            throw new UserDoesNotExists(username);
        if (!availableSkillsRepository.existsBySkillName(skillname))
            throw new SkilldoesNotExists(skillname);
        if (!skillOfUserRepository.existsByUserAndAvailableSkills(getUserByUsername(username), getAvailableSkillBySkillName(skillname)))
            throw new UserHasAlreadyThatSkill(username);
        skillOfUserRepository.deleteSkillOfUserByAvailableSkillsAndUser(getAvailableSkillBySkillName(skillname), getUserByUsername(username));
    }

    public List<String> getAllSkillsOfUser(String username) throws UserDoesNotExists, SkilldoesNotExists {
        if (!userRepository.existsByUsername(username))
            throw new UserDoesNotExists(username);
        User user = getUserByUsername(username);
        List<SkillOfUser> skillOfUserList = user.getSkillOfUsers();
        if (skillOfUserList.size() == 0)
            throw new SkilldoesNotExists(username);
        List<String> result = new ArrayList<String>();
        for (SkillOfUser skillOfUser : skillOfUserList) {
            result.add(skillOfUser.getAvailableSkills().getSkillName());
        }
        return result;

    }

    public List<String> getCoursesOfUser(String username) throws UserDoesNotExists, RecommendationAlreadyExists {
        if (!userRepository.existsByUsername(username))
            throw new UserDoesNotExists(username);
        User user = getUserByUsername(username);
        List<CourseRecommendation> courseRecommendationList = user.getCourseRecommendations();
        List<String> result = new ArrayList<String>();
        for (CourseRecommendation cor : courseRecommendationList) {
            result.add(cor.getNameOfRecomandation());
        }
        if (result.size() == 0)
            throw new RecommendationAlreadyExists("nu ai da ce esti scriitor?");
        return result;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        //user.setPassword("");
        return user;
    }

    public AvailableSkills getAvailableSkillBySkillName(String skillname) {
        AvailableSkills availableSkills = availableSkillsRepository.findAvailableSkillsBySkillName(skillname);
        //availableSkills.setNumberCalc("");
        return availableSkills;
    }

}
