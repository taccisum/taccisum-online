package com.github.taccisum.ol.domain.entity.sp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.taccisum.ol.domain.entity.core.ThirdAccount;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * 高德地图开放平台账号
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.3
 */
public class AmapAccount extends ThirdAccount {
    @Autowired
    private RestTemplate restTemplate;

    public AmapAccount(Long id) {
        super(id);
    }

    /**
     * 将地址转换为地理编码 geocode
     *
     * @param address 地址
     * @return 高德 adcode
     */
    public String toGeocode(String address) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("key", this.data().getAppId());
        params.put("address", address);
        GeoCodeResponse body = restTemplate.getForEntity("https://restapi.amap.com/v3/geocode/geo?key={key}&address={address}", GeoCodeResponse.class, params)
                .getBody();
        return body.getGeoCodes().get(0).getAdcode();
    }

    /**
     * 获取天气信息
     *
     * @param adcode 目标位置地理编码
     */
    public WhetherInfoResponse getWhetherInfo(String adcode) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("key", this.data().getAppId());
        params.put("city", adcode);
        WhetherInfoResponse body = restTemplate.getForEntity("https://restapi.amap.com/v3/weather/weatherInfo?key={key}&city={city}&extensions=all", WhetherInfoResponse.class, params)
                .getBody();
        return body;
    }

    @Data
    public static class GeoCodeResponse {
        @JsonProperty(value = "geocodes")
        private List<GeoCode> geoCodes;

        @Data
        public static class GeoCode {
            @JsonProperty(value = "formatted_address")
            private String formattedAddress;
            private String country;
            private String province;
            private String adcode;
            private String citycode;
        }
    }

    @Data
    public static class WhetherInfoResponse {
        private Lives lives;
        private List<Forecast> forecasts;

        @Data
        public static class Lives {
        }

        @Data
        public static class Forecast {
            @JsonProperty("reporttime")
            private String reportTime;
            private List<Casts> casts;

            @Data
            public static class Casts {
                private String week;
                @JsonProperty("dayweather")
                private String dayWeather;
                @JsonProperty("nightweather")
                private String nightWeather;
            }
        }
    }
}
