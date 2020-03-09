package org.lzz.chat.elasticsearch.service;

import org.lzz.chat.elasticsearch.entity.GoodsInfo;
import org.lzz.chat.elasticsearch.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends ElasticsearchRepository<User,Long> {
}