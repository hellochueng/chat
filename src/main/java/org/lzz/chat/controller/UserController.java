package org.lzz.chat.controller;


import org.apache.commons.lang3.RandomStringUtils;
import org.lzz.chat.container.TokenUser;
import org.lzz.chat.domain.User;
import org.lzz.chat.entity.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TokenUser tokenUser;

    @RequestMapping("/login")
    public String login(ClientType clientType, String username, String pwd) {

        String id = RandomStringUtils.random(6, true, true);

        User user = new User();
        user.setId(id);
        user.setName(username);
        user.setPwd(pwd);

        return tokenUser.addToken(clientType,user);
    }
}
