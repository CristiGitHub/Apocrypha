package com.Apocrypha.Apocrypha.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("3")
public class UniversityRecommendation extends Recommendation {
    private float durationOfStudy;
    private String titleOfStudy;
    private String Location;

    @Builder
    public UniversityRecommendation(String recomandedFormula, String nameOfRecomandation, String description, Date creationDate,
                                    float durationOfStudy, String titleOfStudy, String Location) {
        super(recomandedFormula, nameOfRecomandation, description, creationDate);
        this.durationOfStudy = durationOfStudy;
        this.Location = Location;
        this.titleOfStudy = titleOfStudy;
    }

}
