package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    int deleteByPrimaryKey(Long id);

    int insert(${entity} record);

    int insertSelective(${entity} record);

    ${entity} selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(${entity} record);

    int updateByPrimaryKey(${entity} record);

    List<${entity}> findPage();

    List<${entity}> findAll();

}
</#if>
