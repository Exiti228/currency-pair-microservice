package com.example.mtsproject.service.saver;

import com.example.mtsproject.model.CurrencyPair;
import com.example.mtsproject.model.Snapshot;
import com.example.mtsproject.repository.CurrencyPairRepository;
import com.example.mtsproject.repository.SnapshotRepository;
import com.example.mtsproject.service.generator.CurrencyPairServiceGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;


import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class CurrencyPairServiceSaver {
    private final CurrencyPairServiceGenerator currencyPairServiceGenerator;
    private final CurrencyPairRepository currencyPairRepository;
    private final SnapshotRepository snapshotRepository;
    public CurrencyPairServiceSaver(CurrencyPairServiceGenerator currencyPairServiceGenerator, CurrencyPairRepository currencyPairRepository, SnapshotRepository snapshotRepository) {
        this.currencyPairServiceGenerator = currencyPairServiceGenerator;
        this.currencyPairRepository = currencyPairRepository;
        this.snapshotRepository = snapshotRepository;
    }

    @Value("${currency.pair.from}")
    private  CopyOnWriteArrayList<String> from;

    @Value("${currency.pair.to}")
    private CopyOnWriteArrayList<String> to;

    @Value("${currency.pair.save.len}")
    private volatile Integer currencyPairSaveLen;

    Logger logger = LoggerFactory.getLogger(CurrencyPairServiceSaver.class);

    public void saveRandomPairs() {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for ( int it = 0; it < to.size(); ++it ) {
            final int ind = it;

            executor.submit(() -> {

            Double[] prices = currencyPairServiceGenerator.generateAllValueByPair(from.get( ind ), to.get( ind ), ind, currencyPairSaveLen);
            savePairWithDelete(from.get( ind ), to.get( ind ), prices);

            });
        }

        executor.shutdown();
    }
    public void saveRandomPairsTest(CopyOnWriteArrayList<String> from1, CopyOnWriteArrayList<String> to1) {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for ( int it = 0; it < to1.size(); ++it ) {
            final int ind = it;

            executor.submit(() -> {

                Double[] prices = currencyPairServiceGenerator.generateAllValueByPair(from1.get( ind ), to1.get( ind ), ind, currencyPairSaveLen);
                savePairWithDelete(from1.get( ind ), to1.get( ind ), prices);

            });
        }

        executor.shutdown();
    }
    public void saveRandomPairs(int len, String value, int ind) {
        Double[] prices = currencyPairServiceGenerator.generateAllValueByPair(from.get( ind ), to.get( ind ), ind, len);
        savePairWithoutDelete(from.get( ind ), to.get( ind ), prices);

    }



    @Transactional
    public void addSnapshotsWithPrice(Double[] prices, Optional<CurrencyPair> pairOpt) {
        int add = 0;
        for (Double price : prices) {
            LocalDateTime localDateTime = LocalDateTime.now();
            localDateTime = localDateTime.plusMinutes(add++);
            Snapshot snapshot = new Snapshot(price, localDateTime, pairOpt.get());
            pairOpt.get().getSnapshots().add(snapshot);
        }
    }
    @Transactional
    public void savePair(String from, String to, Double[] prices, boolean needDelete) {
        Optional<CurrencyPair> pairOpt = currencyPairRepository.findByFromAndTo(from, to);
        logger.info("create " + from + to + " pair");
        if (pairOpt.isEmpty()) {

            CurrencyPair currencyPair = new CurrencyPair(from, to, new ArrayList<>());
            for (int it = 0; it < Math.min(currencyPairSaveLen, prices.length); ++it) {
                LocalDateTime localDateTime = LocalDateTime.now();
                localDateTime = localDateTime.plusMinutes(it);
                Snapshot snapshot = new Snapshot(prices[ it ], localDateTime, currencyPair);
                currencyPair.getSnapshots().add(snapshot);
            }
            currencyPairRepository.save(currencyPair);
        }
        else {
            int snapShotLen = pairOpt.get().getSnapshots().size();
            if (needDelete) {
                int skip = Math.max(0, prices.length + snapShotLen - currencyPairSaveLen);
                addSnapshotsWithPrice(prices, pairOpt);
                snapshotRepository.saveAll(pairOpt.get().getSnapshots().subList(skip, snapShotLen + prices.length));
            }
            else {
                addSnapshotsWithPrice(prices, pairOpt);
                snapshotRepository.saveAll(pairOpt.get().getSnapshots().subList(snapShotLen, snapShotLen + prices.length));
            }

        }
    }

    @Transactional
    public void savePairWithDelete(String from, String to, Double[] prices) {
        savePair(from, to, prices, true);
    }
    @Transactional
    public void savePairWithoutDelete(String from, String to, Double[] prices) {
        savePair(from, to, prices, false);
    }




}
