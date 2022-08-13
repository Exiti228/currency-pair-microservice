package com.example.mtsproject2.service;

import com.example.mtsproject2.model.OHLC;
import com.example.mtsproject2.model.Snapshot;
import com.example.mtsproject2.model.SnapshotWithCurrency;
import com.example.mtsproject2.repository.OHLCRepository;
import com.example.mtsproject2.repository.SnapshotWithCurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MetricsService {
    Logger logger = LoggerFactory.getLogger(MetricsService.class);
    private boolean isEquals(LocalDateTime d1, LocalDateTime d2) {
        return d1.getMinute() == d2.getMinute()  && d1.getHour() == d2.getHour() && d1.getMonth().equals(d2.getMonth()) && d1.getYear() == d2.getYear();
    }
    public List<OHLC> getMetrics(LocalDateTime from, LocalDateTime to) {

        HashMap<String, OHLC> arr = new HashMap<>();
        for (SnapshotWithCurrency snapshotWithCurrency : SnapshotWithCurrencyRepository.snapshots) {
            Double open = Double.MIN_VALUE;
            double high = Double.MIN_VALUE;
            double low = Double.MAX_VALUE;
            Double close = Double.MIN_VALUE;
            String pairName = snapshotWithCurrency.getCurrencyPair();

            for (Snapshot snapshot : snapshotWithCurrency.getSnapshots()) {

                if (isEquals(from, snapshot.getDate()))
                    open = snapshot.getPrice();
                if (isEquals(to, snapshot.getDate()))
                    close = snapshot.getPrice();
                if ((snapshot.getDate().isAfter(from) && snapshot.getDate().isBefore(to)) ||
                        isEquals(from, snapshot.getDate()) || isEquals(to, snapshot.getDate())) {
                    high = Math.max(high, snapshot.getPrice());
                    low = Math.min(low, snapshot.getPrice());
                }
            }

            if (!arr.containsKey(pairName))
                arr.put(pairName, new OHLC(open, high, low, close, from, to,  pairName));
            else {
                OHLC oh1 = arr.get(pairName);
                OHLC oh2 = new OHLC(Math.max(open, oh1.getOpen()), Math.max(high, oh1.getHigh()), Math.min(low, oh1.getLow()),
                        Math.max(close, oh1.getClose()), from, to, pairName);
                arr.put(pairName, oh2);
            }
        }
        List<OHLC> r = new ArrayList<>();
        for (String key : arr.keySet()) {
            r.add(arr.get(key));
        }
        logger.info(r.toString());
        return r;
    }

    public List<OHLC> getOHLC(String value) {
        List<OHLC> ret = new ArrayList<>();
        for (OHLC ohlc : OHLCRepository.ohlcs) {
            if (ohlc.getPairName().equals(value))
            ret.add(ohlc);
        }
        return ret;
    }
}
