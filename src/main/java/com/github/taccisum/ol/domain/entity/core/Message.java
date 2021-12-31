package com.github.taccisum.ol.domain.entity.core;

import com.github.taccisum.domain.core.Entity;
import com.github.taccisum.domain.core.Event;
import com.github.taccisum.ol.domain.entity.core.sp.MessageServiceProvider;

/**
 * 代表一条具体的消息，可以是短信、推送、微信等等
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
public abstract class Message extends Entity.Base<Long> {
    public Message(Long id) {
        super(id);
    }

    /**
     * <pre>
     * 将此条消息投递到第三方服务商
     *
     * published events:
     * {@link DeliverEvent}
     * </pre>
     *
     * @return 投递结果
     */
    public abstract boolean deliver();

    /**
     * 获取此消息关联的服务提供商
     */
    protected abstract MessageServiceProvider getServiceProvider();

    protected static abstract class BaseEvent extends Event.Base<Message> {
    }

    /**
     * 消息投递事件（表示已投递到三方服务商，但不一定已发送）
     */
    public static class DeliverEvent extends BaseEvent {
        /**
         * 表示是否投递成功
         */
        private Boolean success;

        public DeliverEvent(Boolean success) {
            this.success = success;
        }
    }

    /**
     * 消息已发送事件（三方服务商已将消息推送给用户，可能成功也可能失败）
     */
    public static class SentEvent extends BaseEvent {
    }
}
