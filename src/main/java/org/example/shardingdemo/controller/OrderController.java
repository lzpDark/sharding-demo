package org.example.shardingdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.shardingdemo.entity.Order;
import org.example.shardingdemo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lzp
 */
@RequestMapping("/orders")
@RestController
public class OrderController {

    @Autowired
    OrderMapper orderMapper;

    @PostMapping("")
    public Boolean create(@RequestBody Order order) {
        return orderMapper.insert(order) > 0;
    }

    @GetMapping("")
    public List<Order> list() {
        Page<Order> page = new Page<>(1, 10);
        List<Order> orders = orderMapper.selectList(page, Wrappers.<Order>lambdaQuery().orderByAsc(Order::getOrderId));
        return orders;
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable("id") Long id) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", id);
        Order order = orderMapper.selectOne(wrapper);
        return order;
    }
}
