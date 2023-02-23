package com.framework.pie.admin.dictionary.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.dictionary.dao.SysDictionaryDataMapper;
import com.framework.pie.admin.dictionary.model.SysDictionaryData;
import com.framework.pie.admin.dictionary.service.SysDictionaryDataService;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houjh
 * @since 2021-09-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDictionaryDataServiceImpl extends ServiceImpl<SysDictionaryDataMapper, SysDictionaryData> implements SysDictionaryDataService {

    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    @Override
    public HttpResult addOrUpdate(SysDictionaryData record) {
        if (StrUtil.isEmpty(record.getId())){
            record.setCreateBy(JwtUtils.getUserId());
            sysDictionaryDataMapper.insert(record);
            return HttpResult.ok("添加成功");
        }
        record.setLastUpdateBy(JwtUtils.getUserId());
        sysDictionaryDataMapper.updateById(record);
        return HttpResult.ok("更新成功");
    }


    @Override
    public List<SysDictionaryData> findDicByCode(String code) {
        Map<String,Object> params = new HashMap<>();
        params.put("code", code);
        return sysDictionaryDataMapper.findByCode(params);
    }

    @Override
    @Transactional
    public HttpResult batchDeleteData(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        //sysDictionaryDataMapper.deleteBatchIds(idList);
        idList.forEach(id->this.delete(id));
        return HttpResult.ok("批量删除成功!");
    }

    @Override
    public HttpResult delete(String id) {
        UpdateWrapper<SysDictionaryData> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(SysDictionaryData::getId,id)
                .set(SysDictionaryData::getDelFlag, GlobalConstants.DEL_FLAG_DELETE)
                .set(SysDictionaryData::getLastUpdateBy,JwtUtils.getUserId());
        sysDictionaryDataMapper.update(new SysDictionaryData(),updateWrapper);
        return HttpResult.ok("删除成功!");
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest,sysDictionaryDataMapper,"findPage",pageRequest.getParams());
    }

}
