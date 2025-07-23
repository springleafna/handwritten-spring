package com.springleaf.springframework.context;

import java.util.EventListener;


/**
 * 事件监听器 泛型接口，提供 事件处理 的方法。
 * @param <E> 事件类型
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理事件，每个事件监听器都要实现该接口
     * @param event 要响应的事件
     */
    void onApplicationEvent(E event);

}
