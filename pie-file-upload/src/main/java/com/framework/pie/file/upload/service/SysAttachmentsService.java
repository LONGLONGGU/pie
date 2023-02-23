package com.framework.pie.file.upload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.pie.file.upload.model.SysAttachments;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;

import java.util.List;

public interface SysAttachmentsService extends IService<SysAttachments> {

    HttpResult addOrUpdate(SysAttachments record);

    HttpResult delete(String id,String userId);

    HttpResult batchDelete(List<SysAttachments> records);

    PageResult findPage(PageRequest pageRequest);

    /**
     * 项目附件列表
     * @param ids
     * @return
     */
    HttpResult listSysAttachment(String ids);
}
