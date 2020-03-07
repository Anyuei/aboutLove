package com.r136a1.door.modules.operateLog.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r136a1.door.entity.Result;
import com.r136a1.door.modules.operateLog.entity.OperateLog;
import com.r136a1.door.modules.operateLog.service.IOperateLogService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

 /**
 * @Description: 操作日志
 * @Author: jeecg-boot
 * @Date:   2020-03-02
 * @Version: V1.0
 */
@RestController
@RequestMapping("/operateLog/operateLog")
@Slf4j
public class OperateLogController{
	@Autowired
	private IOperateLogService operateLogService;

	
	/**
	 *   添加
	 *
	 * @param operateLog
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OperateLog operateLog) {
		operateLogService.save(operateLog);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param operateLog
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OperateLog operateLog) {
		operateLogService.updateById(operateLog);
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
		operateLogService.removeById(id);
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
		this.operateLogService.removeByIds(Arrays.asList(ids.split(",")));
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
		OperateLog operateLog = operateLogService.getById(id);
		if(operateLog==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(operateLog);
	}



}
