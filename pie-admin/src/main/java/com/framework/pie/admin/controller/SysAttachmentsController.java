package com.framework.pie.admin.controller;

import com.framework.pie.admin.model.SysAttachments;
import com.framework.pie.admin.service.SysAttachmentsService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return HttpResult.ok(sysAttachmentsService.delete(records));
    }


}
