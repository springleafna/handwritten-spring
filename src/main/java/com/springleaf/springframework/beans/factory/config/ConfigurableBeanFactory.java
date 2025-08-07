package com.springleaf.springframework.beans.factory.config;

import com.springleaf.springframework.beans.factory.HierarchicalBeanFactory;
import com.springleaf.springframework.core.convert.ConversionService;
import com.springleaf.springframework.util.StringValueResolver;
import org.jetbrains.annotations.Nullable;

/**
 * 配置接口，由大多数 bean 工厂实现。除了 bean 工厂之外，还提供用于配置 bean 工厂的工具
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    // 作用域范围：单例
    // 在整个Spring容器中只有一个实例，容器启动时创建，容器关闭时销毁
    String SCOPE_SINGLETON = "singleton";

    // 作用域范围：原型
    // 每个请求都创建新实例，由容器创建，但由使用者负责销毁(或JVM垃圾回收)
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();

    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    String resolveEmbeddedValue(String value);

    /**
     * Specify a Spring 3.0 ConversionService to use for converting
     * property values, as an alternative to JavaBeans PropertyEditors.
     * @since 3.0
     */
    void setConversionService(ConversionService conversionService);

    /**
     * Return the associated ConversionService, if any.
     * @since 3.0
     */
    @Nullable
    ConversionService getConversionService();
}
