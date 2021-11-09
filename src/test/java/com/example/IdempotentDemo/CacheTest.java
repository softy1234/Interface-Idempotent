package com.example.IdempotentDemo;

import com.example.IdempotentDemo.cache.TimedLocalCache;
import org.junit.jupiter.api.Test;

public class CacheTest {
    @Test
    public void testCache(){
        TimedLocalCache<Integer, String> cache = new TimedLocalCache<>(10);
        for (int i = 0; ; i++) {
            cache.putCache(i, new String("WeakHashMap"));//一直往里面加数据

            if (i % 1000 == 0) { //每隔一千次判断一下有没有对象被回收
                for (int j = 0; j < i; j++) {//遍历一遍
                    if (cache.getCache(j) == null) {
                        System.out.println("第" + j + "个对象开始回收");
                        return;
                    }
                }
            }
        }
    }
}
