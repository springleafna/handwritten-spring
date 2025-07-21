package com.springleaf.springframework.beans.factory;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.config.BeanDefinition;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
