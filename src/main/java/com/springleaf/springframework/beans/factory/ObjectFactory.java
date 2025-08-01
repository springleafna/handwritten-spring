package com.springleaf.springframework.beans.factory;

import com.springleaf.springframework.beans.BeansException;

/**
 * 定义一个可以返回 Object 实例的工厂（可能是共享的或独立的）。
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}
