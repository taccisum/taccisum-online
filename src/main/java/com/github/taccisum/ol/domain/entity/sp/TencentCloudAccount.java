package com.github.taccisum.ol.domain.entity.sp;

import com.github.taccisum.ol.domain.entity.core.ThirdAccount;
import com.github.taccisum.ol.domain.exception.DomainException;
import com.github.taccisum.ol.utils.JsonUtils;
import com.jayway.jsonpath.JsonPath;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRResponse;

import java.util.HashMap;

/**
 * 腾讯云账号
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/2/7
 */
public class TencentCloudAccount extends ThirdAccount {
    private static final String HOST = "ocr.tencentcloudapi.com";

    public TencentCloudAccount(Long id) {
        super(id);
    }

    /**
     * 裁剪身份证
     *
     * @param imgBase64 图片 bytes base64
     * @param side      朝向
     * @return 裁剪后的图片 bytes base64 编码
     */
    public String crop(String imgBase64, IDCardSide side) {
        try {
            Credential cred = new Credential(this.data().getAppId(), this.data().getAppSecret());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(HOST);
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);

            IDCardOCRRequest req = new IDCardOCRRequest();
            req.setImageBase64(imgBase64);
            req.setCardSide(side.name());
            HashMap<Object, Object> config = new HashMap<>();
            config.put("CropIdCard", true);
            req.setConfig(JsonUtils.stringify(config));

            IDCardOCRResponse resp = client.IDCardOCR(req);
            return JsonPath.read(resp.getAdvancedInfo(), "IdCard");
        } catch (TencentCloudSDKException e) {
            throw new OApiAccessException("身份证类裁剪（OCR）", e);
        }
    }

    /**
     * 腾讯云 Open API 访问异常
     */
    public static class OApiAccessException extends DomainException {
        public OApiAccessException(String key, TencentCloudSDKException cause) {
            super(String.format("腾讯云 Open API \"%s\" 访问异常，错误码： %s，错误信息：%s", key, cause.getErrorCode(), cause.getMessage()),
                    cause);
        }
    }

    /**
     * id card 朝向
     */
    public enum IDCardSide {
        /**
         * 正面
         */
        FRONT,
        /**
         * 反面
         */
        BACK
    }
}
