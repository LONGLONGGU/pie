package com.framework.pie.quartz.utils;

import com.framework.pie.http.HttpResult;
import com.framework.pie.mybatis.utils.RunSqlUtils;
import com.framework.pie.quartz.feign.DataCleanClient;
import com.framework.pie.quartz.redis.client.RedisClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
@EnableScheduling
public class SqlCodeCatchImpl implements ApplicationListener<ApplicationReadyEvent> {
    @Resource
    private RedisClient redisClient;
    @Resource
    private DataCleanClient dataCleanClient;

    private RunSqlUtils rsNews;
    private RunSqlUtils rsPolicy;
    private RunSqlUtils rsPolicyjd;
    private RunSqlUtils rsLoadNews;
    private RunSqlUtils rsLoadPolicy;
    private RunSqlUtils rsLoadPolicyjd;
    private RunSqlUtils rsArea;
    private RunSqlUtils rsLog;
    private List<String> newsSqlList = new ArrayList<String>();
    private List<String> policySqlList = new ArrayList<String>();
    private List<String> policyJDSqlList = new ArrayList<String>();

    private static Base64.Encoder encoder = Base64.getEncoder();


    /**
     * 作者：沈驰程
     * 方法描述：开机启动查询app>招商动态>项目统计 sql使用参数
     */
    public void initData() {
        /***数据源获取***/
        //查询新闻、政策近3天更新数据
        newsSqlList = new ArrayList<String>();
        policySqlList = new ArrayList<String>();
        policyJDSqlList = new ArrayList<String>();
        List<Map<String,Object>> newsList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> policyList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> policyJDList = new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> SfList = new ArrayList<>();
        List<Map<String, Object>> NewsLoadList = new ArrayList<>();
        List<Map<String, Object>> PolicyLoadList = new ArrayList<>();
        List<Map<String, Object>> PolicyJDLoadList = new ArrayList<>();
        rsPolicyjd=new RunSqlUtils("com.mysql.cj.jdbc.Driver","jdbc:mysql://172.16.1.125:3306/crawler?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8","root","Ynzsj@Zst#125");
        rsNews=new RunSqlUtils("com.mysql.cj.jdbc.Driver","jdbc:mysql://172.16.1.125:3306/crawler?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8","root","Ynzsj@Zst#125");
        rsPolicy=new RunSqlUtils("com.mysql.cj.jdbc.Driver","jdbc:mysql://172.16.1.125:3306/crawler?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8","root","Ynzsj@Zst#125");
        rsArea=new RunSqlUtils("com.mysql.cj.jdbc.Driver","jdbc:mysql://172.16.1.125:3306/crawler?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8","root","Ynzsj@Zst#125");
        rsLoadNews=new RunSqlUtils("com.mysql.cj.jdbc.Driver","jdbc:mysql://172.16.1.131:3306/zst?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8","root","Ynzsj@Zst#0871");
        rsLoadPolicy=new RunSqlUtils("com.mysql.cj.jdbc.Driver","jdbc:mysql://172.16.1.131:3306/zst?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8","root","Ynzsj@Zst#0871");
        rsLoadPolicyjd=new RunSqlUtils("com.mysql.cj.jdbc.Driver","jdbc:mysql://172.16.1.131:3306/zst?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8","root","Ynzsj@Zst#0871");
        rsLog=new RunSqlUtils("com.mysql.cj.jdbc.Driver","jdbc:mysql://172.16.3.28:3306/zst-crawler?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8","root","Pass_123");
        if(rsNews.getConnection()){
            String sql="SELECT\n" +
                    "\tIFNULL(title, '') title,\n" +
                    "\tIFNULL(publishdate, '') publishdate,\n" +
                    "\tIFNULL(source, '') source,\n" +
                    "\tIFNULL(content, '') content,\n" +
                    "\tIFNULL(id, '') id,\n" +
                    "\tIFNULL(provice, '') provice\n" +
                    "FROM\n" +
                    "\tbase_pageinfo\n" +
                    "WHERE\n" +
                    "\tDATE_SUB(CURDATE(), INTERVAL 3 DAY) <= DATE_FORMAT(created_at, '%y-%m-%d')\n" +
                    "AND title <> ''\n" +
                    "AND title IS NOT NULL\n" +
                    "AND content <> ''\n" +
                    "AND content IS NOT NULL\n" +
                    "ORDER BY\n" +
                    "\tcreated_at DESC";
            newsList = rsNews.getObjectLists(sql);
            rsNews.close();
        }
        if(rsPolicy.getConnection()){
            String sql="SELECT\n" +
                    "\tIFNULL(id, '') id,\n" +
                    "\tIFNULL(url, '') url,\n" +
                    "\tIFNULL(title, '') title,\n" +
                    "\tIFNULL(publishdate, '') publishdate,\n" +
                    "\tIFNULL(source, '') source,\n" +
                    "\tIFNULL(content, '') content,\n" +
                    "\tIFNULL(provice, '') provice\n" +
                    "FROM\n" +
                    "\tbase_zcpageinfo\n" +
                    "WHERE\n" +
                    "\tDATE_SUB(CURDATE(), INTERVAL 3 DAY) <= DATE_FORMAT(created_at, '%y-%m-%d')\n" +
                    "AND title <> ''\n" +
                    "AND title IS NOT NULL\n" +
                    "AND content <> ''\n" +
                    "AND content IS NOT NULL\n" +
                    "ORDER BY\n" +
                    "\tcreated_at DESC";
            policyList = rsPolicy.getObjectLists(sql);
            rsPolicy.close();
        }
        if(rsPolicyjd.getConnection()){
            String sql="SELECT\n" +
                    "\tIFNULL(url, '') url,\n" +
                    "\tIFNULL(publish, '') publish,\n" +
                    "\tIFNULL(title, '') title,\n" +
                    "\tIFNULL(publishdate, '') publishdate,\n" +
                    "\tIFNULL(source, '') source,\n" +
                    "\tIFNULL(content, '') content,\n" +
                    "\tIFNULL(id, '') id,\n" +
                    "\tIFNULL(provice, '') provice\n" +
                    "FROM\n" +
                    "\tbase_zcjdpageinfo\n" +
                    "WHERE\n" +
                    "\tDATE_SUB(CURDATE(), INTERVAL 360 DAY) <= DATE_FORMAT(created_at, '%y-%m-%d')\n" +
                    "AND title <> ''\n" +
                    "AND title IS NOT NULL\n" +
                    "AND content <> ''\n" +
                    "AND content IS NOT NULL\n" +
                    "ORDER BY\n" +
                    "\tcreated_at DESC;";
            policyJDList = rsPolicyjd.getObjectLists(sql);
            rsPolicyjd.close();
        }
        //加载预处理数据
        List<Map<String, Object>> sfList = new ArrayList<>();
        if(rsArea.getConnection()){
            String codeSql = "select F_CODE code,F_SHORT_NAME name from t_base_regionalism";
            SfList = rsArea.getObjectLists(codeSql);
            rsArea.close();
            for (Map m : SfList) {
                SfMap.putCache(m.get("name").toString(), m.get("code").toString());
            }
        }
        //加载新闻去重数据
        if(rsLoadNews.getConnection()){
            String NewsLoadSql = "select title from bus_industry_news";
            NewsLoadList = rsLoadNews.getObjectLists(NewsLoadSql);
        }
        //加载政策去重数据
        if(rsLoadPolicyjd.getConnection()){
            String PolicyLoadSql = "select name from bus_policy";
            PolicyLoadList = rsLoadPolicyjd.getObjectLists(PolicyLoadSql);
        }
        //加载政策去重数据
        if(rsLoadPolicyjd.getConnection()){
            String PolicyLoadSql = "select title FROM bus_policy_explain";
            PolicyJDLoadList = rsLoadPolicyjd.getObjectLists(PolicyLoadSql);
        }
        /***数据预处理***/
        //政策获取地区编码
        policyList.stream().forEach(item ->{
            if(SfMap.getCache(item.get("provice").toString()) != null){
                if("530000".equals(SfMap.getCache(item.get("provice").toString()))){
                    item.put("area","530100,530300,530400,530500,530600,530700,530800,530900,532300,532500,532600,532800,532900,533100,533300,533400");
                }else{
                    item.put("area",SfMap.getCache(item.get("provice").toString()));
                }
            }else{
                item.put("area","");
            }
        });
        /***调用数据清洗接口***/
        //填充去重数据至redis
        redisClient.flushDB();
        NewsLoadList.stream().forEach(item ->{
            redisClient.set(item.get("title").toString(),"");
        });
        PolicyLoadList.stream().forEach(item ->{
            redisClient.set(item.get("name").toString(),"");
        });
        PolicyJDLoadList.stream().forEach(item ->{
            redisClient.set(item.get("title").toString(),"");
        });

        //清洗新闻数据
        newsList.stream().forEach(item ->{
            if(redisClient.exists(item.get("title").toString())) {
                System.out.println("==========数据去重==========");
            }else {
                redisClient.set(item.get("title").toString(),"");
                //获取关键词
                MultiValueMap<String,String> mapNews = new LinkedMultiValueMap<String,String>();
                mapNews.add("text",item.get("title").toString() + item.get("content").toString());
                HttpResult result = dataCleanClient.dataCleaning(mapNews,"news",item.get("id").toString());
                String coreWord = result.getMsg();
                String encodedText = "";
                try {
                    byte[] textByte = item.get("content").toString().getBytes("UTF-8");
                    encodedText = encoder.encodeToString(textByte);
                } catch (Exception e) {

                }
                String addSql = "INSERT INTO `bus_industry_news` (`id`,`creation_time`,`deleted`,`modification_time`,`odr`,`content`,`industry_name`,`industry_url`,`push_time`,`title`,`type`,`url`,`creator_user_id`,`modifier_user_id`,`bus_industry_id`,`source_base`,`source_id`,`source_table`,`coreword`) \n" +
                        "VALUES ('" + item.get("id").toString() + "', NOW(), '0', NULL, '0', '" + encodedText + "', '"+item.get("source").toString()+"', '', '" + item.get("publishdate").toString() + "', '" + item.get("title").toString() + "', '1', '', '2c9081117a1e1ee7017a2c37186a0003', '2c9081117a1e1ee7017a2c37186a0003', '297ea447776aa95c01776ab4d30d0002', 'admin_self', '00000ba79a604ea399368dbdec02a2cb', 'base_dtpageinfo','" + coreWord + "');";
                newsSqlList.add(addSql);
            }
        });
        JdbcSave jdbcSave = new JdbcSave();
        jdbcSave.getConnection();
        jdbcSave.demoBath(newsSqlList);
        jdbcSave.releaseConn();

        //清洗政策数据
        policyList.stream().forEach(item ->{
            if(redisClient.exists(item.get("title").toString())) {
                System.out.println("==========数据去重==========");
            }else {
                redisClient.set(item.get("title").toString(),"");
                //获取关键词
                MultiValueMap<String,String> mapNews = new LinkedMultiValueMap<String,String>();
                mapNews.add("text",item.get("title").toString() + item.get("content").toString());
                HttpResult result = dataCleanClient.dataCleaning(mapNews,"policy",item.get("id").toString());
                String coreWord = result.getMsg();
                String encodedText = "";
                try {
                    byte[] textByte = item.get("content").toString().getBytes("UTF-8");
                    encodedText = encoder.encodeToString(textByte);
                } catch (Exception e) {

                }
                String addSql = "INSERT INTO bus_policy(`id`,`creation_time`,`deleted`,`modification_time`,`odr`,`content`,`name`,`push_org`,`push_time`,`type`,`creator_user_id`,`modifier_user_id`,`bus_district_id`,`collect_policy_id`,`source_base`,`source_id`,`source_table`,`coreword`,`policy_dept`) \n" +
                        "VALUES ('" + item.get("id").toString() + "', NOW(), '0', NULL, '0', '" + encodedText + "', '" + item.get("title").toString() + "', '" + item.get("source").toString() + "', '" + item.get("publishdate").toString() + "', '综合', NULL, NULL, NULL, NULL, 'crawler', '', '" + item.get("url").toString() + "','" + coreWord + "','" + item.get("area").toString() + "');";
                policySqlList.add(addSql);
            }
        });
        JdbcSave jdbcSaves = new JdbcSave();
        jdbcSaves.getConnection();
        jdbcSaves.demoBath(policySqlList);
        jdbcSaves.releaseConn();

        //清洗政策解读数据
        policyJDList.stream().forEach(item ->{
            if(redisClient.exists(item.get("title").toString())) {
                System.out.println("==========数据去重==========");
            }else {
                redisClient.set(item.get("title").toString(),"");
                String addSql = "INSERT INTO `bus_policy_explain` (`id`, `creation_time`, `deleted`, `modification_time`, `odr`, `content`, `title`, `creator_user_id`, `modifier_user_id`, `bus_district_id`, `bus_policy_id`, `bus_policy_explain_id`, `push_name`, `push_time`, `source_name`, `source_url`, `url`, `source_base`, `source_id`, `source_table`) \n" +
                        "VALUES ('"+item.get("id").toString()+"', NOW(), '0', null, '0', '"+item.get("content").toString()+"', '"+item.get("title").toString()+"', 'root', 'root', NULL, NULL, NULL, '"+item.get("provice").toString()+"', '"+item.get("publishdate").toString()+"', '"+item.get("source").toString()+"', '"+item.get("url").toString()+"', '', 'crawler', '', 'base_zcjdpageinfo');";
                policyJDSqlList.add(addSql);
            }
        });
        JdbcSave jdbcSavejd = new JdbcSave();
        jdbcSavejd.getConnection();
        jdbcSavejd.demoBath(policyJDSqlList);
        jdbcSavejd.releaseConn();

        System.out.println("=================完成同步数据======================");

        //记录同步日志
        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(rsLog.getConnection()){
            String logSql = "INSERT INTO `bus_import_log` (`workTime`, `news`, `policy`) VALUES ('"+sbf.format(d)+"', '"+newsSqlList.size()+"', '"+policySqlList.size()+"');";
            rsLog.getUpdateObjectList(logSql);
        }
    }


    @PreDestroy
    public void destroy() {

    }

    /**
     * 每天定时更新一次参数
     */
    @Scheduled(cron = "0 44 8 * * ?")
    public void testOne() {
        //每2小时执行一次缓存
        System.out.println("=================开始同步数据======================");
        initData();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        //initData();


    }
}
