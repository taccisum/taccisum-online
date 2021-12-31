package com.github.taccisum.ol.domain.entity.core;

import com.github.taccisum.domain.core.Entity;
import com.github.taccisum.ol.dao.mapper.MessageTemplateMapper;
import com.github.taccisum.ol.domain.data.MessageDO;
import com.github.taccisum.ol.domain.data.MessageTemplateDO;
import com.github.taccisum.ol.domain.repo.MessageRepo;
import com.github.taccisum.ol.domain.repo.ServiceProviderRepo;
import com.github.taccisum.ol.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * 消息模板，可以是短信模板、邮件模板等等
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
public abstract class MessageTemplate extends Entity.Base<Long> {
    @Autowired
    private MessageTemplateMapper dao;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private ServiceProviderRepo serviceProviderRepo;

    public MessageTemplate(Long id) {
        super(id);
    }

    public MessageTemplateDO data() {
        return dao.selectById(this.id());
    }

    /**
     * 使用当前模板创建出一条新的待发送消息实例
     *
     * @param sender 发送人地址
     * @param target 消息目标
     * @param params 模板参数
     */
    public Message initMessage(String sender, String target, List<String> params) {
        MessageTemplateDO data = this.data();
        MessageDO o = new MessageDO();
        o.setType(this.getMessageType());
        o.setSpType(data.getSpType());
        o.setSpAccountId(data.getSpAccountId());
        o.setSender(sender);
        o.setTarget(target);
        o.setTemplateId(this.id());
        o.setParams(JsonUtils.stringify(params));
        o.setTitle(data.getTitleSnapshot());
        o.setContent(data.getContentSnapshot());
        o.setTag(data.getTag());
        return messageRepo.create(o);
    }

    protected ServiceProvider getServiceProvider() {
        return this.serviceProviderRepo.get(this.data().getSpType());
    }

    /**
     * 获取模板关联的消息类型
     */
    protected abstract Message.Type getMessageType();
}
