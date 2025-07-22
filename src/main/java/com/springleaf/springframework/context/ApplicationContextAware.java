package com.springleaf.springframework.context;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
    