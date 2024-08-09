package org.example.shardingdemo;

import org.example.shardingdemo.entity.Review;
import org.example.shardingdemo.mapper.ReviewMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author lzp
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingTest {

    @Resource
    ReviewMapper reviewMapper;

    @Test
    public void addReview() {
        for(int i = 0; i < 10; i++) {
            Review review = new Review();
            review.setId(Long.parseLong("" + (i + 1)));
            review.setUserId(Long.parseLong("" + (i + 1)));
            review.setText("Hi:" + i);
            reviewMapper.insert(review);
        }
    }

    @Test
    public void searchReview() {
        System.out.println(reviewMapper.selectById(1));
    }
}
