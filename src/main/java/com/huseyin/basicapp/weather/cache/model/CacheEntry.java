package com.huseyin.basicapp.weather.cache.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class CacheEntry {

    private Instant fetchedAt = Instant.now();

    private String dataSource = "weatherapi";



}
