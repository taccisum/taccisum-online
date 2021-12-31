package com.github.taccisum.ol.domain.event.handler;

import com.github.taccisum.ol.domain.entity.core.Message;
import org.springframework.stereotype.Component;

/**
 * 更新消息状态的事件订阅器
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/31
 */
@Component
public class UpdateMessageStatusEventSubscriber implements DomainEventSubscriber {
    @Override
    public void listen(Message.SentEvent e) throws Throwable {
        e.getPublisher().markSent(e.getSuccess());
    }
}
