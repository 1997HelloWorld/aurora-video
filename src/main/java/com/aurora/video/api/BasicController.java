package com.aurora.video.api;

import com.aurora.video.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BasicController {
    @Autowired
    public RedisOperator redisOperator;

    public static final String USER_REDIS_SESSION = "user_redis_session";
}