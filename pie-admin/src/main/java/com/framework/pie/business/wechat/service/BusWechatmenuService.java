package com.framework.pie.business.wechat.service;

import com.framework.pie.business.wechat.model.BusWechatmenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.framework.pie.core.service.CurdService;

import java.util.List;

/**
    * <p>
    * 公众号菜单  服务类
    * </p>
*
* @author longlong
* @since 2020-09-25
*/
public interface BusWechatmenuService extends CurdService<BusWechatmenu> {

    BusWechatmenu findMenu(String wechatinfoId);
}

