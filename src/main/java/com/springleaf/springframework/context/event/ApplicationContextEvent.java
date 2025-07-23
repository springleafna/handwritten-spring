package com.springleaf.springframework.context.event;

import com.springleaf.springframework.context.ApplicationContext;
import com.springleaf.springframework.context.ApplicationEvent;

/**
 * 容器中的事件 以及 用户自定义的事件 都需继承该类
 */
public class ApplicationContextEvent extends ApplicationEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取事件来源
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }

}
