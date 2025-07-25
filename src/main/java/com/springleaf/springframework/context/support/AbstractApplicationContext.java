package com.springleaf.springframework.context.support;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.springleaf.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.springleaf.springframework.beans.factory.config.BeanPostProcessor;
import com.springleaf.springframework.context.ApplicationEvent;
import com.springleaf.springframework.context.ApplicationListener;
import com.springleaf.springframework.context.ConfigurableApplicationContext;
import com.springleaf.springframework.context.event.ApplicationEventMulticaster;
import com.springleaf.springframework.context.event.ContextClosedEvent;
import com.springleaf.springframework.context.event.ContextRefreshedEvent;
import com.springleaf.springframework.context.event.SimpleApplicationEventMulticaster;
import com.springleaf.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory，并从配置文件中加载 所有的BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. 在 Bean 实例化之前注册 BeanPostProcessor
        registerBeanPostProcessors(beanFactory);

        // 6. 初始化事件发布者
        // 即实例化 SimpleApplicationEventMulticaster，这样上下文就具备了 SimpleApplicationEventMulticaster 中定义的事件发布功能，
        // 并将实例化 Bean 对象 applicationEventMulticaster 存入单例 Bean 对象容器 singletonObjects，以供其他方法使用
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        // 获取所有实现了ApplicationListener接口的监听器类对象，例如CustomEventListener，
        // 调用applicationEventMulticaster.addApplicationListener(listener)将监听器Bean对象存入监听器池子中
        registerListeners();

        // 8. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 9. 发布【容器刷新完成】事件
        finishRefresh();
    }

    /**
     * 创建 BeanFactory，并加载配置文件注册 BeanDefinition
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 获取创建好的 DefaultListableBeanFactory
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }


    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    /**
     * 注册钩子方法，关闭容器
     * Shutdown Hook：是 JVM 提供的一种机制：在 JVM 正常关闭前（如 Ctrl+C、System.exit），自动执行一段代码。
     * 常用于清理资源：关闭文件、断开数据库连接、停止线程池等。
     * 只有在以下情况才会触发：
     * 正常终止（调用 System.exit()）
     * 用户中断（Ctrl+C）
     * 系统关闭信号（kill 命令）
     */
    @Override
    public void registerShutdownHook() {
        /*
          Runtime.getRuntime()：获取当前 Java 虚拟机运行时环境
          .addShutdownHook(...)：向 JVM 注册一个“关闭钩子”线程
          new Thread(this::close)：创建一个新线程，任务是执行 this.close() 方法
          this::close：方法引用，等价于 () -> close()
         */
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        /*
          获取底层的 Bean 工厂（通常是 DefaultListableBeanFactory）
          调用其 destroySingletons() 方法 → 销毁所有注册过的可销毁 Bean
        */
        getBeanFactory().destroySingletons();
    }
}
