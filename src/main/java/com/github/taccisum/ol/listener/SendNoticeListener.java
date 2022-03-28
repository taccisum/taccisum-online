package com.github.taccisum.ol.listener;

import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.sp.Pigeon;
import com.github.taccisum.ol.domain.repo.ServiceProviderRepo;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/2/28
 */
@Component
@Profile("prod")
public class SendNoticeListener {
    @Resource
    private ServiceProviderRepo serviceProviderRepo;

    @EventListener(ApplicationStartedEvent.class)
    public void onStarted(ApplicationStartedEvent e) {
        serviceProviderRepo.<Pigeon>getAndCast(ServiceProvider.Type.PIGEON)
                .getAccount()
                .sendTemplateMessage(7, "514162920@qq.com", "robot_01@smtp.66cn.top");
    }

    @EventListener(ApplicationFailedEvent.class)
    public void onFailed(ApplicationFailedEvent e) {
        serviceProviderRepo.<Pigeon>getAndCast(ServiceProvider.Type.PIGEON)
                .getAccount()
                .sendTemplateMessage(8, "514162920@qq.com", "robot_01@smtp.66cn.top");
    }

    @PreDestroy
    public void onExit() {
        serviceProviderRepo.<Pigeon>getAndCast(ServiceProvider.Type.PIGEON)
                .getAccount()
                .sendTemplateMessage(9, "514162920@qq.com", "robot_01@smtp.66cn.top");
    }
}
