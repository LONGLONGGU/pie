package com.framework.pie.file.upload.controller;

import com.framework.pie.file.upload.model.SysAttachments;
import com.framework.pie.file.upload.service.SysAttachmentsService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("attachments")
public class SysAttachmentsController {

    @Autowired
    private SysAttachmentsService sysAttachmentsService;


    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysAttachmentsService.findPage(pageRequest));
    }

    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysAttachments> records) {
        return HttpResult.ok(sysAttachmentsService.batchDelete(records));
    }

    /**
     * 查询附件列表信息
     * @param ids
     * @return
     */
    @PostMapping(value = "/listSysAttachment")
    public HttpResult listSysAttachment(String ids) {
        return sysAttachmentsService.listSysAttachment(ids);
    }
}
