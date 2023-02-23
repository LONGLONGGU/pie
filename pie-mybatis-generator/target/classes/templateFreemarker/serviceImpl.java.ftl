package ${package.ServiceImpl};

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.framework.pie.constant.GlobalConstants;
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.framework.pie.web.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
* <p>
* ${table.comment!} 服务实现类
* </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired
    private ${table.mapperName} ${table.entityPath}Mapper;

    @Override
    public HttpResult addOrUpdate(${entity} ${table.entityPath}) {
        if (StrUtil.isEmpty(${table.entityPath}.getId())){
            ${table.entityPath}.setCreateBy(JwtUtils.getUserId());
            ${table.entityPath}Mapper.insert(${table.entityPath});
            return HttpResult.ok("添加成功");
        }
        ${table.entityPath}.setLastUpdateBy(JwtUtils.getUserId());
        ${table.entityPath}Mapper.updateById(${table.entityPath});
        return HttpResult.ok("更新成功");
    }

    @Override
    public HttpResult delete(String id) {
        UpdateWrapper<${entity}> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(${entity}::getId,id)
        .set(${entity}::getDelFlag, GlobalConstants.DEL_FLAG_DELETE)
        .set(${entity}::getLastUpdateBy,JwtUtils.getUserId());
        ${table.entityPath}Mapper.update(new ${entity}(),updateWrapper);
        return HttpResult.ok("删除成功!");
    }

    @Override
    @Transactional
    public HttpResult batchDelete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        idList.forEach(id->this.delete(id));
        return HttpResult.ok("批量删除成功!");
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest,${table.entityPath}Mapper);
    }
}
</#if>