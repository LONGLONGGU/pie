package com.framework.pie.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * @ Author     ：longlong
 * @ Date       ：Created in 15:04 2021/6/28
 */
public class HdfsClient {

    FileSystem fileSystem;

    @Before
    public void init() throws IOException, URISyntaxException, InterruptedException {

        //连接集群的地址nn
        URI uri = new URI("hdfs://hadoop102:8020");
        //配置文件
        Configuration configuration = new Configuration();

        configuration.set("dfs.replication","2");

        String user = "hadoop";

        fileSystem = FileSystem.get(uri, configuration,user);


    }

    @After
    public void close() throws IOException{
        //关闭资源
        fileSystem.close();
    }

    //创建目录
    @Test
    public void mkdir() throws Exception{
        // 2 创建目录
        fileSystem.mkdirs(new Path("/xiyou/huaguoshan/"));
    }

    //上传文件
    //参数优先级 hdfs-default.xml => hdfs-site.xml=> 在项目资源目录优先级高 =>代码里面的配置
    @Test
    public void testPut() throws IOException{
        //参数解读：参数一：表示删除源数据；参数二：是否允许覆盖；参数三：原数据路径；参数四：目的文件路径；
        fileSystem.copyFromLocalFile(false,true,new Path("/Users/longlong/working/files/sunwukong.txt"),new Path("/xiyou/huaguoshan"));
    }
    //下载文件
    @Test
    public void testDownload() throws IOException{
        //参数解读：参数一：源文件是否删除；参数二原文件路径HDFS；参数三：目标地址路径Win；参数四:是否开启本地校验
        fileSystem.copyToLocalFile(false,new Path("/xiyou/huaguoshan/sunwukong.txt"),new Path("/Users/longlong/working/files/xiyou"),false);

    }

    //删除
    @Test
    public void testDelete() throws IOException,IOException,InterruptedException,URISyntaxException{
        //参数解读：参数1：要删除的路径；参数2：是否递归删除
        //删除文件
        fileSystem.delete(new Path("/jdk-8u212-linux-x64.tar.gz"),false);
        //删除空目录
        fileSystem.delete(new Path("/xiyou"),false);
        //删除非空目录
        fileSystem.delete(new Path("/sanguo"),true);

    }

    //文件的更名和移动
    @Test
    public void testmv() throws IOException{
        //参数解读：参数1：原文件路径；参数2：目标文件路径
        //对文件名称进行修改
//        fileSystem.rename(new Path("/wcinput"),new Path("/ss"));

        //文件的移动和更名
        fileSystem.rename(new Path("/ss"),new Path("/cls"));

        //目录更名
        fileSystem.rename(new Path("/input"),new Path("/output"));
    }

    //查看文件名称、权限、长度、快信息
    @Test
    public void testListFiles() throws  IOException,InterruptedException,URISyntaxException {
        //获取所有文件信息
       RemoteIterator<LocatedFileStatus>  listFiles= fileSystem.listFiles(new Path("/"),true);

       //遍历文件
       while (listFiles.hasNext()){
           LocatedFileStatus fileStatus = listFiles.next();

           System.out.println("--------------------"+fileStatus.getPath()+"------------------------");
           System.out.println("--------------------"+fileStatus.getPermission()+"------------------------");
           System.out.println("--------------------"+fileStatus.getOwner()+"------------------------");
           System.out.println("--------------------"+fileStatus.getGroup()+"------------------------");
           System.out.println("--------------------"+fileStatus.getLen()+"------------------------");
           System.out.println("--------------------"+fileStatus.getModificationTime()+"------------------------");
           System.out.println("--------------------"+fileStatus.getReplication()+"------------------------");
           System.out.println("--------------------"+fileStatus.getBlockSize()+"------------------------");
           System.out.println("--------------------"+fileStatus.getPath().getName()+"------------------------");

           //获取块状信息
           BlockLocation[] blockLocations = fileStatus.getBlockLocations();
           System.out.println(Arrays.toString(blockLocations));

       }
    }

    //判断是文件夹还是文件
    @Test
    public void testFile() throws IOException{
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));
        for (FileStatus fileStatus :fileStatuses){
            if (fileStatus.isFile()){
                System.out.println("文件："+fileStatus.getPath().getName());

            }else {
                System.out.println("目录："+fileStatus.getPath().getName());
            }
        }
    }








}
