package com.github.taccisum.ol.domain.entity.sp;

import com.github.taccisum.domain.core.Entity;
import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.core.ThirdAccount;
import com.github.taccisum.ol.domain.exception.DataNotFoundException;
import com.github.taccisum.ol.domain.exception.DomainException;
import com.github.taccisum.ol.domain.repo.ThirdAccountRepo;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 腾讯云
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/2/7
 */
@Slf4j
public class TencentCloud extends Entity.Base<ServiceProvider.Type> implements
        ServiceProvider {
    @Autowired
    private ThirdAccountRepo thirdAccountRepo;

    public TencentCloud() {
        super(Type.TENCENT_CLOUD);
    }

    /**
     * 获取腾讯云账号实体，可以直接获取主账号或子账号
     *
     * @param name 账号名
     */
    public TencentCloudAccount getAccount(String name) throws DataNotFoundException {
        DomainException ex = new DataNotFoundException("腾讯云账号", name);
        ThirdAccount account = thirdAccountRepo.getByUsername(name)
                .orElseThrow(() -> ex);

        if (account instanceof TencentCloudAccount) {
            return (TencentCloudAccount) account;
        }
        throw ex;
    }

    public enum Region {
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
    }
}
