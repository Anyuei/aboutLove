package com.r136a1.door.modules.messageUser.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * @Description: 消息用户
 * @Author: jeecg-boot
 * @Date:   2020-03-02
 * @Version: V1.0
 */
@Data
@TableName("message_user")
public class MessageUser implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
    private String id;
	/**用户头像*/

    private String userAvator;
	/**用户名*/

    private String userName;
	/**密码*/

    private String userPassword;
	/**登录次数*/

    private Integer loginTimes;
	/**用户等级*/

    private Integer level;
	/**年龄*/

    private String age;
	/**年龄*/

	/**性别*/

    private Integer sex;
	/**位置*/

    private String location;
	/**位置*/
	/**背景图片*/

    private String backgroundImg;
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
}
