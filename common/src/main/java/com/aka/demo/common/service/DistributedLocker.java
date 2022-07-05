package com.aka.demo.common.service;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 */
@SuppressWarnings("ALL")
public interface DistributedLocker {

    /**
     * 分布式锁
     * <p>此方法不带超时 不手动释放可能引起死锁</p>
     *
     * @param lockKey redis的key
     */
    RLock lock(String lockKey);

    /**
     * 分布式锁
     * <p>此方法带超时 单位为毫秒</p>
     *
     * @param lockKey   redis的key
     * @param leaseTime 超时时间，单位为秒
     */
    RLock lock(String lockKey, int leaseTime);


    /**
     * 分布式锁
     * <p>此方法带超时 自定义时间单位</p>
     *
     * @param lockKey   redis的key
     * @param unit      时间单位
     * @param leaseTime 超时释放时间（建议不要太长）
     */
    RLock lock(String lockKey, int leaseTime, TimeUnit unit);

    /**
     * 尝试获取锁
     *
     * @param waitTime  尝试获取锁的时间
     * @param leaseTime 自动释放时间 -1为自动续期
     */
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    /**
     * 通过redisKey 释放锁
     */
    void unlock(String lockKey);

    /**
     * 通过所对象释放锁
     */
    void unlock(RLock lock);

}
