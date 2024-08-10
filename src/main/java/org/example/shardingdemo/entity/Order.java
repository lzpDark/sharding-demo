package org.example.shardingdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author lzp
 */
@TableName(value = "t_order")
public class Order {
    private Long orderId;
    private String name;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
