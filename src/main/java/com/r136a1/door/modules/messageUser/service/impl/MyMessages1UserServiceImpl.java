package com.r136a1.door.modules.messageUser.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r136a1.door.modules.messageUser.entity.MessageUser;
import com.r136a1.door.modules.messageUser.mapper.Messages1UserMapper;
import com.r136a1.door.modules.messageUser.service.IMessageUserService;
import org.springframework.stereotype.Service;


/**
 * @Description: 消息用户
 * @Author: jeecg-boot
 * @Date:   2020-03-02
 * @Version: V1.0
 */
@Service
public class MyMessages1UserServiceImpl extends ServiceImpl<Messages1UserMapper, MessageUser> implements IMessageUserService {

}
