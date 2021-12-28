package com.github.taccisum.ol.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2021/12/24
 */
@Api
@RestController
@RequestMapping("versions")
public class VersionController {
    @Value("${app.version}")
    private String version;

    @GetMapping
    @ApiOperation("获取服务版本号")
    public String current() {
        return version;
    }
}
