package com.example.mtsproject;

import com.example.mtsproject.service.db.CurrencyPairServiceDB;

import com.example.mtsproject.utill.PairNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class CurrencyPairServiceDBTest {

        @InjectMocks
        CurrencyPairServiceDB currencyPairServiceDB;
        @Test
        public void valueSizeShouldBeSix() {
                Exception exception = assertThrows(PairNotFoundException.class, () -> currencyPairServiceDB.findByValue("EURSD"));
                assertEquals("Incorrect currency pair, length should be 6, for example USDRUB", exception.getMessage());

        }
}
