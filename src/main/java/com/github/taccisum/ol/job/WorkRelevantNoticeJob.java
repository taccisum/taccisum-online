package com.github.taccisum.ol.job;

import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.sp.Amap;
import com.github.taccisum.ol.domain.entity.sp.AmapAccount;
import com.github.taccisum.ol.domain.exception.DomainException;
import com.github.taccisum.ol.domain.repo.ServiceProviderRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
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
    @Resource
    private RestTemplate restTemplate;

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
            HashMap<String, Object> params = new HashMap<>();
            params.put("template_id", 4);
            params.put("target", "514162920@qq.com");
            params.put("sender", "taccisum-online");
            Long msgId = restTemplate.postForEntity("http://120.25.107.93:8082/messages?templateId={template_id}&target={target}", null, Long.class, params)
                    .getBody();
        }
    }
}
