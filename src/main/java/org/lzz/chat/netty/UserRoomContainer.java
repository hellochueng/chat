package org.lzz.chat.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserRoomContainer {

    // roomid   userid  userandroom
    private static ConcurrentMap<String, ConcurrentMap<String, UserAndRoom>> map = new ConcurrentHashMap<>();

    //userid    roomid
    public static ConcurrentMap<String, String> onLine = new ConcurrentHashMap<>();

    public static ConcurrentMap<String, ConcurrentMap<String, UserAndRoom>> getMap() {
    return map;
    }

    public static void setMap(ConcurrentMap<String, ConcurrentMap<String, UserAndRoom>> map) {
        UserRoomContainer.map = map;
    }

    public static ConcurrentMap<String, String> getOnLine() {
        return onLine;
    }

    public static void setOnLine(ConcurrentMap<String, String> onLine) {
        UserRoomContainer.onLine = onLine;
    }


}
