package com.springleaf.springframework.beans.factory;

import com.springleaf.springframework.beans.BeansException;

public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
