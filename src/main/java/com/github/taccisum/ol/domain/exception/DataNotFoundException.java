package com.github.taccisum.ol.domain.exception;

/**
 * 数据不存在异常
 *
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/30
 */
public class DataNotFoundException extends DomainException {
    public DataNotFoundException(String key, Object id) {
        super("%s[%s] 不存在", key, id);
    }
}
