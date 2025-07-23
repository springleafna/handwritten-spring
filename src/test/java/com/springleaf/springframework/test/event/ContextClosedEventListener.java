package com.springleaf.springframework.test.event;

import com.springleaf.springframework.context.ApplicationListener;
import com.springleaf.springframework.context.event.ContextClosedEvent;

/**
 * 监听动作在容器关闭时执行
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }

}
