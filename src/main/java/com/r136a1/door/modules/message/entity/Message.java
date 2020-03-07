package com.r136a1.door.modules.message.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * @Description: 消息记录表
 * @Author: jeecg-boot
 * @Date:   2020-03-02
 * @Version: V1.0
 */
@Data
@TableName("message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**消息标题*/

    private String messageTitle;
	/**消息内容*/

    private String messageContent;
	/**发送者id*/

    private String sendUserId;
    /**发送者id*/

    private String sendUserName;
	/**接收者id*/
    private String receiveUserId;
	/**1:已读 0:未读*/

    private Integer isRead;
	/**消息状态（0：正常，1：撤回，2：删除）*/

    private Integer status;
	/**创建人*/

    private String createBy;
	/**创建日期*/

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	/**更新人*/

    private String updateBy;
	/**更新日期*/

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
	/**所属部门*/

    private String sysOrgCode;
	/**约定发送时间*/

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date sendTime;
}
