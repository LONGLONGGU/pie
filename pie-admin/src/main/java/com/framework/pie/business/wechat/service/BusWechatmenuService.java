package com.framework.pie.business.wechat.service;

import com.framework.pie.business.wechat.model.BusWechatmenu;
import com.framework.pie.mybatis.service.CurdService;

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

