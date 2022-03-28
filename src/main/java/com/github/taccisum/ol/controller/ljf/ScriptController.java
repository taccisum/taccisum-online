package com.github.taccisum.ol.controller.ljf;

import com.github.taccisum.ol.config.ApplicationProperties;
import com.github.taccisum.ol.domain.entity.Script;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
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
    public String executeGhCommit(@RequestParam(required = false, defaultValue = "false") boolean force) {
        if (force) {
            setIdle();
        }

        if (!setBusy()) {
            return "Running";
        }

        try {
            Script script = new Script.FilePath(properties.getScript().getGhCommit());

            script.runAsync(proc -> {
                InputStream is = proc.getInputStream();
                InputStream err = proc.getErrorStream();

                int exitVal = 0;
                try {
                    exitVal = proc.waitFor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                BufferedReader isReader = new BufferedReader(new InputStreamReader(is));
                List<String> stdout = isReader.lines().collect(Collectors.toList());
                log.info("stdout: {}", stdout);

                if (exitVal != 0) {
                    BufferedReader errReader = new BufferedReader(new InputStreamReader(err));
                    List<String> stderr = errReader.lines().collect(Collectors.toList());
                    log.warn("stderr: {}", stderr);
                }

                setIdle();
            });

            return "Start Run...";
        } catch (Script.ScriptRunException e) {
            setIdle();
            return "Run script fail: " + e.getMessage();
        } catch (Exception e) {
            setIdle();
            return "Run script error: " + e.getMessage();
        }
    }

    private static final Object LOCK = new Object();
    private static volatile boolean isRunning = false;

    private static boolean setBusy() {
        synchronized (LOCK) {
            if (isRunning) {
                return false;
            }
            isRunning = true;
            return true;
        }
    }

    private static void setIdle() {
        synchronized (LOCK) {
            isRunning = false;
        }
    }
}
