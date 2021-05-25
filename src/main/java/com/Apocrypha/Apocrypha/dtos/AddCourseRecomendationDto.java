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
public class AddCourseRecomendationDto {
    @NotNull
    private String recomandedFormula;
    @NotNull
    private String nameOfRecomandation;
    private String description;

    private int awardPoints;

    private String contentOfCourse;
}
