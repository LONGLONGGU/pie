package ${package.Controller};
import cn.hutool.core.util.StrUtil;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* <p>
* ${table.comment!} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/

@Api(tags = "${table.comment!}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if><#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    <#--设置主键信息-->
    <#list table.fields as field>
        <#if field.keyFlag>
            <#assign keyPropertyName="${field.propertyName}"/>
            <#assign keyPropertyAttr="${field.propertyType}"/>
            <#assign keyCapitalName="${field.capitalName}"/>
        </#if>
    </#list>

    @Autowired
    private ${table.serviceName} ${table.entityPath}Service;

    /**
    * 新增和修改
    */
    @ApiOperation(value = "新增修改${table.comment}")
    @PostMapping(value = "/addOrUpdate")
    public HttpResult addOrUpdate(@RequestBody ${entity} ${table.entityPath}){
        return ${table.entityPath}Service.addOrUpdate(${table.entityPath});
    }

    /**
    * 根据id逻辑删除一条数据
    */
    @ApiOperation(value = "根据单个id逻辑删除")
    @GetMapping("/delete/{<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>}")
    public HttpResult delete(@PathVariable("<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>") <#if keyPropertyAttr??>${keyPropertyAttr}<#else>String</#if> <#if keyPropertyName??>${keyPropertyName}<#else>id</#if>){
        return ${table.entityPath}Service.delete(id);
    }

    /**
    * 根据ids批量逻辑删除数据
    */
    @ApiOperation(value = "根据ids逻辑删除")
    @GetMapping("/batchDelete")
    public HttpResult batchDelete(@RequestBody Map map){
        if(map == null || map.size() == 0){
            return HttpResult.error("参数信息不能为空!");
        }
        if(StrUtil.isEmptyIfStr(map.get("ids"))){
            return HttpResult.error("必填参数[ids]不能为空!");
        }
        return ${table.entityPath}Service.batchDelete(map.get("ids").toString());
    }

    /**
    * 列表（分页）
    */
    @ApiOperation(value = "列表（分页）")
    @GetMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        PageResult page = ${table.entityPath}Service.findPage(pageRequest);
        return HttpResult.ok(page);
    }

    /**
    * 根据ID查询详情
    */
    @ApiOperation(value = "详情")
    @GetMapping("/get/{<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>}")
    public HttpResult get(@PathVariable("<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>") <#if keyPropertyAttr??>${keyPropertyAttr}<#else>String</#if> <#if keyPropertyName??>${keyPropertyName}<#else>id</#if>){
        ${entity} ${table.entityPath} = ${table.entityPath}Service.getById(id);
        return HttpResult.ok(${table.entityPath});
    }

    </#if>

}
</#if>