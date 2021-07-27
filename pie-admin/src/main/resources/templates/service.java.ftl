package ${package.Service};

import ${package.Entity}.${entity};
import com.framework.pie.mybatis.service.CurdService;

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
public interface ${table.serviceName} extends CurdService<${entity}> {

}

</#if>