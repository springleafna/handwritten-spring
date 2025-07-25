package com.springleaf.springframework.beans.factory.config;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * 允许自定义修改应用程序上下文的 Bean 定义，调整上下文底层 Bean 工厂的 Bean 属性值 -- BeanDefinition级别处理
 * 时机：所有BeanDefinition加载完成 → Bean实例化之前
 * 作用对象：BeanDefinition（Bean的定义/元数据）
 * 作用范围：整个Bean工厂。
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
