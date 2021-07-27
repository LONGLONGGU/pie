package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.framework.pie.mybatis.page.MybatisPageHelper;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import javax.annotation.Resource;
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
@Transactional(rollbackFor = Exception.class)
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Resource
    private ${table.mapperName} ${table.entityPath}Mapper;

    @Override
    public int saveByNativeSql(${entity} record) {
      if (record.getId() == null || record.getId() == 0){
        return ${table.entityPath}Mapper.insertSelective(record);
       }
       return ${table.entityPath}Mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(${entity} record) {
       return ${table.entityPath}Mapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<${entity}> records) {
        for (${entity} record : records){
            delete(record);
        }
        return 1;
    }

    @Override
    public ${entity} findById(Long id) {
        return ${table.entityPath}Mapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        return MybatisPageHelper.findPage(pageRequest,${table.entityPath}Mapper);
    }

}
</#if>
