package org.example.shardingdemo.config.sharding;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lzp
 */
@Slf4j
public class ShardingStrategy {

    private static final long n = 2;
    private static final Logger log = LoggerFactory.getLogger(ShardingStrategy.class);

    public static String getShardingDatabase(Long userId, Long orderId) {
        return "ds" + (getShardingId(userId, orderId) % n);
    }

    public static String getShardingTable(Long userId, Long orderId) {
        long shardingId = getShardingId(userId, orderId);
        return "t_order" + ((shardingId / n) % n);
    }

    private static long getShardingId(Long userId, Long orderId) {
        long shardingId = 0L;
        if(userId != null) {
            shardingId = extractShardingKeyFromUserId(userId);
        }
        if(orderId != null) {
            long shardingOrderId = extractShardingKeyFromOrderId(orderId);
            if(shardingOrderId != shardingId) {
                log.error("sharding not matched, userId:({}), orderId:({}).", userId, orderId);
                throw new RuntimeException("userId, orderId not matched.");
            }
        }
        return shardingId;
    }

    private static final long USER_MASK = (1L << 4) - 1;
    private static long extractShardingKeyFromUserId(long userId) {
        return userId & USER_MASK;
    }

    private static long extractShardingKeyFromOrderId(long orderId) {
        // extract 4 bit of userId from orderId
        // orderId: sign(1bit) + epoch_from_2024 (41bit, support 90years?) + end_bit_from_userId (4bit) + random (18bit)
        return (orderId >> 18) & USER_MASK;
    }

}
