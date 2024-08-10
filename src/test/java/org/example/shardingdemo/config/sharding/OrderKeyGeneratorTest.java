package org.example.shardingdemo.config.sharding;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lzp
 */
class OrderKeyGeneratorTest {

    @Test
    public void testOrderKeyGenerator() {
        // Test 1: Basic case
        long timestamp = 1234567890123L;
        int userId = 7;
        int sequence = 525;
        long expected = (timestamp << 22) | ((long)userId << 18) | (sequence & 0x7FFFF);
        long actual = OrderKeyGenerator.generateKeyWithParam(timestamp, userId, sequence);
        assertEquals(expected, actual);

        // Test 2: Edge case with minimum values
        timestamp = 0L;
        userId = 0;
        sequence = 0;
        expected = 0L;
        actual = OrderKeyGenerator.generateKeyWithParam(timestamp, userId, sequence);
        assertEquals(expected, actual);

        // Test 3: Edge case with maximum values
        timestamp = (1L << 41) - 1; // Maximum value for 41 bits
        userId = (1 << 4) - 1;      // Maximum value for 4 bits
        sequence = (1 << 18) - 1;   // Maximum value for 18 bits
        expected = (timestamp << 22) | ((long)userId << 18) | (sequence & 0x7FFFF);
        actual = OrderKeyGenerator.generateKeyWithParam(timestamp, userId, sequence);
        assertEquals(expected, actual);
    }
}