package com.github.taccisum.ol.domain.repo;

import com.github.taccisum.ol.dao.mapper.MessageTemplateMapper;
import com.github.taccisum.ol.domain.data.MessageTemplateDO;
import com.github.taccisum.ol.domain.entity.core.MessageTemplate;
import com.github.taccisum.ol.domain.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
@Component
public class MessageTemplateRepo {
    @Autowired
    private MessageTemplateMapper mapper;
    @Autowired
    private Factory factory;

    public Optional<MessageTemplate> get(long id) {
        MessageTemplateDO data = mapper.selectById(id);
        if (data == null) {
            return Optional.empty();
        }
        return Optional.of(factory.createMessageTemplate(data.getId()));
    }

    public MessageTemplate getOrThrow(long id) throws MessageTemplateNotFoundException {
        return this.get(id)
                .orElseThrow(() -> new MessageTemplateNotFoundException(id));
    }

    public static class MessageTemplateNotFoundException extends DataNotFoundException {
        public MessageTemplateNotFoundException(long id) {
            super("消息模板", id);
        }
    }
}
