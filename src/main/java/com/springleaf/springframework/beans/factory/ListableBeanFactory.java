package com.springleaf.springframework.beans.factory;

import com.springleaf.springframework.beans.BeansException;

import java.util.Map;

/**
 * Listable = 可列举的、可列表化的
 * 就是指能够批量获取和枚举查看Bean的能力。
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 按照类型返回 Bean 实例
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回注册表中所有的 Bean 名称
     */
    String[] getBeanDefinitionNames();

}
