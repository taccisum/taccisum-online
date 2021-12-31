package com.github.taccisum.ol.domain.data;

import lombok.Data;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/31
 */
@Data
public class AliCloudAccountDO {
    private Long id;
    private String username;
    private String accessKeyId;
    private String accessKeySecret;
}
