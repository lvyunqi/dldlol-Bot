package com.mryunqi.qimenbot.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    @GetMapping("/admin")
    public String admin() {
        return "index";
    }

    // 登录页面
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 首页
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 玩家管理页面
    @GetMapping("/player")
    public String player(Map<String,Object> maps) {
        List<String> list = new ArrayList<>();
        maps.put("user", list);
        return "player";
    }

}
