package org.example.shardingdemo.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.shardingdemo.entity.Review;
import org.example.shardingdemo.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * @author lzp
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private static final Log log = LogFactory.getLog(ReviewController.class);
    @Autowired
    private ReviewMapper reviewMapper;

    @PostMapping("")
    public Review crate() throws SQLException {
        Review review = new Review();
        review.setUserId(Long.parseLong("" + new Random().nextInt(3)));
        review.setText("random-" + UUID.randomUUID().toString());
        log.info("insert: " + review.toString());
        int res = reviewMapper.insert(review);
        return res > 0 ? review : null;
    }

    @GetMapping("/{id}")
    public Review read(@PathVariable("id") int id) {
        Review review = reviewMapper.selectById(id);
        return review;
    }

}
