package com.framework.pie.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.pie.admin.model.SysDept;
import com.framework.pie.http.HttpResult;

import java.util.List;


/**
 * 机构管理
 * @author longlong
 */
public interface SysDeptService extends IService<SysDept> {

	/**
	 * 添加和修改部门信息
	 * @param sysDept
	 * @return
	 */
	HttpResult addOrUpdate(SysDept sysDept);

	/**
	 * 查询机构树
	 * @return
	 */
	List<SysDept> findTree();

	/**
	 * 异步加载树
	 * @param parentId
	 * @return
	 */
	List<SysDept> findTree(String parentId);

	/**
	 * 批量删除部门信息
	 * @param records
	 * @return
	 */
	HttpResult batchDelete(List<SysDept> records);

	/**
	 * 通过名称查询部门信息
	 * @param name
	 * @return
	 */
	SysDept selectByName(String name);

	/**
	 * 查询部门角色信息
	 * @param deptId
	 * @return
	 */
	HttpResult getDeptRoles(String deptId);
}
