package com.example.demo.common;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 生成外部唯一id
 */
@Component
public class GetOuterId {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 获取有过期时间的自增长ID
     *
     * @param key
     * @param expireTime
     * @return
     */
    public long generate(String key, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long expire = counter.getExpire();
        if (expire == -1) {
            counter.expireAt(expireTime);
        }
        return counter.incrementAndGet();
    }


    public String generateOrderId() {
        //生成id为当前日期（yyMMddHHmmss）+6位（从000000开始不足位数补0）
        LocalDateTime now = LocalDateTime.now();
        String orderIdPrefix = getOrderIdPrefix(now);//生成yyyyMMddHHmmss
        String orderId = orderIdPrefix + String.format("%1$06d", generate(orderIdPrefix, getExpireAtTime(now)));
        //orderId=str2HexStr(orderId);
        return orderId;

    }

    public static String getOrderIdPrefix(LocalDateTime now) {
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public Date getExpireAtTime(LocalDateTime now) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = now.plusSeconds(20);
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 字符串转换成为16进制
     * @param str
     * @return
     */
    public String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }
}
