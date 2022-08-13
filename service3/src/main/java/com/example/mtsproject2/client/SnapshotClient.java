package com.example.mtsproject2.client;

import com.example.mtsproject2.model.Snapshot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "snapshot-client", url= "${application.starter.url}")

public interface SnapshotClient {
    @GetMapping(value = "/currency/range", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Snapshot> findByValueWithRange(@RequestParam(name = "value") String value,
                                        @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                        @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to);
    @GetMapping("/currency/generate")
    Double[] generateValueOrder(@RequestParam(name = "value") String value,
                                   @RequestParam(name = "count", required = false) Integer count,
                                   @RequestParam(name = "ind", required = false) Integer ind);
}
