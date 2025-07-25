package com.springleaf.springframework.beans.factory.config;

import com.springleaf.springframework.beans.PropertyValues;

/**
 * 表示一个“Bean 的定义信息”，
 * 包括属性、构造方法参数、依赖的 Bean 名称及是否单例、延迟加载等
 * 即描述一个 Java 对象（Bean）应该如何被创建和配置。
 */
public class BeanDefinition {

    // 单例、原型标识符
    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;
    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    // 要实例化的 bean 的 Class 对象（比如 UserService.class）
    private Class beanClass;

    // bean 的属性一个或多个<property name="xxx" value="yyy"/>
    private PropertyValues propertyValues;

    // bean 的初始化方法 init-method="initDataMethod"
    private String initMethodName;

    // bean 的销毁方法 destroy-method="destroyDataMethod"
    private String destroyMethodName;

    // bean 的作用域
    private String scope = SCOPE_SINGLETON;

    // bean 是否为单例
    private boolean singleton = true;

    // bean 是否为原型
    private boolean prototype = false;

    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
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

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
