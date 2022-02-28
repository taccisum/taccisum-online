package com.github.taccisum.ol.controller.ljf;

import com.github.taccisum.ol.config.ApplicationProperties;
import com.github.taccisum.ol.domain.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
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

    @RequestMapping(value = "gh_commit", method = {RequestMethod.GET, RequestMethod.POST})
    public void executeGhCommit() {
        try {
            String scriptPath = properties.getScript().getGhCommit();
            File script = new File(scriptPath);
            if (!script.exists()) {
                throw new DomainException("script %s not exists.", script);
            }

            if (!script.canExecute()) {
                throw new DomainException("script %s can not be execute.", script);
            }

            Process process = Runtime.getRuntime().exec(new String[]{"sh", scriptPath});

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
