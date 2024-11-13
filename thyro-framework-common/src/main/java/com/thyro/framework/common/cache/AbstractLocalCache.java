package com.thyro.framework.common.cache;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 本地缓存抽象类
 */
@EnableScheduling
@Slf4j
public abstract class AbstractLocalCache<K, V> implements LocalCache, SchedulingConfigurer {

    private Long lastUpdateTime;

    private final long incrementalUpdateInterval;
    private final String refreshAllCron;
    protected Map<K, V> cache;

    protected AbstractLocalCache(int cacheSize, String refreshAllCron, long incrementalUpdateInterval) {
        this.incrementalUpdateInterval = incrementalUpdateInterval;
        this.refreshAllCron = refreshAllCron;
        this.cache = new ConcurrentHashMap<>(cacheSize);
    }

    /**
     * 初始化缓存
     */
    @Override
    public void initCache() {
        log.info("开始初始化{}缓存...", this.getClass().getSimpleName());
        lastUpdateTime = System.currentTimeMillis();
        long start = lastUpdateTime;
        boolean result = allCacheData();
        if (!result) {
            lastUpdateTime = 0L;
        }
        log.info("{}缓存加载完毕，共{}条数据，占用大小：{}mb，加载耗时{}秒",
                this.getClass().getSimpleName(),
                cache.size(),
                ObjectSizeCalculator.getObjectSize(cache) / 100_0000,
                (System.currentTimeMillis() - start) / 1000D);
    }


    /**
     * 增量数据
     *
     * @param lastUpdateTime
     */
    public abstract void incrementCacheData(Long lastUpdateTime);

    /**
     * 全量数据
     */
    public abstract boolean allCacheData();

    /**
     * 制定cache key
     *
     * @return
     */
    public abstract Function<V, K> cacheKey();

    /**
     * 如果cache没有命中的处理方法
     *
     * @param cacheId
     * @return
     */
    public abstract V ifNull(K cacheId);

    public V getById(K cacheId) {
        return getCache(cacheId);
    }

    @Nullable
    public V getCache(K cacheId) {
        if (cacheId == null) {
            return null;
        }
        return Optional.ofNullable(cache.get(cacheId)).orElse(ifNull(cacheId));
    }

    public void ifPresent(K cacheId, Consumer<? super V> consumer) {
        Optional.ofNullable(getCache(cacheId)).ifPresent(consumer);
    }

    /**
     * 获取指定key的缓存，并通过指定转换函数获取缓存对象中的某个属性
     *
     * @param cacheId 缓存key
     * @param mapper  缓存value的转换函数
     * @return 属性值
     */
    public <T> T getCacheProperty(K cacheId, Function<V, T> mapper) {
        return Optional.ofNullable(getCache(cacheId)).map(mapper).orElse(null);
    }

    public void putCache(K k, V v) {
        cache.put(k, v);
    }

    public void pushCache(List<V> cacheData) {
        lastUpdateTime = System.currentTimeMillis();
        cacheData.forEach(v -> {
            Optional<K> k = Optional.ofNullable(v).map(cacheKey());
            k.ifPresent(k1 -> putCache(k1, v));
        });
    }

    /**
     * 通过指定条件查询id
     *
     * @param predicate 筛选表达式
     * @return id集合
     */
    public List<K> findIdsBy(Predicate<? super V> predicate) {
        return cache.values().stream().
                filter(predicate)
                .map(cacheKey())
                .collect(Collectors.toList());
    }

    /**
     * 通过指定条件查询缓存，并返回map
     *
     * @param predicate 筛选表达式
     * @return map，key是id，value是对象
     */
    public Map<K, V> findMapBy(Predicate<? super V> predicate) {
        return cache.values().stream().
                filter(predicate).collect(Collectors.toMap(cacheKey(), Function.identity()));
    }

    /**
     * 通过指定条件查询缓存，并返回List
     *
     * @param predicate 筛选表达式
     * @return list
     */
    public List<V> findBy(Predicate<? super V> predicate) {
        return cache.values().stream().
                filter(predicate).collect(Collectors.toList());
    }


    @Override
    public void configureTasks(@Nullable ScheduledTaskRegistrar scheduledTaskRegistrar) {
        if (scheduledTaskRegistrar != null) {
            // 定时任务
            scheduledTaskRegistrar.addTriggerTask(this::allCacheData, new CronTrigger(refreshAllCron));
            // 间隔任务
            IntervalTask intervalTask = new IntervalTask(this::incrementCacheData, incrementalUpdateInterval, incrementalUpdateInterval);
            scheduledTaskRegistrar.addFixedDelayTask(intervalTask);
            log.info("创建本地缓存{}执行任务...ok", this.getClass().getSimpleName());
        }
    }


    private void incrementCacheData() {
        log.info("开始增量刷新缓存{}...间隔时间{}秒", getClass().getSimpleName(), incrementalUpdateInterval);
        int oldSize = cache.size();
        long now = System.currentTimeMillis();
        incrementCacheData(lastUpdateTime);
        lastUpdateTime = now;
        int newSize = cache.size();
        log.info("{}缓存增量刷新完毕，新增{}条，耗时{}毫秒，当前缓存总大小：{}mb",
                getClass().getSimpleName(),
                newSize - oldSize,
                System.currentTimeMillis() - now,
                ObjectSizeCalculator.getObjectSize(cache) / 100_0000);
    }

}
