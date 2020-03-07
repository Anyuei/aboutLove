package com.r136a1.door.controller;

import com.r136a1.door.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
@Controller
public class BaseController {
    private static String notice ="无新公告";
    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response) {
        model.addAttribute("name", "simonsfan");
        return "/welcomePage";
    }
    @RequestMapping("/welcomePage")
    public String welcome(Model model, HttpServletResponse response) {
        model.addAttribute("notice", notice);
        model.addAttribute("loveDays", DateUtil.getDaysToTime("2020-2-14 0:0:0"));
        return "login";
    }
    @ResponseBody
    @RequestMapping("/editNotice/{notice}")
    public String editNotice(@PathVariable("notice")String notice1) {
        notice = notice1;
        return "ok";
    }
    @RequestMapping("/initSendMessagePage")
    public String initSendMessagePage(Model model, HttpServletResponse response) {
        model.addAttribute("name", "simonsfan");
        return "/welcomePage";
    }
}
