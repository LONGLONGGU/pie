package com.framework.pie.mybatis.hander;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 字段自动填充
 *
 * @link https://mp.baomidou.com/guide/auto-fill-metainfo.html
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增填充创建时间和删除标识
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //如果有手动填充，则以手动填充的值为准，不做自动填充
        //设置创建时间
        Object createTime = getFieldValByName("createTime", metaObject);
        if(createTime == null){
            setFieldValByName("createTime", new Date(), metaObject);
        }

        //设置删除标识
        Object delFlag = getFieldValByName("delFlag", metaObject);
        if(delFlag == null){
            byte delFlagNormal = 1;
            setFieldValByName("delFlag",delFlagNormal, metaObject);
        }
    }

    /**
     * 更新填充更新时间
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //设置更新时间
        Object lastUpdateTime = getFieldValByName("lastUpdateTime", metaObject);
        if(lastUpdateTime == null){
            setFieldValByName("lastUpdateTime",new Date(),metaObject);
        }
    }

}
