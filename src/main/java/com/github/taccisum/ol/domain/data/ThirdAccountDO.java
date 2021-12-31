package com.github.taccisum.ol.domain.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.taccisum.ol.domain.entity.core.ServiceProvider;
import lombok.Data;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/31
 */
@Data
@TableName("third_account")
public class ThirdAccountDO {
    private Long id;
    /**
     * 服务商类型
     */
    private ServiceProvider.Type spType;
    /**
     * 用户名
     */
    private String username;
    /**
     * 应用 ID
     */
    private String appId;
    /**
     * 应用 Secret
     */
    private String appSecret;
}
