package com.xu.tulingchat.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  /**
   * 写入缓存
   * @param key
   * @param value
   * @return
   */
  public boolean set(final String key, String value) {
    boolean result = false;
    try {
      stringRedisTemplate.opsForValue().set(key, value);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 写入缓存设置时效时间
   * @param key
   * @param value
   * @param expireTime
   * @return
   */
  public boolean set(final String key, String value, Long expireTime) {
    boolean result = false;
    try {
      stringRedisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获取key
   * @param pattern
   * @return
   */
  public Set<String> getKeys(String pattern) {
	  return stringRedisTemplate.keys(pattern);
  }

  /**
   * 批量删除key
   * @param pattern 正则
   */
  public void removePattern(final String pattern) {
    Set<String> keys = stringRedisTemplate.keys(pattern);
    if (keys.size() > 0) {
      stringRedisTemplate.delete(keys);
    }
  }

  /**
   * 删除对应的value
   * @param key
   */
  public void remove(final String key) {
    if (exists(key)) {
      stringRedisTemplate.delete(key);
    }
  }

	/**
	 * 批量删除对应的value
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return stringRedisTemplate.hasKey(key);
	}

  /**
   * 读取缓存
   * @param key
   * @return
   */
  public String get(final String key) {
    return stringRedisTemplate.opsForValue().get(key);
  }

  /**
   * 哈希 添加
   * @param key
   * @param hashKey
   * @param value
   */
  public void hmSet(String key, Object hashKey, Object value) {
    HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
    hash.put(key, hashKey, value);
  }

  /**
   * 哈希获取数据
   * @param key
   * @param hashKey
   * @return
   */
  public Object hmGet(String key, Object hashKey) {
    HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
    return hash.get(key, hashKey);
  }

  /**
   * 列表添加
   * @param k
   * @param v
   */
  public void lPush(String k, String v) {
    ListOperations<String, String> list = stringRedisTemplate.opsForList();
    list.rightPush(k, v);
  }

  /**
   * 列表获取
   * @param k
   * @param l
   * @param l1
   * @return
   */
  public List<String> lRange(String k, long l, long l1) {
    ListOperations<String, String> list = stringRedisTemplate.opsForList();
    return list.range(k, l, l1);
  }

  /**
   * 集合添加
   * @param key
   * @param value
   */
  public void add(String key, String value) {
    stringRedisTemplate.opsForSet().add(key, value);
  }

  /**
   * 集合获取
   * @param key
   * @return
   */
  public Set<String> setMembers(String key) {
    return stringRedisTemplate.opsForSet().members(key);
  }

  /**
   * 有序集合添加
   * @param key
   * @param value
   * @param scoure
   */
  public void zAdd(String key, String value, double scoure) {
    stringRedisTemplate.opsForZSet().add(key, value, scoure);
  }

  /**
   * 有序集合获取
   * @param key
   * @param scoure
   * @param scoure1
   * @return
   */
  public Set<String> rangeByScore(String key, double scoure, double scoure1) {
    return stringRedisTemplate.opsForZSet().rangeByScore(key, scoure, scoure1);
  }
}