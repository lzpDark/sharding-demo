package org.example.shardingdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.shardingdemo.config.sharding.OrderKeyGenerator;
import org.example.shardingdemo.entity.Order;
import org.example.shardingdemo.mapper.OrderMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lzp
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingOrderTest {

    @Resource
    OrderMapper orderMapper;

    @Before
    public void clearData() {
        orderMapper.delete(null);
    }

    @After
    public void clearDataAfter() {
        orderMapper.delete(null);
    }


    @Test
    public void testOrder() {
        for(long i = 1; i < 10; i++) {
            Order order = new Order();
            order.setOrderId(i);
            order.setUserId(i);
            order.setName("order-" + i);
            orderMapper.insert(order);
        }
    }

    @Test
    public void testList() {
        for(long i = 0; i < 20; i++) {
            Order order = new Order();
//            order.setOrderId(OrderKeyGenerator.generateKey(i));
            order.setUserId(i);
            order.setName("order-" + i);
            orderMapper.insert(order);
        }
        Page<Order> page = new Page<>(1, 10);
        List<Order> orders = orderMapper.selectList(page, Wrappers.<Order>lambdaQuery().orderByAsc(Order::getOrderId));
        Assert.assertEquals(10, orders.size());
        Assert.assertEquals("order-0", orders.get(0).getName());
    }

    @Test
    public void testSelectOne() {
        Order order = new Order();
        order.setName("order-1");
        long orderId = OrderKeyGenerator.generateKey(1L);
        order.setOrderId(orderId);
        order.setUserId(1L);
        orderMapper.insert(order);

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        Order res = orderMapper.selectOne(wrapper);
        Assert.assertNotNull(res);
    }
}
