package com.springleaf.springframework.beans.factory;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.config.BeanDefinition;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory {

    /**
     * 根据 beanName 获取 BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
