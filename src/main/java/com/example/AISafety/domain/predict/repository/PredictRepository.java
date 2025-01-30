package com.example.AISafety.domain.predict.repository;

import com.example.AISafety.domain.predict.Predict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictRepository extends JpaRepository<Predict, Long> {

}
