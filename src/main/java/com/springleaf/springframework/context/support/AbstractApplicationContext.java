package com.springleaf.springframework.context.support;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.springleaf.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.springleaf.springframework.beans.factory.config.BeanPostProcessor;
import com.springleaf.springframework.context.ConfigurableApplicationContext;
import com.springleaf.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory，并加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 5. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

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
        /*
          获取底层的 Bean 工厂（通常是 DefaultListableBeanFactory）
          调用其 destroySingletons() 方法 → 销毁所有注册过的可销毁 Bean
        */
        getBeanFactory().destroySingletons();
    }
}
