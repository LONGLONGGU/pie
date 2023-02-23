package com.framework.pie.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.dao.SysArticleMapper;
import com.framework.pie.admin.model.SysArticle;
import com.framework.pie.admin.service.SysArticleService;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 文章  服务实现类
 * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysArticleServiceImpl extends ServiceImpl<SysArticleMapper,SysArticle> implements SysArticleService {
    @Resource
    private SysArticleMapper sysArticleMapper;

    @Override
    public int saveByNativeSql(SysArticle record) {
      if (record.getId() == null){
        return sysArticleMapper.insertSelective(record);
       }
       return sysArticleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysArticle record) {
       return sysArticleMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysArticle> records) {
        for (SysArticle record : records){
            delete(record);
        }
        return 1;
    }

    @Override
    public SysArticle findById(String id) {
        return sysArticleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        return MybatisPageHelper.findPage(pageRequest,sysArticleMapper);
    }

    @Override
    public SysArticle saveArticle(SysArticle record) {
        if (record.getId() == null){
            sysArticleMapper.insertSelective(record);
            return record;
        }
        sysArticleMapper.updateByPrimaryKeySelective(record);
        return record;
    }
}
