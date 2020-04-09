package com.moxi.interview.study.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * 扩展的BeanFactory
 * @author: 陌溪
 * @create: 2020-04-02-10:52
 */
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 针对 Bean工厂做一些事情

        // BeanFactory存储了BeanDefinition
        GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("c");
        System.out.println(genericBeanDefinition);
    }
}
