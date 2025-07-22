package com.springleaf.springframework.test.common;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.PropertyValue;
import com.springleaf.springframework.beans.PropertyValues;
import com.springleaf.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.springleaf.springframework.beans.factory.config.BeanDefinition;
import com.springleaf.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }

}
