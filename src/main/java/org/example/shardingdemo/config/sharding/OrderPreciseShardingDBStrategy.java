package org.example.shardingdemo.config.sharding;

import groovy.util.logging.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author lzp
 */
@Slf4j
public class OrderPreciseShardingDBStrategy implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        Long orderId = preciseShardingValue.getValue();
        String dbName = ShardingStrategy.getShardingDatabase(orderId);
        if(collection.contains(dbName)) {
            return dbName;
        }
        throw new RuntimeException("error sharding database with orderId:" + orderId + ", dbName:" + dbName);
    }

}
