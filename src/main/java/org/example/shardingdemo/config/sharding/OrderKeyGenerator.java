package org.example.shardingdemo.config.sharding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lzp
 */
public final class OrderKeyGenerator {

    private static final long EPOCH;
    private static final Logger log = LoggerFactory.getLogger(OrderKeyGenerator.class);

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.FEBRUARY, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }

    /**
     *
     * the id is generated in 3 parts: [1 bit sign][41 bits timestamp][4 bits userId][18 bits randomSequence], all 64 bits as 'long' type
     *  41 bits timestamp: milliseconds from 2024-01-01/00:00:00 to now
     *  4 bits userId: last 4 bits of userId
     *  18 bits randomSequence: random number
     *
     * @param userId use it to generate primary key id
     * @return primary key id
     */
    public static long generateKey(long userId) {
        long currentMilliseconds = System.currentTimeMillis();
        return generateKeyWithParam(currentMilliseconds - EPOCH, userId, random18BitsSequence());
    }

    static long generateKeyWithParam(long millis, long userId, long random) {
        long id = (millis << 22)
                | (get4BitsFromUserId(userId) << 18)
                | random;
        debugKey(userId, id);
        return id;
    }

    public static void debugKey(long userId, long orderId) {
        log.info("generate orderId:{} using userId:{}", orderId, userId);
        // extract 4 bits of userId from orderId
        long bitsInOrderId = (orderId >> 18) & USER_MASK;
        long bitsInUserId = get4BitsFromUserId(userId);
        if(bitsInOrderId != bitsInUserId) {
            log.error("bits in orderId not same with bits in userId, orderId:{}, bits:{}, userId:{}, bits:{}",
                    orderId, bitsInOrderId, userId, bitsInUserId);
        }
    }

    private static final long USER_MASK = (1L << 4) - 1;
    private static long get4BitsFromUserId(long userId) {
        return userId & USER_MASK;
    }

    private static final long SEQUENCE_MASK = (1L << 18) - 1;
    private static long random18BitsSequence() {
        long randomSequence = ThreadLocalRandom.current().nextLong();
        return randomSequence & SEQUENCE_MASK;
    }

}
