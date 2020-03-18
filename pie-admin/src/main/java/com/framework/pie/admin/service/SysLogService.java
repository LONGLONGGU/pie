package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysLog;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import com.framework.pie.core.service.CurdService;

public interface SysLogService extends CurdService<SysLog> {

    PageResult findPage(PageRequest pageRequest);
}
