package org.lzz.chat.container.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.lzz.chat.container.TalkRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *           /  roomName
 * ROOM ----
 *           \  roomId
 *
 *                    \ 房间id对应的list存放用户id表示房间内的人
 *                         用户id
 *
 */

@Service
public class TalkRoomImpl implements TalkRoom {

    public final static String ROOM = "ROOM";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void addRoom(String id,String room) {
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(ROOM);

        String roomId = null;
        if(boundHashOperations.hasKey(room)){

            roomId = (String)boundHashOperations.get(room);
        } else {
            roomId = RandomStringUtils.random(9, true, true);
            boundHashOperations.put(room,roomId);
        }

        BoundSetOperations roomOP = redisTemplate.boundSetOps(roomId);

        roomOP.add(id);
    }


    @Override
    public void removeRoom(String room) {
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(ROOM);

        String roomId = (String)boundHashOperations.get(room);

        redisTemplate.delete(roomId);

        boundHashOperations.delete(room);
    }

    @Override
    public Map<String,String> getAllRoom() {
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(ROOM);

        return boundHashOperations.entries();
    }

    @Override
    public Set<String> getRoomUser(String room) {
        HashOperations hashOperations = redisTemplate.opsForHash();

        String roomId = (String)hashOperations.get(ROOM,room);

        BoundSetOperations roomOP = redisTemplate.boundSetOps(roomId);
        return roomOP.members();
    }


}
