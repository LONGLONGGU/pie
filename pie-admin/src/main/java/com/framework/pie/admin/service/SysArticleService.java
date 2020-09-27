package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.pie.core.service.CurdService;

import java.util.List;

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

