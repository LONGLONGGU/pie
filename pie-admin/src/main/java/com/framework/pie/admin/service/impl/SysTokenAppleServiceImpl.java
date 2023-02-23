package com.framework.pie.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.framework.pie.admin.util.DateUtils;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.admin.model.SysTokenApple;
import com.framework.pie.admin.dao.SysTokenAppleMapper;
import com.framework.pie.admin.service.SysTokenAppleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.utils.DateTimeUtils;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* <p>
* token申请表 服务实现类
* </p>
*
* @author longlong
* @since 2022-08-18
*/
@Service
public class SysTokenAppleServiceImpl extends ServiceImpl<SysTokenAppleMapper, SysTokenApple> implements SysTokenAppleService {

    @Autowired
    private SysTokenAppleMapper sysTokenAppleMapper;

    @Override
    public HttpResult addOrUpdate(SysTokenApple sysTokenApple) {
        if (StrUtil.isEmpty(sysTokenApple.getId())){
            sysTokenApple.setCreateBy(JwtUtils.getUserId());
            sysTokenAppleMapper.insert(sysTokenApple);
            return HttpResult.ok("添加成功");
        }
        sysTokenApple.setLastUpdateBy(JwtUtils.getUserId());
        sysTokenAppleMapper.updateById(sysTokenApple);
        return HttpResult.ok("更新成功");
    }

    @Override
    public HttpResult delete(String id) {
        UpdateWrapper<SysTokenApple> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(SysTokenApple::getId,id)
        .set(SysTokenApple::getDelFlag, GlobalConstants.DEL_FLAG_DELETE)
        .set(SysTokenApple::getLastUpdateBy,JwtUtils.getUserId());
        sysTokenAppleMapper.update(new SysTokenApple(),updateWrapper);
        return HttpResult.ok("删除成功!");
    }

    @Override
    @Transactional
    public HttpResult batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        idList.forEach(id->this.delete(id));
        return HttpResult.ok("批量删除成功!");
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest,sysTokenAppleMapper,"findPage",pageRequest.getParams());
    }

    @Override
    public HttpResult tokenApply(Map<String, String> parms) {
        String id = parms.get("id");
        String accessToken = parms.get("accessToken");
        SysTokenApple sysTokenApple = this.getById(id);
        sysTokenApple.setToken(accessToken);
        sysTokenApple.setApplyTime(new Date());
        Date overdueTime = DateTimeUtils.getDateByDay(new Date(),180);
        sysTokenApple.setApplyTime(new Date());
        sysTokenApple.setOverdueTime(overdueTime);
        this.updateById(sysTokenApple);
        return HttpResult.ok(sysTokenApple);
    }
}
