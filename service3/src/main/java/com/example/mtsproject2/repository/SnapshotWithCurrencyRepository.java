package com.example.mtsproject2.repository;

import com.example.mtsproject2.model.SnapshotWithCurrency;
import lombok.Data;


import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class SnapshotWithCurrencyRepository {
    public static CopyOnWriteArrayList<SnapshotWithCurrency> snapshots = new CopyOnWriteArrayList<>();

    public static void save(SnapshotWithCurrency snapshotWithCurrency) {
        snapshots.add(snapshotWithCurrency);
    }
}
