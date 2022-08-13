package com.example.mtsproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "currency_pair")
@Data
public class CurrencyPair {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "from_db")
    String from;

    @Column(name = "to_db")
    String to;

    public CurrencyPair(String from, String to, List<Snapshot> snapshots) {
        this.from = from;
        this.to = to;
        this.snapshots = snapshots;
    }

    @OneToMany(mappedBy = "currencyPair", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Snapshot> snapshots;

    public CurrencyPair() {

    }
}
