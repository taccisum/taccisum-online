package com.github.taccisum.ol.domain.repo;

import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
@Component
public class ServiceProviderRepo {
    private static Map<ServiceProvider.Type, ServiceProvider> spMap = new HashMap<>();

    @Autowired
    private Factory factory;

    public ServiceProvider get(ServiceProvider.Type id) {
        ServiceProvider sp = spMap.get(id);
        if (sp == null) {
            sp = factory.createServiceProvider(id);
            spMap.put(id, sp);
        }

        if (sp == null) {
            throw new IllegalArgumentException(id.name());
        }
        return sp;
    }

    public <T> T getAndCast(ServiceProvider.Type id) {
        return (T) this.get(id);
    }
}
