package com.github.taccisum.ol.controller.lwf.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author taccisum - liaojinfeng6938@dingtalk.com
 * @since 2022/2/7
 */
@Controller
@RequestMapping("lwf/id-cards")
public class IdCardViewController {
    @RequestMapping
    public String index(Model model) {
        model.addAttribute("name", "Guest");
        return "lwf/id-cards/index.html";
    }
}
