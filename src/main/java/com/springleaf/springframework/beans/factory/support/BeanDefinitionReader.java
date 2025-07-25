package com.springleaf.springframework.beans.factory.support;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.core.io.Resource;
import com.springleaf.springframework.core.io.ResourceLoader;

/**
 * 读取 Spring 配置文件中的内容，将其转换为 IoC 容器内部的数据结构：BeanDefinition
 * 资源加载器 + BeanDefinition 注册器。
 * BeanDefinitionRegistry 接口一次只能注册一个 BeanDefinition，而且只能自己构造 BeanDefinition 类来注册。
 * BeanDefinitionReader 解决了这些问题，它一般可以使用一个 BeanDefinitionRegistry 构造，
 * 然后通过 loadBeanDefinitions()等方法，把 Resources 转化为多个 BeanDefinition 并注册到 BeanDefinitionRegistry。
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;

}
