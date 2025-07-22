package com.springleaf.springframework.context;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     */
    void refresh() throws com.springleaf.springframework.beans.BeansException;

}
