package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysLog;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.mybatis.service.CurdService;

public interface SysLogService extends CurdService<SysLog> {

    PageResult findPage(PageRequest pageRequest);
}
