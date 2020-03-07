package com.r136a1.door.modules.messageUser.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r136a1.door.entity.Result;
import com.r136a1.door.modules.messageUser.entity.MessageUser;
import com.r136a1.door.modules.messageUser.service.IMessageUserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;


 /**
 * @Description: 消息用户
 * @Author: jeecg-boot
 * @Date:   2020-03-02
 * @Version: V1.0
 */
@RestController
@RequestMapping("/messageUser/messageUser")
@Slf4j
public class Messages1UserController {
	@Autowired
	private IMessageUserService messageUserService;
	

	
	/**
	 *   添加
	 *
	 * @param messageUser
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MessageUser messageUser) {
		messageUserService.save(messageUser);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param messageUser
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MessageUser messageUser) {
		messageUserService.updateById(messageUser);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		messageUserService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.messageUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MessageUser messageUser = messageUserService.getById(id);
		if(messageUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(messageUser);
	}


}
