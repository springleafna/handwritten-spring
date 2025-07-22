package com.springleaf.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.DisposableBean;
import com.springleaf.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    /**
     * 如果已经实现了 DisposableBean接口，并且xml中配置的 destroy-method="destroy"，
     * 那就说明两个指的是同一个方法，只需执行一次。
     */
    @Override
    public void destroy() throws Exception {
        // 1. 判断是否实现接口 DisposableBean
        if (bean instanceof DisposableBean) {
            // 如果是则执行 destroy() 方法
            ((DisposableBean) bean).destroy();
        }

        // 2. 注解配置 destroy-method {判断是为了避免二次执行销毁}
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
