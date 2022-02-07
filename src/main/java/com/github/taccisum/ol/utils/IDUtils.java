package com.github.taccisum.ol.utils;

import java.util.UUID;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/2/7
 */
public abstract class IDUtils {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String uuid(int length) {
        return uuid().substring(0, length);
    }
}
