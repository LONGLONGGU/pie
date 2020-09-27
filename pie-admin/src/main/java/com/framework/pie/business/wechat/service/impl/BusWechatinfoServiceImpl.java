package com.framework.pie.business.wechat.service.impl;

import com.framework.pie.business.wechat.model.BusWechatinfo;
import com.framework.pie.business.wechat.dao.BusWechatinfoMapper;
import com.framework.pie.business.wechat.service.BusWechatinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.framework.pie.core.page.MybatisPageHelper;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 公众号信息表  服务实现类
 * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BusWechatinfoServiceImpl implements BusWechatinfoService {
    @Resource
    private BusWechatinfoMapper busWechatinfoMapper;

    @Override
    public int save(BusWechatinfo record) {
      if (record.getId() == null || record.getId() == 0){
        return busWechatinfoMapper.insertSelective(record);
       }
       return busWechatinfoMapper.updateByPrimaryKeySelective(record);
    }
   @Override
    public int delete(BusWechatinfo record) {
       return busWechatinfoMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<BusWechatinfo> records) {
        for (BusWechatinfo record : records){
            delete(record);
        }
        return 1;
    }

    @Override
    public BusWechatinfo findById(Long id) {
        return busWechatinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        return MybatisPageHelper.findPage(pageRequest,busWechatinfoMapper);
    }

}
