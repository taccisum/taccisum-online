package com.github.taccisum.ol.controller.dev;

import com.github.taccisum.ol.domain.entity.core.MessageTemplate;
import com.github.taccisum.ol.domain.repo.MessageTemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
@RequestMapping("/dev/messages")
@RestController
public class MessageController4Dev {
    @Autowired
    private MessageTemplateRepo messageTemplateRepo;

    @PostMapping
    public boolean deliver(Long templateId, String target, List<String> params) {
        MessageTemplate template = messageTemplateRepo.getOrThrow(templateId);
        return template.initMessage(target, params)
                .deliver();
    }
}
