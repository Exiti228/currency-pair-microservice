package com.example.mtsproject.repository;

import com.example.mtsproject.model.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Integer> {
    Optional<CurrencyPair> findByFromAndTo(String from, String to);
    
}
