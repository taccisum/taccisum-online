package com.github.taccisum.ol.domain.entity.sp;

import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.core.ThirdAccount;
import com.github.taccisum.ol.domain.repo.ServiceProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 0.4
 */
public class PigeonAccount extends ThirdAccount {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ServiceProviderRepo serviceProviderRepo;

    public PigeonAccount(long id) {
        super(id);
    }

    /**
     * 发送模板消息
     *
     * @param templateId 模板 id
     * @param target     目标
     * @param sender     发送者
     */
    public void sendTemplateMessage(int templateId, String target, String sender) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("template_id", templateId);

        HashMap<String, Object> body = new HashMap<>();

        body.put("target", target);
        body.put("sender", sender);
        Long msgId = restTemplate.postForEntity(this.getInstance().getUrl() + "/messages?templateId={template_id}", body, Long.class, params)
                .getBody();
    }

    private Pigeon getInstance() {
        return serviceProviderRepo.getAndCast(ServiceProvider.Type.PIGEON);
    }
}
