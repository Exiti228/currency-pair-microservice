package com.example.mtsproject.service.generator;


import com.example.mtsproject.model.CurrencyPair;
import com.example.mtsproject.repository.CurrencyPairRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Service

public class CurrencyPairServiceGenerator {

    private final CurrencyPairRepository currencyPairRepository;

    @Value("${currency.pair.price}")
    private CopyOnWriteArrayList<Double> pricesPair;

    public CurrencyPairServiceGenerator(CurrencyPairRepository currencyPairRepository) {
        this.currencyPairRepository = currencyPairRepository;
    }

    private Double generateOneValueByPair(double prev) {
        Random random = new Random();
        return ( ( random.nextDouble() * 6 - 3 ) / 100 + 1 ) * prev;
    }

    @Transactional
    public Double[] generateAllValueByPair(String from, String to, int ind, int currencyPairSaveLen) {
        Optional<CurrencyPair> currencyPair = currencyPairRepository.findByFromAndTo(from, to);
        Double[] prices = new Double[ currencyPairSaveLen ];
        for (int it = 0; it < currencyPairSaveLen; ++it) {

            Double save = ( it == 0 ? pricesPair.get(ind) : prices[ it - 1 ] );
            if (currencyPair.isPresent())
                save = currencyPair.get().getSnapshots().get( currencyPair.get().getSnapshots().size() - 1 ).getPrice();

            prices[ it ] = generateOneValueByPair( save );


        }
        return prices;
    }

}
