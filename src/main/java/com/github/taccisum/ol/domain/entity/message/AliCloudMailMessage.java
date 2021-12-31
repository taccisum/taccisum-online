package com.github.taccisum.ol.domain.entity.message;

import com.github.taccisum.ol.domain.data.MessageDO;
import com.github.taccisum.ol.domain.entity.core.sp.MailServiceProvider;
import com.github.taccisum.ol.domain.entity.sp.AliCloud;
import com.github.taccisum.ol.domain.entity.sp.AliCloudAccount;
import com.github.taccisum.ol.domain.exception.DataErrorException;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.rmi.ServerException;

/**
 * 阿里云邮件服务消息
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
@Slf4j
public class AliCloudMailMessage extends MailMessage {
    public AliCloudMailMessage(Long id) {
        super(id);
    }

    public MessageDO data() {
        throw new NotImplementedException();
    }

    @Override
    public boolean deliver() {
        boolean success = false;
        try {
            MessageDO data = this.data();
            this.getServiceProvider()
                    .getAccount("taccisum-online.mail.send@1520113260150920.onaliyun.com")
                    .sendMailVia(
                            data.getSender(),
                            data.getTarget(),
                            data.getTitle(),
                            data.getContent(),
                            data.getTag(),
                            null
                    );
            success = true;
        } catch (AliCloudAccount.MailSendException e) {
            success = false;
            if (e.getCause() instanceof ServerException) {
                log.warn(String.format("消息 %d 发送失败，阿里云服务端返回异常", this.id()), e);
            } else {
                log.error(String.format("消息 %d 发送失败", this.id()), e);
            }
        }
        this.publish(new DeliverEvent(success));
        return success;
    }

    @Override
    protected AliCloud getServiceProvider() {
        MailServiceProvider sp = super.getServiceProvider();
        if (sp instanceof AliCloud) {
            return (AliCloud) sp;
        }
        throw new DataErrorException("Message.ServiceProvider", this.id(), "阿里云邮件消息只能通过阿里云进行发送，当前提供商为 " + sp.getType().name());
    }
}
