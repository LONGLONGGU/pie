package com.framework.pie.admin.controller;

import com.framework.pie.admin.model.SysDept;
import com.framework.pie.admin.service.SysDeptService;
import com.framework.pie.admin.util.syslog.Log;
import com.framework.pie.http.HttpResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
	@ApiOperation("新增修改部门")
	@PostMapping(value="/save")
	public HttpResult save(@RequestBody SysDept record) {
		return sysDeptService.addOrUpdate(record);
	}

	@Log(value = "删除部门")
	@ApiOperation("删除部门信息")
	@PostMapping(value="/delete")
	public HttpResult delete(@RequestBody List<SysDept> records) {
		return sysDeptService.batchDelete(records);
	}

	@GetMapping(value="/findTree")
	public HttpResult findTree() {
		return HttpResult.ok(sysDeptService.findTree());
	}

	@ApiOperation("异步加载部门tree")
	@GetMapping(value="/findTree/{parentId}")
	public HttpResult asyncFindTree(@PathVariable("parentId") String parentId) {
		return HttpResult.ok(sysDeptService.findTree(parentId));
	}

	@ApiOperation("查询部门角色信息")
	@GetMapping(value="/getDeptRoles/{deptId}")
	public HttpResult getDeptRoles(@PathVariable("deptId") String deptId){
		return sysDeptService.getDeptRoles(deptId);
	}
}
