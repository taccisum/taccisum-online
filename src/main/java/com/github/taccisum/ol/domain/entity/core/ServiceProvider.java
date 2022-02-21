package com.github.taccisum.ol.domain.entity.core;

import com.github.taccisum.domain.core.Entity;

import static com.github.taccisum.ol.domain.entity.core.ServiceProvider.Type;

/**
 * 服务提供商
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
public interface ServiceProvider extends Entity<Type> {
    default Type getType() {
        return this.id();
    }

    enum Type {
        /**
         * 阿里云
         */
        ALI_CLOUD,
        /**
         * 腾讯云
         */
        TENCENT_CLOUD,
        /**
         * 高德地图
         */
        AMAP,
    }
}
