package com.springleaf.springframework.beans.factory;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.springleaf.springframework.beans.factory.config.BeanDefinition;
import com.springleaf.springframework.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 根据 beanName 获取 BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;
}
