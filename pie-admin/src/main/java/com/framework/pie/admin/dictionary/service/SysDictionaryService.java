package com.framework.pie.admin.dictionary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.pie.admin.dictionary.model.SysDictionary;
import com.framework.pie.http.HttpResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houjh
 * @since 2021-09-06
 */
public interface SysDictionaryService extends IService<SysDictionary> {
    /**
     * 添加或修改数据字典信息
     * @param record
     * @return
     */
    HttpResult addOrUpdate(SysDictionary record);

    /**
     * 删除数据字典信息
     * @param id
     * @return
     */
    HttpResult delete(String id);

    /**
     * 根据父级ID查询数据字典信息
     * @param parentId
     * @return
     */
    List<SysDictionary> asyncFindTree(String parentId);

    /**
     * 验证数据字典code是否重复
     * @param id
     * @param code
     * @return
     */
    HttpResult validateCode(String id,String code);
}

