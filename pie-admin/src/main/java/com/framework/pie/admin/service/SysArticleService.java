package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysArticle;
import com.framework.pie.mybatis.service.CurdService;

/**
    * <p>
    * 文章  服务类
    * </p>
*
* @author longlong
* @since 2020-09-25
*/
public interface SysArticleService extends CurdService<SysArticle> {
    SysArticle saveArticle(SysArticle record);

}

