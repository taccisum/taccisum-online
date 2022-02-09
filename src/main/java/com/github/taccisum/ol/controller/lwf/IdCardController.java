package com.github.taccisum.ol.controller.lwf;

import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import com.github.taccisum.ol.domain.entity.sp.TencentCloud;
import com.github.taccisum.ol.domain.entity.sp.TencentCloudAccount;
import com.github.taccisum.ol.domain.exception.DomainException;
import com.github.taccisum.ol.domain.repo.ServiceProviderRepo;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/2/7
 */
@Slf4j
@RestController
@RequestMapping("apis/lwf/id-cards")
public class IdCardController {
    @Resource
    private ServiceProviderRepo serviceProviderRepo;

    /**
     * 找出正反面区域并裁剪图片
     */
    @Timed(percentiles = {0.5, 0.95}, histogram = true)
    @PostMapping("crops")
    public String[] cropIdCard(@RequestBody MultipartFile file, @RequestParam Boolean fake, @RequestParam String side) throws IOException {
        if (Boolean.TRUE.equals(fake)) {
            BufferedReader br = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:1.txt").getPath()));
            StringBuilder sb = new StringBuilder();
            while (br.ready()) {
                sb.append(br.readLine()).append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            return new String[]{
                    sb.toString()
            };
        }

        TencentCloud tencent = serviceProviderRepo.getAndCast(ServiceProvider.Type.TENCENT_CLOUD);
        TencentCloudAccount lwfSvcAccount = tencent.getAccount("lwf_svc");

        TencentCloudAccount.IDCardSide sideEnum = null;
        try {
            sideEnum = TencentCloudAccount.IDCardSide.valueOf(side);
        } catch (IllegalArgumentException ignored) {
        }

        byte[] imgBytes = file.getBytes();
        if (sideEnum == null) {
            // 正反面区域均识别
            String front = null;
            String back = null;
            List<String> errs = new ArrayList<>();
            try {
                front = lwfSvcAccount.crop(Base64.getEncoder().encodeToString(imgBytes), TencentCloudAccount.IDCardSide.FRONT);
            } catch (TencentCloudAccount.OApiAccessException e) {
                log.warn("身份证正面识别失败", e);
                errs.add(e.getMessage());
            }
            try {
                back = lwfSvcAccount.crop(Base64.getEncoder().encodeToString(imgBytes), TencentCloudAccount.IDCardSide.BACK);
            } catch (TencentCloudAccount.OApiAccessException e) {
                log.warn("身份证反面识别失败", e);
                errs.add(e.getMessage());
            }
            if (front == null && back == null) {
                throw new DomainException("身份证识别失败：%s", errs);
            }
            return new String[]{front, back};
        } else {
            return new String[]{
                    lwfSvcAccount.crop(Base64.getEncoder().encodeToString(imgBytes), sideEnum)
            };
        }
    }
}
