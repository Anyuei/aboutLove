package com.r136a1.door.util;

import com.r136a1.door.modules.message.entity.Message;
import com.r136a1.door.modules.operateLog.entity.OperateLog;

import java.util.Date;

public class InitObjectUtil {
    public static OperateLog initOperateLog(String type){
        OperateLog operateLog = new OperateLog();
        operateLog.setId(UUIDGenerator.generate());
        operateLog.setCreateTime(new Date());
        operateLog.setOperateType(type);
        return operateLog;
    }
    public static Message initMessage(Message message){
        message.setCreateTime(new Date());
        message.setId(UUIDGenerator.generate());
        message.setIsRead(0);//0:未读 1:已读
        message.setStatus(0);//消息状态（0：正常，1：撤回，2：删除）
        message.setUpdateTime(new Date());
        System.out.println(message.getSendTime());
        if (null==message.getSendTime()||"".equals(message.getSendTime().toString())){
            message.setSendTime(new Date());
        }
        return message;
    }
}
