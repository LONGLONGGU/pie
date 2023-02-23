package com.framework.pie.mybatis.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
/**
 * @Author: guanqiangzhang
 * @Date:16:13 2021/9/13
 *  生成Long 类型唯一ID
 *
 */
public class OnlyId {
    private static AtomicLong id;

    public synchronized static Long getId() {
        //如果需要更长 或者更大冗余空间, 只需要 time * 10^n   即可
        //当前可保证1毫秒 生成 10000条不重复
        Long time = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))*10000;
        if (id == null) {
            id = new AtomicLong(time);
            return id.get();
        }
        if (time <= id.get()) {
            id.addAndGet(1);
        } else {
            id = new AtomicLong(time);
        }
        return id.get();
    }
}
