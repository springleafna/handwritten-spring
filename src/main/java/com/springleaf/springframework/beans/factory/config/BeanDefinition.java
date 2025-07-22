package com.springleaf.springframework.beans.factory.config;

import com.springleaf.springframework.beans.PropertyValues;

/**
 * 表示一个“Bean 的定义信息”，即描述一个 Java 对象（Bean）应该如何被创建和配置。
 */
public class BeanDefinition {

    // 要实例化的类的 Class 对象（比如 UserService.class）
    private Class beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
