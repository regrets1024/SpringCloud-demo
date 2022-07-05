package com.aka.demo.common.controller;

import cn.hutool.core.util.StrUtil;
import com.aka.demo.common.exception.BaseException;
import com.aka.demo.common.model.RequestVO;
import com.aka.demo.common.service.Add;
import com.aka.demo.common.service.Update;
import com.aka.demo.common.util.RedisUtil;
import com.aka.demo.common.util.RedissonLockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.concurrent.TimeUnit;

/**
 * 公共接口控制器
 */
@Validated
@RestController
@RequestMapping("/common")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    private final RedisUtil redisUtils;

    public CommonController(RedisUtil redisUtils) {
        this.redisUtils = redisUtils;
    }

    @GetMapping("/set")
    public boolean testSet( @NotBlank(message = "key不能为空") String key,
                           @NotBlank(message = "value不能为空") String value) {
        return redisUtils.set(key, value);
    }

    @GetMapping("/get")
    public Object testGet(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BaseException("key不能为空");
        }
        return redisUtils.get(key);
    }

    @GetMapping("/expire")
    public boolean testExpire(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BaseException("key不能为空");
        }
        return redisUtils.expire(key, 120, TimeUnit.SECONDS);
    }

    @GetMapping("/getExpire")
    public Long testGetExpire(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BaseException("key不能为空");
        }
        return redisUtils.getExpire(key, TimeUnit.SECONDS);
    }

    @GetMapping("/increment")
    public Long testIncrement(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BaseException("key不能为空");
        }
        return redisUtils.increment(key);
    }

    @GetMapping("/lock")
    public Integer testLock(String key) {
        if (StrUtil.isBlank(key)) {
            throw new BaseException("key不能为空");
        }

        String lockKey = key + "_lock";
        boolean tryLock = RedissonLockUtil.tryLock(lockKey, TimeUnit.SECONDS, 3, 10);
        if (tryLock) {
            redisUtils.increment(key);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 释放锁
            RedissonLockUtil.unlock(lockKey);
        } else {
            logger.info("[CommonController] [lock] 未获取到锁");
        }

        return (Integer) redisUtils.get(key);
    }

    @GetMapping(value = "/add")
    public String testAdd(@Validated(value = {Add.class}) @RequestBody RequestVO requestVO) {
        logger.info("[CommonController] [testAdd] requestVO={}", requestVO);
        return requestVO.getName();
    }

    @GetMapping(value = "/update")
    public String testUpdate(@Validated(value = {Update.class}) @RequestBody RequestVO requestVO) {
        logger.info("[CommonController] [testUpdate] requestVO={}", requestVO);
        return requestVO.getName();
    }

}
