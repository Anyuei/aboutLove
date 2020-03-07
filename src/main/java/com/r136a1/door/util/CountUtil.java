package com.r136a1.door.util;

import com.r136a1.door.modules.messageUser.entity.MessageUser;

public class CountUtil {
    /**
     * 添加用户登录次数
     * @param user
     * @return
     */
    public static MessageUser addUserLoginTimes(MessageUser user){
        Integer loginTimes = user.getLoginTimes();
        loginTimes++;
        user.setLoginTimes(loginTimes);
        return user;
    }
}
