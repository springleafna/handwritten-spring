package com.springleaf.springframework.beans.factory;

import com.springleaf.springframework.beans.BeansException;

/**
 * BeanFactory 是 Spring 的核心接口，提供了获取 bean 的能力
 */
public interface BeanFactory {

    /**
     * 根据 beanName 获取 bean 实例
     */
    Object getBean(String name) throws BeansException;

    /**
     * 带构造参数获取
     * 用途：获取原型(Prototype)作用域的Bean，并传入构造参数
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * 指定类型获取
     * 用途：类型安全的获取方式，避免手动类型转换
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /**
     * 按类型获取
     * 注意：如果同一类型有多个Bean会抛出异常
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

}
