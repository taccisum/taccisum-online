package com.github.taccisum.ol.domain.entity;

import com.github.taccisum.ol.domain.exception.DomainException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/3/28
 */
@Slf4j
public abstract class Script {
    public abstract CompletableFuture<Void> runAsync(Consumer<Process> hook);

    public static class FilePath extends Script {
        private String filePath;

        public FilePath(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public CompletableFuture<Void> runAsync(Consumer<Process> hook) {
            File script = new File(this.filePath);
            if (!script.exists()) {
                throw new DomainException("script %s not exists.", script);
            }

            if (!script.canExecute()) {
                throw new DomainException("script %s can not be execute.", script);
            }

            try {
                Process process = Runtime.getRuntime().exec(new String[]{"sh", this.filePath});

                return CompletableFuture.runAsync(() -> {
                    hook.accept(process);
                });
            } catch (IOException e) {
                throw new ScriptRunException("脚本执行异常", e);
            }
        }
    }

    public static class ScriptRunException extends DomainException {
        public ScriptRunException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
