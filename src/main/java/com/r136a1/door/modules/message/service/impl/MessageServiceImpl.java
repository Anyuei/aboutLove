package com.r136a1.door.modules.message.service.impl;

import com.r136a1.door.modules.message.entity.Message;
import com.r136a1.door.modules.message.mapper.MessageMapper;
import com.r136a1.door.modules.message.service.IMessageService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 消息记录表
 * @Author: jeecg-boot
 * @Date:   2020-03-02
 * @Version: V1.0
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
