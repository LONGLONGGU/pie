<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>
</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
        <#list table.fields as field>
            <#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}"/>
            </#if>
        </#list>
        <#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}"/>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}"/>
            </#if>
        </#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        <#list table.commonFields as field>
            ${field.name},
        </#list>
        ${table.fieldNames}
    </sql>
</#if>
<#macro mapperEl value>${r"#{"}${value}}</#macro>
<#macro jspEl value>${r"${"}${value}}</#macro>

    <select id="selectByPrimaryKey" parameterType="java.lang.Long" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${table.name}
        where id = <@mapperEl 'id'/>
    </select>

    <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
        delete  from ${table.name}
        where id = <@mapperEl 'id'/>
    </delete>

    <insert id="insert" parameterType="${package.Entity}.${entity}">
        insert into ${table.name}(${table.fieldNames})
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list table.fields as field>
             <@mapperEl field.name/>,
            </#list>
        </trim>
    </insert>

    <insert id="insertSelective" parameterType="${package.Entity}.${entity}">
        <selectKey  keyProperty="id" resultType="java.lang.Long" order="AFTER">
            SELECT LAST_INSERT_ID()
        </selectKey>
        insert into ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list table.fields as field>
            <if test="${field.propertyName} != null">
                ${field.name},
            </if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
           <#list table.fields as field>
            <if test="${field.propertyName} != null">
             <@mapperEl field.propertyName/>,
            </if>
           </#list>
        </trim>
    </insert>

    <update id="updateByPrimaryKeySelective" parameterType="${package.Entity}.${entity}">
        update ${table.name}
        <set>
        <#list table.fields as field>
            <if test="${field.propertyName} != null">
                ${field.name} = <@mapperEl field.propertyName/>,
            </if>
        </#list>
        </set>
        where id = <@mapperEl 'id'/>
    </update>

    <update id="updateByPrimaryKey" parameterType="${package.Entity}.${entity}">
        update ${table.name}
        set
         <#list table.fields as field>
           ${field.name} = <@mapperEl field.propertyName/><#sep>,
         </#list>
        where id = <@mapperEl 'id'/>
    </update>

    <select id="findAll" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${table.name}
    </select>

    <select id="findPage" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from  ${table.name}
    </select>
</mapper>
