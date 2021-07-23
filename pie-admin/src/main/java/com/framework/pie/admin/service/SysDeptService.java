package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysDept;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.service.CurdService;

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

	 HttpResult remove(List<SysDept> records);
}
