package com.framework.pie.admin.service;


import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;

import java.util.Map;


public interface SysCodeService {

    PageResult findPage(PageRequest pageRequest);
    int generator( Map<String,Object> record);


}
