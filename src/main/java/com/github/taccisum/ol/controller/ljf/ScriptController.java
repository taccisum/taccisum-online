package com.github.taccisum.ol.controller.ljf;

import com.github.taccisum.ol.config.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/2/28
 */
@Slf4j
@RestController
@RequestMapping("apis/ljf/scripts")
public class ScriptController {
    @Resource
    private ApplicationProperties properties;

    @PostMapping("gh_commit")
    public void executeGhCommit() {
        try {
            Process process = Runtime.getRuntime().exec(properties.getScript().getGhCommit());

            CompletableFuture.runAsync(() -> {
                try {
                    InputStream is = process.getInputStream();
                    InputStream err = process.getErrorStream();

                    int exitVal = process.waitFor();

                    BufferedReader isReader = new BufferedReader(new InputStreamReader(is));
                    List<String> stdout = isReader.lines().collect(Collectors.toList());
                    log.info("stdout: {}", stdout);

                    if (exitVal != 0) {
                        BufferedReader errReader = new BufferedReader(new InputStreamReader(err));
                        List<String> stderr = errReader.lines().collect(Collectors.toList());
                        log.warn("stderr: {}", stderr);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
