package org.example.shardingdemo.config.sharding;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lzp
 */
public final class OrderKeyGenerator {

    private static final long EPOCH;
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.FEBRUARY, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }

    private static final AtomicLong sequence = new AtomicLong();

    /**
     *
     * the id is generated in 3 parts: [41 bits timestamp][4 bits userId][19 bits randomSequence], all 64 bits as 'long' type
     *  41 bits timestamp: milliseconds from 2024-01-01/00:00:00 to now
     *  4 bits userId: last 4 bits of userId
     *  19 bits randomSequence: random number
     *
     * @param userId use it to generate primary key id
     * @return primary key id
     */
    public static long generateKey(long userId) {
        long currentMilliseconds = System.currentTimeMillis();
        long randomSequence = ThreadLocalRandom.current().nextLong();
        long id = currentMilliseconds - EPOCH << 22
                | extractBits(userId, 4, 1)
                | extractBits(randomSequence, 19, 1);
        debugKey(id);
        return id;
    }

    public static void debugKey(long id) {
        System.out.println(extractBits(id, 64, 19 + 4  + 1));;
        System.out.println(extractBits(id, 19 + 4, 19 + 1));;
        System.out.println(extractBits(id, 19, 0));
    }
//11110111000010010111101101110111 11000 1110001100101001000
    // num: 011111101011
    // left:4, right:3
    // res: 010
    static long extractBits(long num, int left, int right) {
        int extractLength = left - right + 1;
        long mask = (1L << extractLength) - 1;
        return (num >> right) & mask;
    }


}
