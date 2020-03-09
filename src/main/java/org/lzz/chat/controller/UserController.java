package org.lzz.chat.controller;


import org.lzz.chat.container.TokenUser;
import org.lzz.chat.domain.User;
import org.lzz.chat.entity.ClientType;
import org.lzz.chat.mapper.UserMapper;
import org.lzz.chat.util.RandomValue;
import org.lzz.chat.util.SnowFlakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private TokenUser tokenUser;
    @Autowired private SnowFlakeGenerator snowFlakeGenerator;
    @Autowired private UserMapper userMapper;

    @RequestMapping("/login")
    public String login(ClientType clientType, String username, String pwd) {

        Long id = snowFlakeGenerator.generateLongId();

        User user = new User();
        user.setId(id);
        user.setName(username);
        user.setPwd(pwd);

        return tokenUser.addToken(clientType,user);
    }

    @RequestMapping("/setUser")
    public int set() {

        Integer a=0;
        for (int j = 0; j < 500000; j++) {
            User user = new User(snowFlakeGenerator.generateLongId(), RandomValue.getTel(), "123456", RandomValue.getChineseName(), RandomValue.getEmail(8, 20), RandomValue.name_sex);
            userMapper.insertUser(user);
            System.out.println(Thread.currentThread().getName()+j);
        }
        return 1;
    }
}
