package com.github.taccisum.ol.domain.entity.message.template;

import com.github.taccisum.ol.domain.entity.core.Message;
import com.github.taccisum.ol.domain.entity.core.MessageTemplate;
import com.github.taccisum.ol.domain.entity.sp.Pigeon;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 * @deprecated use {@link Pigeon} instead
 */
public class MailTemplate extends MessageTemplate {
    public MailTemplate(Long id) {
        super(id);
    }

    @Override
    protected Message.Type getMessageType() {
        return Message.Type.MAIL;
    }
}
