package org.lzz.chat.controller;

import org.lzz.chat.container.TalkRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private TalkRoom talkRoom;

    @RequestMapping("/getin")
    public String goRoom(){
        return "matching";
    }

    @RequestMapping("/allRoom")
    @ResponseBody
    public Map<String,String> getAllRoomName() {

        return talkRoom.getAllRoom();
    }
}
