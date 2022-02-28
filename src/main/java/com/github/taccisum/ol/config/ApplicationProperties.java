package com.github.taccisum.ol.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/24
 */
@Data
@Component
@ConfigurationProperties("app")
public class ApplicationProperties {
    private String version;
    private Script script = new Script();

    @Data
    public static class Script {
        private String ghCommit = "/root/script/gh_commits/1.sh";
    }
}
