package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * <p>
    * ${table.comment!} Mapper 接口
    * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Repository
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
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
