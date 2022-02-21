package com.github.taccisum.ol.domain.entity.sp;

import com.github.taccisum.domain.core.Entity;
import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.exception.DataNotFoundException;
import com.github.taccisum.ol.domain.repo.ThirdAccountRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.3
 */
public class Pigeon extends Entity.Base<ServiceProvider.Type> implements ServiceProvider {
    @Autowired
    private ThirdAccountRepo thirdAccountRepo;
    @Getter
    private String url;

    public Pigeon(String url) {
        super(Type.PIGEON);
        this.url = url;
    }

    public PigeonAccount getAccount() throws DataNotFoundException {
        return (PigeonAccount) thirdAccountRepo.getBySpTypeAndUsername(Type.PIGEON, "default")
                .orElseThrow(() -> new DataNotFoundException("PigeonAccount", "default"));
    }
}
