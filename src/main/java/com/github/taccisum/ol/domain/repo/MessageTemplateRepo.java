package com.github.taccisum.ol.domain.repo;

import com.github.taccisum.ol.domain.entity.core.MessageTemplate;
import com.github.taccisum.ol.domain.exception.DataNotFoundException;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
@Component
public class MessageTemplateRepo {
    public Optional<MessageTemplate> get(long id) {
        throw new NotImplementedException();
    }

    public MessageTemplate getOrThrow(long id) throws DataNotFoundException {
        throw new NotImplementedException();
    }

    public static class MessageTemplateNotFoundException extends DataNotFoundException {
        public MessageTemplateNotFoundException(long id) {
            super("消息模板", id);
        }
    }
}
