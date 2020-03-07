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
import com.r136a1.door.util.InitObjectUtil;
import com.r136a1.door.util.JwtUtil;
import com.r136a1.door.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/message")
public class MessagesController {
    @Autowired
    private IMessageUserService iMessageUserService;
    @Autowired
    private IOperateLogService iOperateLogService;
    @Autowired
    private IMessageService iMessageService;

    @RequestMapping("/send")
    public Result<?> sendMessage(HttpServletRequest request,Message realmessage){
        String token = (String) request.getSession().getAttribute("token");
        if (null==token){
            HashMap<String, String> map = new HashMap<>();
            map.put("status","3");
            map.put("notice","会话已过期请重新登录");
            return Result.ok(map);
        }
        String userID = JwtUtil.getUsername(token);
        //查找发信人消息
/*        String receiveUserId = realmessage.getReceiveUserId();
        MessageUser user = iMessageUserService.getById(receiveUserId);
        if (null==user){
            HashMap<String, String> map = new HashMap<>();
            map.put("status","4");
            map.put("notice","投递用户不存在");
            return Result.ok(map);
        }*/
        MessageUser user = iMessageUserService.getById(userID);
        String sysOrgCode = user.getSysOrgCode();
        String userName = user.getUserName();
        realmessage.setCreateBy(userID);
        realmessage.setSendUserId(userID);
        realmessage.setUpdateBy(userID);
        realmessage.setSendUserName(userName);
        realmessage.setSysOrgCode(sysOrgCode);
        InitObjectUtil.initMessage(realmessage);
        boolean save = iMessageService.save(realmessage);
        if (save){
            //记录日志
            OperateLog operateLog = InitObjectUtil.initOperateLog(Constant.View_Type);
            operateLog.setUserId(userID);
            iOperateLogService.save(operateLog);
            return Result.ok("佩奇已将信件送达");
        }else {
            return Result.error("找不到佩奇了！请联系管理员！");
        }
    }
    @RequestMapping("/getOneMessage")
    public String getMessagePage(HttpServletRequest request,
                                 @RequestParam("id") String id) {
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
        return "sendMessage";
    }
}
