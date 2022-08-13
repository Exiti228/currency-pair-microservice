package com.example.mtsproject.service.Scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class SchedulerService {

    @Value("${application.starter.url}")
    String url;

    @Value("${application.starter.savePerRequest}")
    Integer savePerRequest;

    @Value("${currency.pair.from}")
    private CopyOnWriteArrayList<String> from;

    @Value("${currency.pair.to}")
    private CopyOnWriteArrayList<String> to;

    private final RestTemplate restTemplate;

    public SchedulerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 50000)
    public void scheduleGenerateCurrencyPair() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int it = 0; it < to.size(); ++it ) {
            int finalIt = it;
            executor.submit(() -> {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
                map.add("value", from.get(finalIt) + to.get(finalIt));
                map.add("count", savePerRequest.toString());
                map.add("ind", Integer.toString(finalIt));
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
                System.out.println(url + "/currency/generate");
                System.out.println((finalIt));
                restTemplate.postForLocation(url + "/currency/generate", request);

            });
        }

        executor.shutdown();
    }
}
