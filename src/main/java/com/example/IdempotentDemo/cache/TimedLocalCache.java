package com.example.IdempotentDemo.cache;

import org.springframework.util.ConcurrentReferenceHashMap;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
/**
 * 具有过期回收功能的内部缓存-->可以指定(弱)引用类型的concurrentHashMap
 */
public class TimedLocalCache<K, V> extends ConcurrentReferenceHashMap<K, TimedLocalCachedDataDTO<V>> {
    private final long timeout;

    public TimedLocalCache(long timeout) {
        super(16,ConcurrentReferenceHashMap.ReferenceType.WEAK);
        this.timeout = timeout;
    }

    /**
     * 默认5分钟过期
     */
    public TimedLocalCache() {
        super(16,ConcurrentReferenceHashMap.ReferenceType.WEAK);
        timeout = TimeUnit.MINUTES.toSeconds(5);
    }

    public long getTimeout() {
        return timeout;
    }

    public TimedLocalCachedDataDTO<V> putCache(K key, V value) {
        TimedLocalCachedDataDTO<V> dataDTO = new TimedLocalCachedDataDTO<>(value);
        return super.put(key, dataDTO);
    }

    public V getCache(K key) {
        TimedLocalCachedDataDTO<V> dataDTO = super.get(key);
        if (dataDTO == null) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time = dataDTO.getTime();
        long seconds = Duration.between(time, now).getSeconds();
        if (seconds > getTimeout()) {
            remove(key);
            return null;
        }
        return dataDTO.getData();
    }
}