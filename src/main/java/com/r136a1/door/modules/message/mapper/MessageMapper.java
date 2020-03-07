package com.r136a1.door.modules.message.mapper;

import com.r136a1.door.modules.message.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


/**
 * @Description: 消息记录表
 * @Author: jeecg-boot
 * @Date:   2020-03-02
 * @Version: V1.0
 */
@Component
public interface MessageMapper extends BaseMapper<Message> {

}
