package ${package.Controller};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.page.PageRequest;
import com.framework.pie.mybatis.page.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    public ${table.serviceName} ${table.entityPath}Service;

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public HttpResult save(@RequestBody ${entity} ${table.entityPath}){
        ${table.entityPath}Service.save(${table.entityPath});
        return HttpResult.ok("保存成功");
    }

    @ApiOperation(value = "根据id删除")
    @DeleteMapping("/delete/{<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>}")
    public HttpResult delete(@PathVariable("<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>") <#if keyPropertyAttr??>${keyPropertyAttr}<#else>Long</#if> <#if keyPropertyName??>${keyPropertyName}<#else>id</#if>){
        ${table.entityPath}Service.removeById(id);
        return HttpResult.ok("删除成功");
    }

    @ApiOperation(value = "列表条件查询，不分页")
    @GetMapping("/list")
    public HttpResult list(@RequestBody ${entity} ${table.entityPath}){
        List<${entity}> ${table.entityPath}List = ${table.entityPath}Service.list(new QueryWrapper<>(${table.entityPath}));
        return HttpResult.ok(${table.entityPath}List);
    }

    @ApiOperation(value = "列表（分页）")
    @GetMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        PageResult page = ${table.entityPath}Service.findPage(pageRequest);
        return HttpResult.ok(page);
    }

    @ApiOperation(value = "详情")
    @GetMapping("/get/{<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>}")
    public HttpResult get(@PathVariable("<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>") <#if keyPropertyAttr??>${keyPropertyAttr}<#else>Long</#if> <#if keyPropertyName??>${keyPropertyName}<#else>id</#if>){
        ${entity} ${table.entityPath} = ${table.entityPath}Service.getById(id);
        return HttpResult.ok(${table.entityPath});
    }

    @ApiOperation(value = "根据id修改")
    @PutMapping("/update/{<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>}")
    public HttpResult update(@PathVariable("<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>") <#if keyPropertyAttr??>${keyPropertyAttr}<#else>Long</#if> <#if keyPropertyName??>${keyPropertyName}<#else>id</#if>, @RequestBody ${entity} ${table.entityPath}){
        ${table.entityPath}.set<#if keyCapitalName??>${keyCapitalName}<#else>Id</#if>(<#if keyPropertyName??>${keyPropertyName}<#else>id</#if>);
        ${table.entityPath}Service.updateById(${table.entityPath});
        return HttpResult.ok("更新成功");
    }

    </#if>

}
</#if>