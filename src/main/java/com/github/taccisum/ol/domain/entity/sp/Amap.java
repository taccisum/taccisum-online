package com.github.taccisum.ol.domain.entity.sp;

import com.github.taccisum.domain.core.Entity;
import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.exception.DataNotFoundException;
import com.github.taccisum.ol.domain.repo.ThirdAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 高德地图
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.3
 */
public class Amap extends Entity.Base<ServiceProvider.Type> implements ServiceProvider {
    @Autowired
    private ThirdAccountRepo thirdAccountRepo;

    public Amap() {
        super(Type.AMAP);
    }

    /**
     * 获取高德开放平台应用
     *
     * @param name {应用 name}.{key name}
     */
    public AmapAccount getApplication(String name) {
        return (AmapAccount) thirdAccountRepo.getByUsername(name)
                .orElseThrow(() -> new DataNotFoundException("高德地图账号", name));
    }
}
