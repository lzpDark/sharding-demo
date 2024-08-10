package org.example.shardingdemo.config.sharding;

/**
 * @author lzp
 */
public class ShardingStrategy {

    public static String getShardingDatabase(long orderId) {
        return "ds" + extractShardingKey(orderId) % 2;
    }

    public static String getShardingTable(long orderId) {
        return "t_order" + ((extractShardingKey(orderId) / 2) % 2);
    }

    private static long extractShardingKey(long orderId) {
        // extract 4 bit of userId from orderId
        // orderId: epoch_from_2024 (41bit, support 90years?) + end_bit_from_userId (4bit) + random (19bit)
        long mask = (1L << 4) - 1;
        return (orderId >> 19) & mask;
    }

}
