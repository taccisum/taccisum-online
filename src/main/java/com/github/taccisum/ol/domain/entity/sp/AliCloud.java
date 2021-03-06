package com.github.taccisum.ol.domain.entity.sp;

import com.github.taccisum.domain.core.Entity;
import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.core.ThirdAccount;
import com.github.taccisum.ol.domain.entity.core.sp.MailServiceProvider;
import com.github.taccisum.ol.domain.exception.DataNotFoundException;
import com.github.taccisum.ol.domain.exception.DomainException;
import com.github.taccisum.ol.domain.repo.ThirdAccountRepo;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 阿里云
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
@Slf4j
public class AliCloud extends Entity.Base<ServiceProvider.Type> implements
        ServiceProvider,
        MailServiceProvider {
    @Autowired
    private ThirdAccountRepo thirdAccountRepo;

    public AliCloud() {
        super(Type.ALI_CLOUD);
    }

    /**
     * 获取阿里云账号实体
     *
     * @param id 账号 id
     */
    public AliCloudAccount getAccount(long id) throws DataNotFoundException {
        DomainException ex = new DataNotFoundException("阿里云账号", id);
        ThirdAccount account = thirdAccountRepo.get(id)
                .orElseThrow(() -> ex);

        if (account instanceof AliCloudAccount) {
            return (AliCloudAccount) account;
        }
        throw ex;
    }

    /**
     * 获取阿里云账号实体，可以直接获取主账号或子账号（无主账号权限时）
     *
     * @param name 账号名，主账号（账号 id，示例：15201132xxxxxxxx）或子账号（完全名称，示例：sub@15201132xxxxxxxx.onaliyun.com）均可
     */
    public AliCloudAccount getAccount(String name) throws DataNotFoundException {
        DomainException ex = new DataNotFoundException("阿里云账号", name);
        ThirdAccount account = thirdAccountRepo.getByUsername(name)
                .orElseThrow(() -> ex);

        if (account instanceof AliCloudAccount) {
            return (AliCloudAccount) account;
        }
        throw ex;
    }

    public enum Region {
        /**
         * 中国杭州
         */
        HANG_ZHOU("cn-hangzhou"),
        /**
         * 中国上海
         */
        SHANG_HAI("cn-shanghai"),
        ;

        @Getter
        @Accessors(fluent = true)
        private String key;

        Region(String key) {
            this.key = key;
        }

        /**
         * <pre>
         * 获取此区域对应的 oss endpoint url
         *
         * 示例：
         * - {@link #HANG_ZHOU} -> https://oss-cn-hangzhou.aliyuncs.com
         * </pre>
         */
        public String getOssEndpoint() {
            return String.format("https://oss-%s.aliyuncs.com", this.key);
        }
    }
}
