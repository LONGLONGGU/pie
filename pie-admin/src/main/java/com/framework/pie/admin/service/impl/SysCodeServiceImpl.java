package com.framework.pie.admin.service.impl;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.framework.pie.admin.config.druid.DruidDataSourceProperties;
import com.framework.pie.admin.dao.SysTableMapper;
import com.framework.pie.admin.service.SysCodeService;
import com.framework.pie.core.page.MybatisPageHelper;
import com.framework.pie.core.page.PageRequest;
import com.framework.pie.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class SysCodeServiceImpl implements SysCodeService {
    @Autowired
    private SysTableMapper sysTableMapper;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DruidDataSourceProperties properties;

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object name = pageRequest.getParam("name");
        if (!StringUtils.isEmpty(name)){
            return MybatisPageHelper.findPage(pageRequest,sysTableMapper,"findPageByTableColumn",name);
        }
        return MybatisPageHelper.findPage(pageRequest,sysTableMapper);
    }

    @Override
    public int generator( Map<String,Object> record) {
        String tableName = record.get("tableName").toString();
        String path = record.get("path").toString();
        String author = record.get("author").toString();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 作者
        gc.setAuthor(author);
        // 表名
        strategy.setInclude(tableName);
        // 全局配置
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/pie-admin/src/main/java");
        gc.setBaseResultMap(true);
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);
        // 设置 resultMap
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);

        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        dsc.setUrl(properties.getUrl());
        dsc.setDriverName(properties.getDriverClassName());
        dsc.setUsername(properties.getUsername());
        // 数据库密码
        dsc.setPassword(properties.getPassword());
        // 数据库url
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("");
        pc.setParent(path);
        pc.setMapper("dao");
        pc.setXml("mapping");
        pc.setEntity("model");
        mpg.setPackageInfo(pc);
        // 策略配置
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

        return 0;
    }
}
