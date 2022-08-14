package com.example.mtsproject;
import com.example.mtsproject.repository.CurrencyPairRepository;
import com.example.mtsproject.service.generator.CurrencyPairServiceGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest

public class CurrencyPairServiceGeneratorTest {

    @InjectMocks
    private  CurrencyPairServiceGenerator currencyPairServiceGenerator;

    @Mock
    private CurrencyPairRepository currencyPairRepository;

    @Value("${currency.pair.price}")
    private List<Double> pricesPair;






    @Test
    public void deltaShouldBeNoMoreThanThreePercent() {
        when(currencyPairRepository.findByFromAndTo(any(String.class), any(String.class))).thenReturn(Optional.empty());
        Double[] price = currencyPairServiceGenerator.generateAllValueByPairTest("USD", "RUB", 0, 100, pricesPair);

        double prev = pricesPair.get(0);
        for (Double value : price) {
            System.out.println(value + " " + prev);
            Assertions.assertFalse((value / prev)> 1.035, "Value is to high");
            Assertions.assertFalse((prev / value) > 1.035, "Value is to lowe");
            prev = value;
        }

    }
}
