package com.framework.pie.admin.service;

import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;

import java.util.Map;


public interface SysCodeService {

    PageResult findPage(PageRequest pageRequest);
    int generator( Map<String,Object> record);


}
