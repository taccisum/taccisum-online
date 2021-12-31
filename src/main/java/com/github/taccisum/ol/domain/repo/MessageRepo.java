package com.github.taccisum.ol.domain.repo;

import com.github.taccisum.ol.dao.mapper.MessageMapper;
import com.github.taccisum.ol.domain.data.MessageDO;
import com.github.taccisum.ol.domain.entity.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/31
 */
@Component
public class MessageRepo {
    @Autowired
    private MessageMapper mapper;
    @Autowired
    private Factory factory;

    public Message create(MessageDO data) {
        mapper.insert(data);
        return factory.createMessage(data.getId(), data.getType(), data.getSpType());
    }
}
