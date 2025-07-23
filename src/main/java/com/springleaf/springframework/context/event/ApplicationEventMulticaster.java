package com.springleaf.springframework.context.event;

import com.springleaf.springframework.context.ApplicationEvent;
import com.springleaf.springframework.context.ApplicationListener;

/**
 * 事件处理接口
 */
public interface ApplicationEventMulticaster {

    /**
     * 新增监听器
     * @param listener 要添加的监听器
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 删除监听器
     * @param listener 要删除的监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 将给定的事件广播到适当的监听器。
     * @param event 要广播的事件
     */
    void multicastEvent(ApplicationEvent event);
}
