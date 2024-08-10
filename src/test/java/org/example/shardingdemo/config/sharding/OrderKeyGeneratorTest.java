package org.example.shardingdemo.config.sharding;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lzp
 */
class OrderKeyGeneratorTest {

    @Test
    void extractBits() {
        long l = OrderKeyGenerator.extractBits(1, 4, 1);
        Assert.assertEquals(0, l);
    }
}