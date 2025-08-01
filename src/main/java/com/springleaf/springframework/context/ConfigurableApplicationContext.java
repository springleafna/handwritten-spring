package com.springleaf.springframework.context;

import com.springleaf.springframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     * refresh() 方法的主要作用包括：
     * 1. 加载或刷新配置元数据（如 XML、注解、Java 配置类等）。
     * 2. 解析 Bean 定义（BeanDefinition）并注册到容器中。
     * 3. 实例化非懒加载的单例 Bean。
     * 4. 调用各种生命周期回调和后处理器（如 BeanPostProcessor、BeanFactoryPostProcessor）。
     * 5. 准备上下文环境，使其进入可用状态。
     * 调用 refresh() 后，Spring 容器就完成了初始化，可以正常使用 getBean() 获取 Bean 实例。
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
