package com.framework.pie.business.wechat.service.impl;

import com.framework.pie.business.wechat.model.BusWechatmenu;
import com.framework.pie.business.wechat.dao.BusWechatmenuMapper;
import com.framework.pie.business.wechat.service.BusWechatmenuService;
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
 * 公众号菜单  服务实现类
 * </p>
 *
 * @author longlong
 * @since 2020-09-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BusWechatmenuServiceImpl implements BusWechatmenuService {
    @Resource
    private BusWechatmenuMapper busWechatmenuMapper;

    @Override
    public int save(BusWechatmenu record) {
      if (record.getId() == null || record.getId() == 0){
        return busWechatmenuMapper.insertSelective(record);
       }
       return busWechatmenuMapper.updateByPrimaryKeySelective(record);
    }
   @Override
    public int delete(BusWechatmenu record) {
       return busWechatmenuMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<BusWechatmenu> records) {
        for (BusWechatmenu record : records){
            delete(record);
        }
        return 1;
    }

    @Override
    public BusWechatmenu findById(Long id) {
        return busWechatmenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        return MybatisPageHelper.findPage(pageRequest,busWechatmenuMapper);
    }

    @Override
    public BusWechatmenu findMenu(String wechatinfoId) {

        return busWechatmenuMapper.findMneu(wechatinfoId);
    }
}
