package com.github.taccisum.ol.domain.entity.message;

import com.github.taccisum.ol.domain.entity.core.Message;
import com.github.taccisum.ol.domain.entity.core.sp.MailServiceProvider;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * 邮件消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
public class MailMessage extends Message {
    public MailMessage(Long id) {
        super(id);
    }

    @Override
    public boolean deliver() {
        throw new NotImplementedException();
    }

    @Override
    protected MailServiceProvider getServiceProvider() {
        throw new NotImplementedException();
    }
}
