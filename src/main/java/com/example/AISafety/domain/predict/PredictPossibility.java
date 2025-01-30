package com.example.AISafety.domain.predict;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PredictPossibility {
    private String risk1;
    private String risk2;
    private String risk3;
    private String risk4;
}
