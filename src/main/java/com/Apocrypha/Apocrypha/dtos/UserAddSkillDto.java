package com.Apocrypha.Apocrypha.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAddSkillDto {
    @NotNull
    private String username;
    @NotNull
    private String skillName;
}
