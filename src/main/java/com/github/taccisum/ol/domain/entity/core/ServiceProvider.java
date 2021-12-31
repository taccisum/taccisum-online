package com.github.taccisum.ol.domain.entity.core;

import com.github.taccisum.domain.core.Entity;

/**
 * 服务提供商
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
public interface ServiceProvider extends Entity<Long> {
    Type getType();

    enum Type {
        /**
         * 阿里云
         */
        ALI_CLOUD,
    }
}
