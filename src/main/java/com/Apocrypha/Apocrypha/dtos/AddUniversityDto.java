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
public class AddUniversityDto {
    @NotNull
    private String recomandedFormula;
    @NotNull
    private String creationDate;
    private String nameOfRecomandation;
    private String description;
    private float durationOfStudy;
    private String titleOfStudy;
    private String Location;

}
