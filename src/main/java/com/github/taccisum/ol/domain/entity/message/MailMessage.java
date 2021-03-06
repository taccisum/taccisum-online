package com.github.taccisum.ol.domain.entity.message;

import com.github.taccisum.ol.domain.entity.core.Message;
import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.core.sp.MailServiceProvider;
import com.github.taccisum.ol.domain.entity.sp.Pigeon;
import com.github.taccisum.ol.domain.exception.DataErrorException;
import com.github.taccisum.ol.domain.repo.ServiceProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * 邮件消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 * @deprecated use {@link Pigeon} instead
 */
public class MailMessage extends Message {
    @Autowired
    private ServiceProviderRepo serviceProviderRepo;

    public MailMessage(Long id) {
        super(id);
    }

    @Override
    public boolean deliver() {
        throw new NotImplementedException();
    }

    @Override
    protected MailServiceProvider getServiceProvider() {
        ServiceProvider.Type type = this.data().getSpType();
        ServiceProvider sp = serviceProviderRepo.get(type);
        if (sp instanceof MailServiceProvider) {
            return (MailServiceProvider) sp;
        }
        throw new DataErrorException("MailMessage.ServiceProvider", this.id(), "邮件消息可能关联了错误的服务提供商：" + sp.getType().name() + "，请检查数据是否异常");
    }
}
