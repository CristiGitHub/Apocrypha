package com.Apocrypha.Apocrypha.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.UniqueConstraint;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSkillDto {
    @NotNull
    private String skillName;
    @NotNull
    private String numberCalc;
}
