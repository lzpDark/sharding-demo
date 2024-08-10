package org.example.shardingdemo.config.sharding;

import groovy.util.logging.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author lzp
 */
@Slf4j
public class OrderPreciseShardingDBStrategy implements ComplexKeysShardingAlgorithm<Long> {

    private static final Logger log = LoggerFactory.getLogger(OrderPreciseShardingDBStrategy.class);

    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Long> complexKeysShardingValue) {
        Map<String, Collection<Long>> columnShardingValue =
                complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        Collection<Long> userIdValues = columnShardingValue.get("user_id");
        if (CollectionUtils.isEmpty(userIdValues)) {
            log.error("no userId assigned.");
            throw new RuntimeException("no userId assigned.");
        }
        Long userId = userIdValues.stream().findFirst().get();
        Collection<Long> orderIdValues = columnShardingValue.get("order_id");
        Iterator<Long> iterator = orderIdValues.stream().iterator();
        List<String> resultDatabase = new ArrayList<>();
        while (iterator.hasNext()) {
            Long orderId = iterator.next();
            String shardingDatabase = ShardingStrategy.getShardingDatabase(userId, orderId);
            if (collection.contains(shardingDatabase)) {
                resultDatabase.add(shardingDatabase);
            } else {
                throw new RuntimeException("error sharding database with userId:" + userId + ", orderId:" + orderId + ", dbName:" + shardingDatabase);
            }
        }
        return resultDatabase;
    }
}
