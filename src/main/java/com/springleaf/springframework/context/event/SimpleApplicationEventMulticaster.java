package com.springleaf.springframework.context.event;

import com.springleaf.springframework.beans.factory.BeanFactory;
import com.springleaf.springframework.context.ApplicationEvent;
import com.springleaf.springframework.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    /**
     * 该方法会调用事件对应的监听器，执行onApplicationEvent(event)事件监听方法
     * @param event 要广播的事件
     */
    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }

}
