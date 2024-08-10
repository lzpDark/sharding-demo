package org.example.shardingdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.example.shardingdemo.config.sharding.OrderKeyGenerator;

/**
 * @author lzp
 */
@TableName(value = "t_order")
public class Order {
    @TableId
    private Long orderId;
    private Long userId;
    private String name;

    public Order(Long userId) {
        this.orderId = OrderKeyGenerator.generateKey(userId);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                '}';
    }
}
