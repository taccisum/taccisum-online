package com.github.taccisum.ol.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/24
 */
@RestController
@RequestMapping("versions")
public class VersionController {
    @Value("${app.version}")
    private String version;

    @GetMapping
    public String current() {
        return version;
    }
}
