package com.aka.demo.common.util;

import com.aka.demo.common.service.DistributedLocker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Redisson锁工具
 */
public class RedissonLockUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedissonLockUtil.class);

    private static DistributedLocker redissonLock;

    public static void setLocker(DistributedLocker locker) {
        redissonLock = locker;
    }

    public static void lock(String lockKey) {
        redissonLock.lock(lockKey);
    }

    public static void lock(String lockKey, int timeout) {
        redissonLock.lock(lockKey, timeout);
    }

    public static void lock(String lockKey, TimeUnit unit, int timeout) {
        redissonLock.lock(lockKey, timeout, unit);
    }

    public static void unlock(String lockKey) {
        logger.info("[RedissonLockUtil] [unlock] lockKey={}", lockKey);
        redissonLock.unlock(lockKey);
    }

    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        logger.info("[RedissonLockUtil] [tryLock] lockKey={}, unit={}, waitTime={}, leaseTime={}", lockKey, unit, waitTime, leaseTime);
        return redissonLock.tryLock(lockKey, unit, waitTime, leaseTime);
    }

}
