package com.example.mtsproject;



import com.example.mtsproject.repository.CurrencyPairRepository;
import com.example.mtsproject.service.db.CurrencyPairServiceDB;

import com.example.mtsproject.service.saver.CurrencyPairServiceSaver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.test.context.SpringBootTest;


import java.util.concurrent.CopyOnWriteArrayList;





@SpringBootTest
public class CurrencyPairControllerTest {

    @InjectMocks
    CurrencyPairServiceSaver currencyPairServiceSaver;

    @InjectMocks
    CurrencyPairServiceDB currencyPairServiceDB;

    @Mock
    private CurrencyPairRepository currencyPairRepository;
    @Value("${currency.pair.from}")
    private CopyOnWriteArrayList<String> from;

    @Value("${currency.pair.to}")
    private CopyOnWriteArrayList<String> to;

    @Test
    public void generateShouldNotThrowException() {
        Assertions.assertTrue(true);
          Assertions.assertDoesNotThrow(() -> currencyPairServiceSaver.saveRandomPairsTest(from, to));
    }

    @Test
    public void findByValueShouldNotThrowException() {
         Assertions.assertDoesNotThrow(() -> currencyPairServiceDB.findByValue("USDRUB"));
    }

    @Test
    public void findByValueShouldRetunNullIfPairNotFound() {
         Assertions.assertNull(currencyPairServiceDB.findByValue("USDRUS"));
    }
}
