package com.Apocrypha.Apocrypha.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class CourseRecommendation extends Recommendation {
    private int awardPoints;

    @Type(type = "ntext")
    private String contentOfCourse;

    private Boolean ItsConfirmed;

    @ManyToOne
    private User authorOfCourse;

    @Builder
    public CourseRecommendation(String recomandedFormula, String nameOfRecomandation, String description, Date creationDate,
                                String contentOfCourse, Boolean ItsConfirmed, User authorOfCourse, int awardPoints) {
        super(recomandedFormula, nameOfRecomandation, description, creationDate);
        this.authorOfCourse = authorOfCourse;
        this.contentOfCourse = contentOfCourse;
        this.ItsConfirmed = ItsConfirmed;
        this.awardPoints = awardPoints;

    }
}
