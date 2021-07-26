package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysDept;
import com.framework.pie.core.http.HttpResult;
import com.framework.pie.core.service.CurdService;

import java.util.List;


/**
 * 机构管理
 * @author longlong
 */
public interface SysDeptService extends CurdService<SysDept> {

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
	List<SysDept> findTree(Long parentId);

	 HttpResult remove(List<SysDept> records);
}
