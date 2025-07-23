package com.springleaf.springframework.test.event;

import com.springleaf.springframework.context.ApplicationListener;
import com.springleaf.springframework.context.event.ContextRefreshedEvent;

/**
 * 监听动作在容器完成初始化刷新时执行
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }

}
