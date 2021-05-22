package com.Apocrypha.Apocrypha.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    private String password;

    @Column(name = "username", unique = true)
    private String username;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "user")
    private List<SkillOfUser> skillOfUsers= new ArrayList<SkillOfUser>();
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "authorOfCourse")
    private List<CourseRecommendation> courseRecommendations = new ArrayList<CourseRecommendation>();


}
