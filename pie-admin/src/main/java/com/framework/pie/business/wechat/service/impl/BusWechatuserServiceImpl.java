package com.framework.pie.business.wechat.service.impl;

import com.framework.pie.business.wechat.model.BusWechatuser;
import com.framework.pie.business.wechat.dao.BusWechatuserMapper;
import com.framework.pie.business.wechat.service.BusWechatuserService;
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
 * 微信用户  服务实现类
 * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BusWechatuserServiceImpl implements BusWechatuserService {
    @Resource
    private BusWechatuserMapper busWechatuserMapper;

    @Override
    public int save(BusWechatuser record) {
      if (record.getId() == null || record.getId() == 0){
        return busWechatuserMapper.insertSelective(record);
       }
       return busWechatuserMapper.updateByPrimaryKeySelective(record);
    }
   @Override
    public int delete(BusWechatuser record) {
       return busWechatuserMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<BusWechatuser> records) {
        for (BusWechatuser record : records){
            delete(record);
        }
        return 1;
    }

    @Override
    public BusWechatuser findById(Long id) {
        return busWechatuserMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        return MybatisPageHelper.findPage(pageRequest,busWechatuserMapper);
    }

}
