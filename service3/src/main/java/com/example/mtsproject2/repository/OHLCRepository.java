package com.example.mtsproject2.repository;

import com.example.mtsproject2.model.OHLC;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OHLCRepository {
    public static CopyOnWriteArrayList<OHLC> ohlcs = new CopyOnWriteArrayList<>();
    public static void save(List<OHLC> ohlc) {
        ohlcs.addAll(ohlc);
    }
}
