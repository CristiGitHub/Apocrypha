package com.Apocrypha.Apocrypha.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddJobDto {
    @NotNull
    private String recomandedFormula;
    @NotNull
    private String nameOfRecomandation;
    private String description;
    @NotNull
    private String Location;
    @NotNull
    private String jobType;
    private String mostImportantSkill;

}
