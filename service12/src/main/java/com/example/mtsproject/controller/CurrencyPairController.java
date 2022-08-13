package com.example.mtsproject.controller;

import com.example.mtsproject.model.Snapshot;
import com.example.mtsproject.service.db.CurrencyPairServiceDB;
import com.example.mtsproject.service.generator.CurrencyPairServiceGenerator;
import com.example.mtsproject.service.saver.CurrencyPairServiceSaver;
import com.example.mtsproject.utill.PairNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController

@RequestMapping("/currency")
public class CurrencyPairController {

    Logger logger = LoggerFactory.getLogger(CurrencyPairController.class);
    private final CurrencyPairServiceDB currencyPairServiceDB;
    private final CurrencyPairServiceSaver currencyPairServiceSaver;
    private final CurrencyPairServiceGenerator currencyPairServiceGenerator;
    public CurrencyPairController(CurrencyPairServiceDB currencyPairServiceDB, CurrencyPairServiceSaver currencyPairServiceSaver, CurrencyPairServiceGenerator currencyPairServiceGenerator) {
        this.currencyPairServiceDB = currencyPairServiceDB;
        this.currencyPairServiceSaver = currencyPairServiceSaver;
        this.currencyPairServiceGenerator = currencyPairServiceGenerator;
    }

    @GetMapping("")
    public List<Snapshot> findByValue(@RequestParam(name = "value") String value) {
        return currencyPairServiceDB.findByValue(value);
    }
    @GetMapping("/range")
    public List<Snapshot> findByValueWithRange(@RequestParam(name = "value") String value,
                                               @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                               @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        return currencyPairServiceDB.findByValueAndRange(value, from, to);

    }
    @PostMapping("/generate")
    public void generateValueOrderWithSave(@RequestParam(name = "value") String value,
                                   @RequestParam(name = "count", required = false) Integer count,
                                   @RequestParam(name = "ind", required = false) Integer ind) {
        System.out.println(value);
        currencyPairServiceSaver.saveRandomPairs(count, value, ind);

    }
    @GetMapping("/generate")
    public Double[] generateValueOrderWithoutSave(@RequestParam(name = "value") String value,
                                   @RequestParam(name = "count", required = false) Integer count,
                                   @RequestParam(name = "ind", required = false) Integer ind) {

        return currencyPairServiceGenerator.generateAllValueByPair(value.substring(0, 3), value.substring(3, 6), ind, count);

    }
    @PostMapping("/add")
    public void generate() {
        currencyPairServiceSaver.saveRandomPairs();
    }

    @ExceptionHandler(PairNotFoundException.class)
    public String pairNotFound() {
        logger.error("Incorrect currency pair, length should be 6, for example USDRUB");
        return "Incorrect currency pair, length should be 6, for example USDRUB";
    }

}
