package com.r136a1.door.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r136a1.door.entity.Constant;
import com.r136a1.door.entity.Result;
import com.r136a1.door.modules.message.entity.Message;
import com.r136a1.door.modules.message.service.IMessageService;
import com.r136a1.door.modules.messageUser.entity.MessageUser;
import com.r136a1.door.modules.messageUser.service.IMessageUserService;
import com.r136a1.door.modules.operateLog.entity.OperateLog;
import com.r136a1.door.modules.operateLog.service.IOperateLogService;
import com.r136a1.door.util.CountUtil;
import com.r136a1.door.util.InitObjectUtil;
import com.r136a1.door.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMessageUserService iMessageUserService;
    @Autowired
    private IOperateLogService iOperateLogService;
    @ResponseBody
    @RequestMapping("/login")
    public Result<?> login(HttpServletRequest request,HttpServletResponse response){
        HashMap<String , String> map = new HashMap<>();
        String userName = request.getParameter("userName");
        String key = request.getParameter("key");
        QueryWrapper<MessageUser> userWrapper = new QueryWrapper<>();
        userWrapper.eq("user_name",userName);
        MessageUser one = iMessageUserService.getOne(userWrapper);
        if (null==one){
            map.put("status","1");
            map.put("notice","佩奇没有找到[ "+userName+" ]这个用户哦");
            return Result.ok(map);
        }
        logger.info("用户登录: "+userName+":"+key);
        userWrapper.eq("user_password",key);
        MessageUser one2 = iMessageUserService.getOne(userWrapper);

        if (null==one2){
            map.put("status","2");
            map.put("notice","秘钥错误,请重新输入");
            return Result.ok(map);
        }else{
            String user_id = one2.getId();
            //记录日志
            OperateLog operateLog = InitObjectUtil.initOperateLog(Constant.Login_Type);
            operateLog.setUserId(user_id);
            iOperateLogService.save(operateLog);
            //增加登录次数
            MessageUser messageUser = CountUtil.addUserLoginTimes(one2);
            iMessageUserService.updateById(messageUser);
            //生成token
            String token = JwtUtil.sign(user_id, one2.getUserName());
            request.getSession().setAttribute("token",token);
            map.put("status","0");
            map.put("token",token);
            return Result.ok(map);
        }
    }

    @RequestMapping("/getMessagePage")
    public String getMessagePage(Model model,HttpServletRequest request,
                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                 @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize) {
        String token = (String) request.getSession().getAttribute("token");
        if (null==token){
            return "login";
        }
        String user_id = JwtUtil.getUsername(token);
        //查看留言记录日志
        OperateLog operateLog = InitObjectUtil.initOperateLog(Constant.View_Type);
        operateLog.setUserId(user_id);
        iOperateLogService.save(operateLog);
        //增加登录次数
        MessageUser user = iMessageUserService.getById(user_id);//查询用户
        user= CountUtil.addUserLoginTimes(user);

        QueryWrapper<Message> messageQueryWrapper= new QueryWrapper<>();
        messageQueryWrapper.orderByDesc("create_time");
        messageQueryWrapper.eq("sys_org_code",user.getSysOrgCode());//获取用户的信箱
        final Page<Message> objectPage = new Page<>(pageNo,pageSize);
        IPage<Message> messages = messageService.page(objectPage, messageQueryWrapper);//根据信箱获取信件
        List<Message> records = messages.getRecords();
        LinkedList<Message> LinkedRecords = new LinkedList(records);
        records.removeIf(e ->new Date().getTime()<e.getSendTime().getTime());
        model.addAttribute("user",user);
        MessageUser user1 = iMessageUserService.getById(user_id);//查询用户
        user1.setUpdateTime(new Date());
        iMessageUserService.updateById(user1);
        model.addAttribute("messages",LinkedRecords);
        return "sendMessage";
    }
    @ResponseBody
    @RequestMapping("/getMessage")
    public List<Message> getMessagePage(HttpServletRequest request,
                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String token = (String) request.getSession().getAttribute("token");
        if (null==token){
            return null;
        }
        String user_id = JwtUtil.getUsername(token);
        //查看留言记录日志
        OperateLog operateLog = InitObjectUtil.initOperateLog(Constant.ViewOld_Type);
        operateLog.setUserId(user_id);
        iOperateLogService.save(operateLog);
        MessageUser user = iMessageUserService.getById(user_id);//查询用户
        QueryWrapper<Message> messageQueryWrapper= new QueryWrapper<>();
        messageQueryWrapper.orderByDesc("create_time");
        messageQueryWrapper.eq("sys_org_code",user.getSysOrgCode());//获取用户的信箱
        final Page<Message> objectPage = new Page<>(pageNo,pageSize);
        IPage<Message> messages = messageService.page(objectPage, messageQueryWrapper);//根据信箱获取信件
        List<Message> records = messages.getRecords();
        return records;
    }
    @ResponseBody
    @RequestMapping("/edit")
    public Result<?> edit(HttpServletRequest request,MessageUser user){
        String token = (String) request.getSession().getAttribute("token");
        if (null==token){
            HashMap<String, String> map = new HashMap<>();
            map.put("status","3");
            map.put("notice","会话已过期请重新登录");
            return Result.ok(map);
        }
        String user_id = JwtUtil.getUsername(token);
        user.setId(user_id);
        boolean success = iMessageUserService.updateById(user);
        if (success){
            HashMap<String, String> map = new HashMap<>();
            map.put("status","0");
            map.put("notice","修改成功");
            return Result.ok(map);
        }else{
            HashMap<String, String> map = new HashMap<>();
            map.put("status","5");
            map.put("notice","修改失败");
            return Result.ok(map);
        }
    }
}
