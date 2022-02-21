package com.github.taccisum.ol.domain.event.handler;

import com.github.taccisum.ol.domain.entity.core.Message;
import com.github.taccisum.ol.domain.entity.sp.Pigeon;
import org.springframework.stereotype.Component;

/**
 * 针对阿里云邮件的特殊逻辑处理，避免污染主业务逻辑
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/31
 * @deprecated use {@link Pigeon} instead
 */
@Component
public class AliMailSpecialEventSubscriber implements DomainEventSubscriber {
    @Override
    public void listen(Message.DeliverEvent e) throws Throwable {
        // TODO:: 未开启邮件跟踪的话只能特别处理，假定邮件投递成功即为发送成功
        e.getPublisher().publish(new Message.SentEvent(e.getSuccess()));
    }
}
