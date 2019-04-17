package org.lzz.chat.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

public class Mes {

    private static ObjectMapper gson = new ObjectMapper();

    private String token;

    private String room;

    private String message;

    public static Mes strJson2Mage(String message) throws Exception{
        return StringUtils.isEmpty(message) ? null : gson.readValue(message, Mes.class);
    }

    public String toJson() throws Exception{
        return gson.writeValueAsString(this);
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
