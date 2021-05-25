package com.Apocrypha.Apocrypha.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("1")
public class JobRecommendation extends Recommendation {
    private String Location;
    private String jobType;
    @OneToOne
    private AvailableSkills mostImportantSkill;

    @Builder
    public JobRecommendation(String recomandedFormula, String nameOfRecomandation, String description, Date creationDate,
                             String Location, String jobType, AvailableSkills mostImportantSkill) {
        super(recomandedFormula, nameOfRecomandation, description, creationDate);
        this.jobType = jobType;
        this.Location = Location;
        this.mostImportantSkill = mostImportantSkill;

    }

}
