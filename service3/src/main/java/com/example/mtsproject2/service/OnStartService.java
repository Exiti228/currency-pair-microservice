package com.example.mtsproject2.service;

import com.example.mtsproject2.client.SnapshotClient;
import com.example.mtsproject2.model.Snapshot;
import com.example.mtsproject2.model.SnapshotWithCurrency;
import com.example.mtsproject2.repository.SnapshotWithCurrencyRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class OnStartService {
    private final SnapshotClient snapshotClient;

    @Value("${currency.pair.from}")
    CopyOnWriteArrayList<String> from;

    @Value("${currency.pair.to}")
    CopyOnWriteArrayList<String> to;

    @Value("${application.starter.savePerRequest}")
    Integer savePerRequest;



    public OnStartService(SnapshotClient snapshotClient) {
        this.snapshotClient = snapshotClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int it = 0; it < from.size(); ++it) {

            int finalIt = it;
            executor.submit(() -> {


                LocalDate today = LocalDate.now();
                LocalDateTime start = today.atStartOfDay();
                LocalDateTime end = LocalDateTime.now();
                String pairName = from.get(finalIt) + to.get(finalIt);
                List<Snapshot> snapshots = snapshotClient.findByValueWithRange(pairName, start, end);
                SnapshotWithCurrency snapshotWithCurrency = new SnapshotWithCurrency(snapshots, pairName);
                SnapshotWithCurrencyRepository.save(snapshotWithCurrency);
            });

        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);


    }

}
