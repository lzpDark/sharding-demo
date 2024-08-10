package org.example.shardingdemo.config.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author lzp
 */
public class OrderPreciseShardingTableStrategy implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        Long orderId = preciseShardingValue.getValue();
        String tableName = ShardingStrategy.getShardingTable(orderId);
        if(collection.contains(tableName)) {
            return tableName;
        }
        throw new RuntimeException("error sharding table, orderId:" + orderId + ", tableName:" + tableName);
    }
}
