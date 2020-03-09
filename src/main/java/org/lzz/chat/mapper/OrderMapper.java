package org.lzz.chat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.lzz.chat.domain.Order;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface OrderMapper {

    @Insert("insert into order values(#{id},#{serianumber},#{createtime},#{price})")
    int insertOrder(Order order);
}
