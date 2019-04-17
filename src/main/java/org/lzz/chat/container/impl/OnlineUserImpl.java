package org.lzz.chat.container.impl;


import org.lzz.chat.container.OnlineUser;
import org.lzz.chat.domain.User;
import org.lzz.chat.redis.RedisCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 在线用户set集合
 */
@Service
public class OnlineUserImpl implements OnlineUser {

    public final static String ONLINE = "ONLINE";

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void addUser(User user){
        if(redisTemplate.hasKey(ONLINE)){

            redisTemplate.boundSetOps(ONLINE).add(user.getId());
        } else {

            redisTemplate.opsForValue().set(ONLINE,user.getId());
        }
    }

    @Override
    public void removeUser(User user){

        if(redisTemplate.hasKey(ONLINE)){

            redisTemplate.boundSetOps(ONLINE).remove(user.getId());
        }
    }

    @Override
    public Long countUser() {

        return redisTemplate.boundSetOps(ONLINE).size();
    }
}
