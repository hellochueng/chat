package org.lzz.chat.container;

import org.lzz.chat.domain.User;
import org.lzz.chat.entity.ClientType;

public interface TokenUser {

    void deleteToken(ClientType clientType, String id);

    String addToken(ClientType clientType, User user);

    User getUserByToken(String token);
}
