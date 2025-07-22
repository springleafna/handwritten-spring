package com.springleaf.springframework.context;

import com.springleaf.springframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     */
    void refresh() throws BeansException;

    /**
     * 注册 JVM 关闭钩子 -> 向 JVM 注册一个‘关闭时要执行的任务’
     * 告诉JVM：当我这个 Java 程序即将正常退出的时候，请先帮我执行一下 close() 方法，然后再真正关闭。
     * 也就是当 JVM 即将关闭时，启动一个新的线程来执行 close()
     */
    void registerShutdownHook();

    /**
     * 关闭容器，销毁所有单例 Bean
     */
    void close();
}
