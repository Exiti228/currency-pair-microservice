package com.example.mtsproject.service.db;

import com.example.mtsproject.model.CurrencyPair;
import com.example.mtsproject.model.Snapshot;
import com.example.mtsproject.repository.CurrencyPairRepository;

import com.example.mtsproject.utill.PairNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrencyPairServiceDB {
    private final CurrencyPairRepository currencyPairRepository;


    @Transactional
    public List<Snapshot> findByValue(String value) {
        if (value.length() != 6) {
            throw new PairNotFoundException("Incorrect currency pair, length should be 6, for example USDRUB");
        }
        value = value.toUpperCase();
        Optional<CurrencyPair> pair = currencyPairRepository.findByFromAndTo( value.substring(0, 3), value.substring(3, 6) );
        return pair.map(CurrencyPair::getSnapshots).orElse(null);
    }

    @Transactional
    public List<Snapshot> findByValueAndRange(String value, LocalDateTime from, LocalDateTime to) {
        List<Snapshot> ret = new ArrayList<>();
        List<Snapshot> snapshots = findByValue(value);
        if (snapshots == null) return  null;
        for (Snapshot snapshot : snapshots) {
            if (snapshot.getDate().isAfter(from) && snapshot.getDate().isBefore(to)) {
                ret.add(snapshot);
            }
        }
        return ret;

    }

}
