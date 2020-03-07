package com.r136a1.door.modules.message.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r136a1.door.entity.Result;
import com.r136a1.door.modules.message.entity.Message;

import com.r136a1.door.modules.message.service.IMessageService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

 /**
 * @Description: 消息记录表
 * @Author: jeecg-boot
 * @Date:   2020-03-02
 * @Version: V1.0
 */
@RestController
@RequestMapping("/message/message")
@Slf4j
public class Messages1Controller {
	@Autowired
	private IMessageService messageService;
	/**
	 *   添加
	 *
	 * @param message
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Message message) {
		messageService.save(message);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param message
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Message message) {
		messageService.updateById(message);
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
		messageService.removeById(id);
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
		this.messageService.removeByIds(Arrays.asList(ids.split(",")));
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
		Message message = messageService.getById(id);
		if(message==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(message);
	}


}
