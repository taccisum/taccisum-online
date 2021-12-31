package com.github.taccisum.ol.domain.data;

import lombok.Data;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/31
 */
@Data
public class MessageDO {
    private Long id;
    /**
     * 发送人
     */
    private String sender;
    /**
     * 推送目标
     */
    private String target;
    /**
     * 标题
     */
    private String title;
    /**
     * 正文
     */
    private String content;
    /**
     * 标签
     */
    private String tag;
}
