package com.example.mtsproject2.model;

import lombok.Data;

import java.util.List;

@Data
public class SnapshotWithCurrency {
    private List<Snapshot> snapshots;
    private String currencyPair;

    public SnapshotWithCurrency(List<Snapshot> snapshots, String pairName) {
        this.snapshots = snapshots;
        this.currencyPair = pairName;
    }
}
