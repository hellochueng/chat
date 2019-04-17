package org.lzz.chat.container;

import java.util.Map;
import java.util.List;
import java.util.Set;

public interface TalkRoom {

    void addRoom(String id,String room);

    void removeRoom(String room);

    Map<String,String> getAllRoom();

    Set<String> getRoomUser(String room);
}
