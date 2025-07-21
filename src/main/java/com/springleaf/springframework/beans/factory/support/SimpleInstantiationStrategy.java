package com.springleaf.springframework.beans.factory.support;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * JDK实例化
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        Class clazz = beanDefinition.getBeanClass();
        try {
            if (null != ctor) {
                // 使用传入的构造函数和参数 args 创建实例
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                // 默认无参构造函数创建实例
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            /*
              NoSuchMethodException：找不到指定的构造方法。
              InstantiationException：类是抽象类或接口，无法实例化。
              IllegalAccessException：构造方法不可访问（非 public）。
              InvocationTargetException：构造方法内部抛出异常
             */
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
    }
}
