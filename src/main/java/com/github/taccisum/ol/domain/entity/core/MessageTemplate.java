package com.github.taccisum.ol.domain.entity.core;

import com.github.taccisum.domain.core.Entity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * 消息模板，可以是短信模板、邮件模板等等
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
public abstract class MessageTemplate extends Entity.Base<Long> {
    public MessageTemplate(Long id) {
        super(id);
    }

    /**
     * 使用当前模板创建出一条新的待发送消息实例
     *
     * @param target 消息目标
     * @param params 模板参数
     */
    public Message initMessage(String target, List<String> params) {
        throw new NotImplementedException();
    }
}
