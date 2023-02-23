package com.framework.pie.admin.dictionary.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.dictionary.dao.SysDictionaryDataMapper;
import com.framework.pie.admin.dictionary.dao.SysDictionaryMapper;
import com.framework.pie.admin.dictionary.model.SysDictionary;
import com.framework.pie.admin.dictionary.model.SysDictionaryData;
import com.framework.pie.admin.dictionary.service.SysDictionaryService;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.http.HttpResult;
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


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
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements SysDictionaryService {

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;
    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    @Override
    public HttpResult addOrUpdate(SysDictionary record) {
        //处理字典pathInfo信息
        String pathInfo = record.getCode();
        if(StrUtil.isNotEmpty(record.getParentId())){
            SysDictionary parentDic = sysDictionaryMapper.selectById(record.getParentId());
            pathInfo = parentDic.getPathInfo()+"_"+pathInfo;
        }
        record.setPathInfo(pathInfo);
        if (StrUtil.isEmpty(record.getId())){
            record.setCreateBy(JwtUtils.getUserId());
            sysDictionaryMapper.insert(record);
            return HttpResult.ok("添加成功");
        }
        record.setLastUpdateBy(JwtUtils.getUserId());
        sysDictionaryMapper.updateById(record);
        return HttpResult.ok("修改成功");
    }

    @Override
    public HttpResult delete(String id) {
        // 验证是否存在子级
        SysDictionary sysDictionary = sysDictionaryMapper.selectById(id);
        // 注意此处查询只验证子类，需要加下划线查询
        String pathInfo = sysDictionary.getPathInfo()+"_";
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysDictionary::getId).like(SysDictionary::getPathInfo,pathInfo);
        List<SysDictionary> sysDictionaryList = sysDictionaryMapper.selectList(queryWrapper);
        if(sysDictionaryList != null && sysDictionaryList.size() > 0){
            return HttpResult.error("该字典下还存在子级字典，请先删除子级后再删除该字典!");
        }

        // 验证字典下是否还存在字典值
        QueryWrapper<SysDictionaryData> dataQueryWrapper = new QueryWrapper<>();
        dataQueryWrapper.lambda().select(SysDictionaryData::getId).eq(SysDictionaryData::getDictionaryId,id);
        List<SysDictionaryData> sysDictionaryDataList = sysDictionaryDataMapper.selectList(dataQueryWrapper);
        if(sysDictionaryDataList != null && sysDictionaryDataList.size() > 0){
            return HttpResult.error("该字典下还存在数据字典值，请先删除数据字典值之后再删除!");
        }

        UpdateWrapper<SysDictionary> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(SysDictionary::getId,id)
                .set(SysDictionary::getDelFlag,GlobalConstants.DEL_FLAG_DELETE)
                .set(SysDictionary::getLastUpdateBy,JwtUtils.getUserId());

        sysDictionaryMapper.update(new SysDictionary(),updateWrapper);
        return HttpResult.ok("删除成功!");
    }

    @Override
    public List<SysDictionary> asyncFindTree(String parentId) {
        List<SysDictionary> sysDictionaryList = new ArrayList<>();
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isEmpty(parentId) || "0".equals(parentId)){
            queryWrapper.lambda().isNull(SysDictionary::getParentId);
        } else {
            queryWrapper.lambda().eq(SysDictionary::getParentId,parentId);
        }
        queryWrapper.lambda().orderByAsc(SysDictionary::getOrderNum);
        sysDictionaryList = sysDictionaryMapper.selectList(queryWrapper);
        sysDictionaryList.forEach(dic -> {
            QueryWrapper<SysDictionary> childQueryWrapper = new QueryWrapper<>();
            childQueryWrapper.lambda().select(SysDictionary::getId).eq(SysDictionary::getParentId,dic.getId());
            List<SysDictionary> childList = sysDictionaryMapper.selectList(childQueryWrapper);
            if(childList != null && childList.size() > 0){
                dic.setHasChildren(false);
            }
        });
        return sysDictionaryList;
    }

    @Override
    public HttpResult validateCode(String id, String code) {
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysDictionary::getId).eq(SysDictionary::getCode,code);
        if(StrUtil.isNotEmpty(id)){
            queryWrapper.lambda().ne(SysDictionary::getId,id);
        }
        List<SysDictionary> sysDictionaryList = sysDictionaryMapper.selectList(queryWrapper);
        if(sysDictionaryList != null && sysDictionaryList.size() > 0){
            return HttpResult.error("数据字典code已经存在,请重新填写");
        } else {
            return HttpResult.ok("验证通过");
        }
    }
}
