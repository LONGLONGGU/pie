package com.framework.pie.admin.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.framework.pie.mybatis.model.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 通讯录bean
 * @author ：longlong
 * @date ：Created in 2022/1/5 13:31
 * @modified By：
 * @version: ：V1.0
 */
@Data
public class AddressBookBean extends BaseEntity {

    // 名称
    private String name;

    // 名称
    private String nickName;

    // department部门   user 用户
    private String type;

    //电话号码
    private String mobile;

    //children
    private List<AddressBookBean> children;

    //是否有子节点
    private boolean hasChildren = true;

    //是否选中
    private boolean checked = false;



}
