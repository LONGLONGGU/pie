package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import com.framework.pie.http.HttpResult;

/**
* <p>
* ${table.comment!} 服务类
* </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
    * 新增和修改数据信息
    * @param ${table.entityPath}
    * @return
    */
    HttpResult addOrUpdate(${entity} ${table.entityPath});

    /**
    * 单条记录逻辑删除
    * @param id
    * @return
    */
    HttpResult delete(String id);

    /**
    * 批量逻辑删除
    * @param ids
    * @return
    */
    HttpResult batchDelete(String ids);

    /**
    * 分页查询
    * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
    * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
    * 影响服务层以上的分页接口，起到了解耦的作用
    * @param pageRequest 自定义，统一分页查询请求
    * @return PageResult 自定义，统一分页查询结果
    */
    PageResult findPage(PageRequest pageRequest);
}
</#if>