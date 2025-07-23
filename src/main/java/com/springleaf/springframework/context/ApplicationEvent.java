package com.springleaf.springframework.context;

import java.util.EventObject;

/**
 * 定义事件的抽象类，所有的事件包括关闭、刷新，以及用户自己实现的事件，都需要继承这个类
 * 继承EventObject的主要原因：遵循Java标准事件模型、与Java生态系统兼容、获得标准的事件源管理
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
