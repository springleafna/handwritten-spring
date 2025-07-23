package com.springleaf.springframework.context;

/**
 * 事件发布者接口
 */
public interface ApplicationEventPublisher {

    /**
     * 通知所有在此应用程序注册的侦听器应用程序事件。
     * @param event 要发布的事件
     */
    void publishEvent(ApplicationEvent event);

}
