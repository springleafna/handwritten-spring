package com.springleaf.springframework.beans.factory.config;

import com.springleaf.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * 配置接口，由大多数 bean 工厂实现。除了 bean 工厂之外，还提供用于配置 bean 工厂的工具
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();
}
