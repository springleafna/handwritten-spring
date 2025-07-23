package com.springleaf.springframework.beans.factory.config;

/**
 * 单例 Bean 注册接口
 */
public interface SingletonBeanRegistry {

    /**
     * 获取单例对象
     */
    Object getSingleton(String beanName);

    /**
     * 注册单例对象
     */
    void registerSingleton(String beanName, Object singletonObject);

}
