package com.framework.pie.file.upload.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.file.upload.dao.SysAttachmentsMapper;
import com.framework.pie.file.upload.model.SysAttachments;
import com.framework.pie.file.upload.service.SysAttachmentsService;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class SysAttachmentsServiceImpl extends ServiceImpl<SysAttachmentsMapper, SysAttachments> implements SysAttachmentsService {

    @Autowired
    private SysAttachmentsMapper sysAttachmentsMapper;

    @Override
    public HttpResult addOrUpdate(SysAttachments record) {
        if(StrUtil.isEmpty(record.getId())){
            if(StrUtil.isEmpty(record.getCreateBy())){
                record.setCreateBy(JwtUtils.getUserId());
            }
            sysAttachmentsMapper.insert(record);
            return HttpResult.ok("附件添加成功!");
        }
        if(StrUtil.isEmpty(record.getLastUpdateBy())){
            record.setLastUpdateBy(JwtUtils.getUserId());
        }
        sysAttachmentsMapper.updateById(record);
        return HttpResult.ok("修改成功!");
    }

    @Override
    public HttpResult delete(String id,String userId) {
        // 删除附件信息，此处暂时先不做附件删除
//        SysAttachments attachments = sysAttachmentsMapper.selectById(id);
//        File file = new File(attachments.getFilePath());
//        if(file.exists()) {
//            file.delete();
//        }

        //更新记录信息
        UpdateWrapper<SysAttachments> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(SysAttachments::getId,id)
                .set(SysAttachments::getDelFlag, GlobalConstants.DEL_FLAG_DELETE);
        if(StrUtil.isNotEmpty(userId)){
            updateWrapper.lambda().set(SysAttachments::getLastUpdateBy,userId);
        } else {
            updateWrapper.lambda().set(SysAttachments::getLastUpdateBy,JwtUtils.getUserId());
        }
        sysAttachmentsMapper.update(new SysAttachments(),updateWrapper);
        return HttpResult.ok("删除成功");
    }

    @Override
    @Transactional
    public HttpResult batchDelete(List<SysAttachments> records) {
        for (SysAttachments record : records){
            this.delete(record.getId(),record.getLastUpdateBy());
        }
        return HttpResult.ok("删除成功");
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest,sysAttachmentsMapper);
    }

    @Override
    public HttpResult listSysAttachment(String ids) {
        List<SysAttachments> sysAttachmentsList = sysAttachmentsMapper.selectBatchIds(Arrays.asList(ids.split(",")));
        if(sysAttachmentsList != null && sysAttachmentsList.size() > 0){
            return HttpResult.ok(sysAttachmentsList);
        }
        return HttpResult.error("未查询到数据信息");
    }
}
