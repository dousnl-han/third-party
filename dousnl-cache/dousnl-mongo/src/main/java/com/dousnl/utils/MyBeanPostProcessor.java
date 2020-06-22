package com.dousnl.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/6/22 15:02
 */
//@Component
public class MyBeanPostProcessor implements BeanPostProcessor,InitializingBean {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //对所有bean生效
        System.out.println(">>>>>>>>>>>>>>>>>postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(">>>>>>>>>>>>>>>>>postProcessAfterInitialization");
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>afterPropertiesSet");
    }
}
