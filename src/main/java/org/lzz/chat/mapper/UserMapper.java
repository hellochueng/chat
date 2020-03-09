package org.lzz.chat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.lzz.chat.domain.User;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Insert("insert into user(id,username,name,pwd,sex,email) values(#{id},#{username},#{name},#{pwd},#{sex},#{email})")
    int insertUser(User user);
}
