package com.di.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by bentengdi on 2018/2/28.
 */
public class RedisUtil {

    private static final Logger logger = Logger.getLogger(RedisUtil.class);

    private static ShardedJedisPool shardedJedisPool;

    static {
        Properties properties = new Properties();
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("redis.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            logger.error("load redis.properties file error");
        }

        String property = properties.getProperty("redis.address", "");

        String redisAddresses = StringUtils.deleteWhitespace(property);

        List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();

        if (StringUtils.isNotEmpty(redisAddresses)) {
            String[] split = redisAddresses.split(",");

            for (String address : split) {
                if (StringUtils.isNotEmpty(address)) {
                    String[] hostPort = address.split(":");
                    if (hostPort == null || hostPort.length != 2 || StringUtils.isEmpty(hostPort[0]) || StringUtils.isEmpty(hostPort[1])) {
                        logger.info("redis address parse error");
                        continue;
                    }
                    jedisShardInfos.add(new JedisShardInfo(hostPort[0], hostPort[1]));
                }
            }
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(20);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPoolConfig.setTestOnBorrow(true);

        shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, jedisShardInfos);
    }

    /**
     * common operation
     */

    public static boolean exists(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.exists(key);
        } finally {
            if (null != shardedJedis) {
                shardedJedis.close();
            }
        }
    }





}
