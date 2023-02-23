package com.framework.pie.quartz.config.quartz;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author ：longlong
 * @date ：Created in 2022/2/11 09:11
 * @modified By：
 * @version: ：V1.0
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext appCtx;
    /**
     * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
     * @param applicationContext ApplicationContext 对象.
     * @throws BeansException
     * @author chenchen
     */
    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
        appCtx = applicationContext;
    }

    /**
     * 获取ApplicationContext
     * @return
     * @author chenchen
     */
    public static ApplicationContext getApplicationContext(){
        return appCtx;
    }

    /**
     * 这是一个便利的方法，帮助我们快速得到一个BEAN
     * @param beanName bean的名字
     * @return 返回一个bean对象
     * @author chenchen
     */
    public static Object getBean( String beanName ) {
        return appCtx.getBean( beanName );
    }

}
