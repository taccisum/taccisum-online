package com.github.taccisum.ol.job;

import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.sp.Amap;
import com.github.taccisum.ol.domain.entity.sp.AmapAccount;
import com.github.taccisum.ol.domain.entity.sp.Pigeon;
import com.github.taccisum.ol.domain.exception.DomainException;
import com.github.taccisum.ol.domain.repo.ServiceProviderRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 与个人工作相关的提醒 job
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.3
 */
@Component
public class WorkRelevantNoticeJob {
    @Resource
    private ServiceProviderRepo serviceProviderRepo;

    /**
     * 每周日 13 点执行
     */
    @Scheduled(cron = "0 0 13 * * 0")
    public void index() {
        Amap amap = serviceProviderRepo.getAndCast(ServiceProvider.Type.AMAP);
        AmapAccount account = amap.getApplication("taccisum-online.web-be");

        String adcode = account.toGeocode("广东省广州市番禺区祈福新村");
        AmapAccount.WhetherInfoResponse weather = account.getWhetherInfo(adcode);

        AmapAccount.WhetherInfoResponse.Forecast.Casts cast = weather.getForecasts().get(0).getCasts().stream()
                .filter(c -> Objects.equals(c.getWeek(), "1"))
                .findFirst()
                .orElse(null);

        if (cast == null) {
            throw new DomainException("找不到星期一天气信息");
        } else if (cast.getDayWeather().contains("雨")) {
            serviceProviderRepo.<Pigeon>getAndCast(ServiceProvider.Type.PIGEON)
                    .getAccount()
                    .sendTemplateMessage(4, "514162920@qq.com", "robot_01@smtp.66cn.top");
        }
    }
}
