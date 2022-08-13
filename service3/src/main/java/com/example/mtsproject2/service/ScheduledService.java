package com.example.mtsproject2.service;

import com.example.mtsproject2.client.MetricsClient;
import com.example.mtsproject2.client.SnapshotClient;

import com.example.mtsproject2.model.Snapshot;
import com.example.mtsproject2.model.SnapshotWithCurrency;
import com.example.mtsproject2.repository.OHLCRepository;
import com.example.mtsproject2.repository.SnapshotWithCurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduledService {
    @Value("${currency.pair.from}")
    CopyOnWriteArrayList<String> from;

    @Value("${currency.pair.to}")
    CopyOnWriteArrayList<String> to;

    @Value("${application.starter.savePerRequest}")
    Integer savePerRequest;

    Logger logger = LoggerFactory.getLogger(ScheduledService.class);
    private final SnapshotClient snapshotClient;
    private final MetricsClient metricsClient;
    public ScheduledService(SnapshotClient snapshotClient, MetricsClient metricsClient) {
        this.snapshotClient = snapshotClient;
        this.metricsClient = metricsClient;
    }

    @Scheduled(fixedRate = 60000, initialDelay = 10000)
    public void generateValueOrder() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        for (int it = 0; it < from.size(); ++it) {
            int finalIt = it;
            executor.submit(() -> {

                String pairName = from.get( finalIt ) + to.get( finalIt );
                Double[] values = snapshotClient.generateValueOrder(pairName, savePerRequest, finalIt);
                List<Snapshot> snapshots = new ArrayList<>();
                for (int i = 0; i < values.length; ++i) {
                    LocalDateTime localDateTime = LocalDateTime.now();
                    localDateTime = localDateTime.plusMinutes(i);
                    snapshots.add(new Snapshot( values[ i ], localDateTime) );
                }
                SnapshotWithCurrency snapshotWithCurrency = new SnapshotWithCurrency(snapshots, pairName);
                SnapshotWithCurrencyRepository.save(snapshotWithCurrency);

            });
        }

        executor.shutdown();

        executor.awaitTermination(1, TimeUnit.MINUTES);



    }
    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void saveLastOneMinutes() {
        LocalDateTime start = LocalDateTime.now();
        start = start.minusMinutes(1);
        OHLCRepository.save(metricsClient.getMetrics(start, LocalDateTime.now()));

    }

    @Scheduled(fixedRate = 5 * 60000, initialDelay = 5 * 60000)
    public void saveLastFiveMinutes() {
        LocalDateTime start = LocalDateTime.now();
        start = start.minusMinutes(5);
        OHLCRepository.save(metricsClient.getMetrics(start, LocalDateTime.now()));

    }

    @Scheduled(fixedRate = 15 * 60000, initialDelay = 15 * 60000)
    public void saveLastFifthMinutes() {
        LocalDateTime start = LocalDateTime.now();
        start = start.minusMinutes(15);
        OHLCRepository.save(metricsClient.getMetrics(start, LocalDateTime.now()));

    }

    @Scheduled(fixedRate = 30 * 60000, initialDelay = 30 * 60000)
    public void saveLastThirtyMinutes() {
        LocalDateTime start = LocalDateTime.now();
        start = start.minusMinutes(30);
        OHLCRepository.save(metricsClient.getMetrics(start, LocalDateTime.now()));

    }

    @Scheduled(fixedRate = 60 * 60000, initialDelay = 60 * 60000)
    public void saveLastSixtyMinutes() {
        LocalDateTime start = LocalDateTime.now();
        start = start.minusMinutes(60);
        OHLCRepository.save(metricsClient.getMetrics(start, LocalDateTime.now()));
    }


}
