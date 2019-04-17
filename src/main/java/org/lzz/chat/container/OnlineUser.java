package org.lzz.chat.container;

import org.lzz.chat.domain.User;

public interface OnlineUser {

    void addUser(User user);

    void removeUser(User user);

    Long countUser();
}
