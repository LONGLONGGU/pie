package com.framework.pie.admin.controller;

import com.framework.pie.admin.model.SysDept;
import com.framework.pie.admin.service.SysDeptService;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.http.HttpResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构管理
 * @author longlong
 */
@RestController
@RequestMapping("dept")
public class SysDeptController {

	@Autowired
	private SysDeptService sysDeptService;

	@Log(value = "新增修改部门")
	@PreAuthorize("hasAuthority('sys:dept:add') AND hasAuthority('sys:dept:edit')")
	@PostMapping(value="/save")
	public HttpResult save(@RequestBody SysDept record) {
		return HttpResult.ok(sysDeptService.save(record));
	}

	@Log(value = "删除部门")
	@PreAuthorize("hasAuthority('sys:dept:delete')")
	@PostMapping(value="/delete")
	public HttpResult delete(@RequestBody List<SysDept> records) {

		return sysDeptService.remove(records);
	}

	@PreAuthorize("hasAuthority('sys:dept:view')")
	@GetMapping(value="/findTree")
	public HttpResult findTree() {
		return HttpResult.ok(sysDeptService.findTree());
	}

	@ApiOperation("异步加载部门tree")
	@GetMapping(value="/findTree/{parentId}")
	public HttpResult asyncFindTree(@PathVariable("parentId") Long parentId) {

		return HttpResult.ok(sysDeptService.findTree(parentId));
	}


}
