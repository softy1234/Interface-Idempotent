package com.example.IdempotentDemo.cache;

import java.time.LocalDateTime;

public class TimedLocalCachedDataDTO<V> {
    private final LocalDateTime time;
    private final V data;

    public TimedLocalCachedDataDTO(V data) {
        this.time = LocalDateTime.now();
        this.data = data;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public V getData() {
        return data;
    }
}
