package com.github.taccisum.ol.domain.entity.sp;

import com.github.taccisum.domain.core.Entity;
import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.core.sp.MailServiceProvider;
import lombok.Getter;
import lombok.experimental.Accessors;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * 阿里云
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
public class AliCloud extends Entity.Base<Long> implements
        ServiceProvider,
        MailServiceProvider {

    public AliCloud(Long id) {
        super(id);
    }

    /**
     * 获取阿里云账号实体，可以直接获取主账号或子账号（无主账号权限时）
     *
     * @param name 账号名，主账号（账号 id，示例：15201132xxxxxxxx）或子账号（完全名称，示例：sub@15201132xxxxxxxx.onaliyun.com）均可
     */
    public AliCloudAccount getAccount(String name) {
        throw new NotImplementedException();
    }

    @Override
    public Type getType() {
        return Type.ALI_CLOUD;
    }

    public enum Region {
        /**
         * 中国杭州
         */
        HANG_ZHOU("cn-hangzhou");

        @Getter
        @Accessors(fluent = true)
        private String key;

        Region(String key) {
            this.key = key;
        }
    }
}
