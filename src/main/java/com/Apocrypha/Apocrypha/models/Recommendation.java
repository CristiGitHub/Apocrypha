package com.Apocrypha.Apocrypha.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "recommendation")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="recommendation_type" , discriminatorType = DiscriminatorType.INTEGER)
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recomandedFormula;

    private String nameOfRecomandation;

    private String description;

    private Date creationDate;


    public Recommendation(String recomandedFormula, String nameOfRecomandation, String description, Date creationDate) {
        this.creationDate=creationDate;
        this.description=description;
        this.nameOfRecomandation=nameOfRecomandation;
        this.recomandedFormula=recomandedFormula;
    }
}
