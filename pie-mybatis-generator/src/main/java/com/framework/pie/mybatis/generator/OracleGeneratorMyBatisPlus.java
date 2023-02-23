package com.framework.pie.mybatis.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OracleGeneratorMyBatisPlus {

    /**
     * @param dataSourceUrl 数据连接URL地址
     * @param dataSourceName 数据库名称
     * @param dataSourcePassword 数据库密码
     * @param dataSourceDriver 数据库驱动名称
     * @param tables 要生成的数据表，多个数据表用英文逗号分隔
     * @param packageParent 包名
     * @param codePath 存放代码的路径信息
     * @param isNormalize
     * @description:
     * @return: void
     * @author: houjh
     * @time: 2021/07/08 22:46
     */
    public static void generate(String dataSourceUrl, String dataSourceName, String dataSourcePassword, String dataSourceDriver,
                                String tables, String packageParent, String codePath,boolean isNormalize) {

        AutoGenerator mpg = new AutoGenerator();
        // 配置策略
        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = codePath;// 当前项目的路径
        gc.setOutputDir(projectPath + "/src/main/java");// 生成文件输出根目录
        gc.setAuthor("houjh");// 作者
        gc.setOpen(true); // 生成完成后不弹出文件框
        gc.setFileOverride(true); // 文件是否覆盖
        gc.setIdType(IdType.ASSIGN_UUID); //主键策略 实体类主键ID类型
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true); // 是否开启swagger
        //gc.setActiveRecord(true); //活动记录 不需要ActiveRecord特性的请改为false 是否支持AR模式 继承于基类的时候需要设置为false
        gc.setActiveRecord(false); //活动记录 不需要ActiveRecord特性的请改为false 是否支持AR模式 继承于基类的时候需要设置为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap xml映射文件的配置
        //gc.setBaseColumnList(false);// XML columList xml映射文件的配置
        gc.setBaseColumnList(true);// XML columList xml映射文件的配置

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        //2、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.ORACLE);
        dsc.setUrl(dataSourceUrl);
        dsc.setDriverName(dataSourceDriver);
        dsc.setUsername(dataSourceName);
        dsc.setPassword(dataSourcePassword);
        mpg.setDataSource(dsc);

        //3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageParent);
        pc.setController("controller"); // 可以不用设置，默认是这个
        pc.setService("service"); // 同上
        pc.setServiceImpl("service.impl"); // 同上
        pc.setMapper("dao"); // 默认是mapper
        pc.setEntity("model"); // 默认是entity
        pc.setXml("mapping"); // 默认是默认是mapper.xml
        //pc.setModuleName("demo"); // 控制层请求地址的包名显示
        mpg.setPackageInfo(pc);

        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(tables.split(",")); // 需要生成的表 设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //设置是否基于Lombok的方式生成，如果需要基于lombok的方式生成直接将此处设置成true即可
        strategy.setEntityLombokModel(true); // 自动lombok；
        //strategy.setEntityLombokModel(false); // 自动lombok；
        strategy.setCapitalMode(false); // 开启全局大写命名 一般ORACL数据库时使用
        strategy.setSuperMapperClass(null); //

        //如果子类不需要继承于基类信息，则以下两句代码不需要即可
        //strategy.setSuperEntityClass("com.framework.pie.mybatis.model.BaseEntity"); //设置基类路径信息
        //设置基类的数据字段信息，注意填写的EntityColumns是数据库字段，不是实体类字段，不要搞混淆了
        //strategy.setSuperEntityColumns(new String[] {"id","create_by","create_time","last_update_by","last_update_time","del_flag"});


        // 是否需要开启特定规范字段
        if (true == isNormalize) {
            strategy.setLogicDeleteFieldName("deleted");
            // 自动填充配置
            TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
            TableFill gmtModified = new TableFill("gmt_modified",
                    FieldFill.INSERT_UPDATE);
            ArrayList<TableFill> tableFills = new ArrayList<>();
            tableFills.add(gmtCreate);
            tableFills.add(gmtModified);
            strategy.setTableFillList(tableFills);
            // 乐观锁
            strategy.setVersionFieldName("version");
        }
        strategy.setRestControllerStyle(true); // 控制：true——生成@RsetController false——生成@Controller
        strategy.setControllerMappingHyphenStyle(true); //
        strategy.setEntityTableFieldAnnotationEnable(true); // 表字段注释启动 启动模板中的这个 <#if table.convert>
        strategy.setEntityBooleanColumnRemoveIsPrefix(true); // 是否删除实体类字段的前缀
        //以下可以用来去掉某张表名的前缀后生成实体类
        //strategy.setTablePrefix("mdm_"); // 去掉表名mdm_inf_rec_data中的 mdm_ 类名为InfRecData
        strategy.setControllerMappingHyphenStyle(false); // 控制层mapping的映射地址 false：infRecData true：inf_rec_data
        mpg.setStrategy(strategy);

        //模板生成器
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templateFreemarker/controller.java");
        tc.setService("/templateFreemarker/service.java");
        tc.setServiceImpl("/templateFreemarker/serviceImpl.java");
        tc.setEntity("/templateFreemarker/entity.java");
        tc.setMapper("/templateFreemarker/mapper.java");
        tc.setXml("/templateFreemarker/mapper.xml");
        mpg.setTemplate(tc);

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String,Object> map = new HashMap<>();
                map.put("superColums",this.getConfig().getStrategyConfig().getSuperEntityColumns());
                this.setMap(map);
            }
        };

        mpg.setCfg(cfg);

        mpg.execute(); //执行
    }


    public static void main(String[] args) {
        String dataSourceUrl = "jdbc:oracle:thin:@172.16.3.2:1521:orcl";
        String dataSourceName = "INVEST_BIZ";
        String dataSourcePassword = "Ynzsj1p6_db#Biz";
        String dataSourceDriver = "oracle.jdbc.driver.OracleDriver";
        String tables = "T_INVEST_SUM"; //需要生成的表名，多张表用英文逗号隔开
        String packageParent = "com.framework.pie.multiple.datasource.oracle";
        String codePath = "E:/testCodeInfo";
        boolean isNormalize = false;
        generate(dataSourceUrl,dataSourceName,dataSourcePassword,dataSourceDriver,tables,packageParent,codePath,isNormalize);
    }
}
