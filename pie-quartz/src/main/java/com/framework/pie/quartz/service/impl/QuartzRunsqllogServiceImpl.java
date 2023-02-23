package com.framework.pie.quartz.service.impl;

import com.framework.pie.quartz.model.QuartzRunsqllog;
import com.framework.pie.quartz.dao.QuartzRunsqllogMapper;
import com.framework.pie.quartz.service.QuartzRunsqllogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houjh
 * @since 2021-09-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuartzRunsqllogServiceImpl extends ServiceImpl<QuartzRunsqllogMapper, QuartzRunsqllog> implements QuartzRunsqllogService {

    @Resource
    private QuartzRunsqllogMapper quartzRunsqllogMapper;

    @Override
    public int saveByNativeSql(QuartzRunsqllog record) {
      if (StringUtils.isEmpty(record.getId())){
        return quartzRunsqllogMapper.insert(record);
       }
       return quartzRunsqllogMapper.updateById(record);
    }

    @Override
    public int delete(QuartzRunsqllog record) {
       return quartzRunsqllogMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<QuartzRunsqllog> records) {
        for (QuartzRunsqllog record : records){
            delete(record);
        }
        return 1;
    }

    @Override
    public QuartzRunsqllog findById(String id) {
        return quartzRunsqllogMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        return MybatisPageHelper.findPage(pageRequest,quartzRunsqllogMapper);
    }

}
