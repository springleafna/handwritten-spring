package com.springleaf.springframework.beans.factory;

public interface FactoryBean<T> {

    /**
     * 创建并返回目标对象实例
     */
    T getObject() throws Exception;

    /**
     * 返回目标对象的类型
     */
    Class<?> getObjectType();

    /**
     * 是否是单例
     */
    boolean isSingleton();

}
