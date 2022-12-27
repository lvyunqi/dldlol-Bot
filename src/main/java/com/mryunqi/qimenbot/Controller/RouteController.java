package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.mryunqi.qimenbot.Model.Yiyan.YiyanGet;

@Controller
public class RouteController {
    @RequestMapping("login")
    public String login(HttpServletRequest request,Model model){
        String loginName = (String) request.getSession().getAttribute("loginName");
        if (loginName != null){
            return "index";
        }
        JSONObject Yiyan = YiyanGet("https://v1.hitokoto.cn");
        model.addAttribute("text", Yiyan.getString("hitokoto")+"-"+(Yiyan.getString("from_who")== null ? "":Yiyan.getString("from_who"))+"["+Yiyan.getString("from")+"]");
        return "login";
    }
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("title", "控制台");
        model.addAttribute("page","index");
        model.addAttribute("page_tab","index");
        return "index";
    }
    @RequestMapping("/index")
    public String home(Model model){
        model.addAttribute("title", "控制台");
        model.addAttribute("page","index");
        model.addAttribute("page_tab","index");
        return "index";
    }
    @RequestMapping("/player")
    public String player(Model model){
        model.addAttribute("title", "玩家列表");
        model.addAttribute("page","player");
        model.addAttribute("page_tab","player");
        return "player";
    }
    @RequestMapping("/command")
    public String command(Model model){
        model.addAttribute("title", "全局配置");
        model.addAttribute("page","command");
        model.addAttribute("page_tab","command");
        return "command";
    }
    @RequestMapping("/hunshou")
    public String hunshou(Model model){
        model.addAttribute("title", "魂兽设置 - 数据配置");
        model.addAttribute("page","data");
        model.addAttribute("page_tab","hunshou");
        return "hunshou";
    }
    @RequestMapping("/addhunshou")
    public String addHunshou(Model model){
        model.addAttribute("title", "魂兽设置 - 添加魂兽");
        model.addAttribute("page","data");
        model.addAttribute("page_tab","hunshou");
        return "addhunshou";
    }
    @RequestMapping("/loginout")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
    @RequestMapping("/Edit")
    public String Edit(HttpServletRequest request) {
        return "lyear_pages_edit_pwd";
    }
    @RequestMapping("/adduser")
    public String adduser(HttpServletRequest request) {
        return "adduser";
    }
}
