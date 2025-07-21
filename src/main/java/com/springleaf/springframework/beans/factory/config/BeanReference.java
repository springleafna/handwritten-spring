package com.springleaf.springframework.beans.factory.config;

/**
 * Bean 的引用
 * 它是一个封装了 Bean 名称的引用对象，表示“这个属性依赖于另一个 Bean”，而不是一个普通值。
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

}
