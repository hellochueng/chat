package org.lzz.chat.container.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.lzz.chat.container.TokenUser;
import org.lzz.chat.domain.User;
import org.lzz.chat.entity.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenUserImpl implements TokenUser {

    //id对应用户信息集合
    public final static String USERLIST = "USERLIST";

    //不同端对应token的hash名字
    public final static String ONLINETOEKN = "ONLINETOEKN";

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${user.expire.second}")
    private Long expireSecond;

    /**
     * 登录使用
     * 获取token信息
     */
    @Override
    public String addToken(ClientType clientType, User user) {

        String token = RandomStringUtils.random(32, true, true);

        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(ONLINETOEKN);

        //判断ONLINETOEKN hash表是否存在
        if(boundHashOperations.hasKey(clientType.getInfo()+user.getId())) {

            //获取在线token  hash集合中的旧token
            String oldToken = (String)boundHashOperations.get(clientType.getInfo()+user.getId());

            //获取旧token中的id值
            String id = (String)redisTemplate.opsForValue().get(oldToken);

            //删除旧token
            redisTemplate.delete(oldToken);

            //在线token hash集合加入新token
            boundHashOperations.put(clientType.getInfo()+user.getId(),token);

            //设置新的登录信息
            redisTemplate.opsForValue().set(token,id);
        } else {

            //在线token hash集合加入新token
            boundHashOperations.put(clientType.getInfo()+user.getId(),token);

            //设置新的登录信息
            redisTemplate.opsForValue().set(token,user.getId());

            BoundHashOperations userList = redisTemplate.boundHashOps(USERLIST);

            if(!userList.hasKey(user.getId())) {

                userList.put(user.getId(), user);
            }

            //设置过期时间
            redisTemplate.opsForValue().set(token,user.getId());
        }

        redisTemplate.expire(token,expireSecond, TimeUnit.SECONDS);

        return token;
    }

    @Override
    public User getUserByToken(String token) {
        //获取旧token中的id值
        String id = (String)redisTemplate.opsForValue().get(token);

        BoundHashOperations userList = redisTemplate.boundHashOps(USERLIST);

        return (User)userList.get(id);
    }

    /**
     * 删除用户对应客服端的登录状态
     * @param clientType
     * @param id
     */
    @Override
    public void deleteToken(ClientType clientType, String id) {

        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(ONLINETOEKN);

        if(boundHashOperations.hasKey(clientType.getInfo()+id)) {

            String token = (String)boundHashOperations.get(clientType.getInfo()+id);

            redisTemplate.delete(token);

            boundHashOperations.delete(clientType.getInfo()+id);
        }
    }
}
