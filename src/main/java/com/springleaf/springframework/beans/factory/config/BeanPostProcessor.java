package com.springleaf.springframework.beans.factory.config;

import com.springleaf.springframework.beans.BeansException;

/**
 * 用于修改新实例化 Bean 对象的扩展点 -- Bean实例级别处理
 * 时机：Bean实例化之后 → Bean初始化前后
 * 作用对象：Bean实例（实际的对象）
 * 作用范围：每个Bean实例
 */
public interface BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
