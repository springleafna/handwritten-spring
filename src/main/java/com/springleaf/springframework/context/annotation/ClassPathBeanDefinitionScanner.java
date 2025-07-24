package com.springleaf.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.springleaf.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.springleaf.springframework.beans.factory.config.BeanDefinition;
import com.springleaf.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.springleaf.springframework.stereotype.Component;

import java.util.Set;

/**
 * 作用：通过包扫描 @Component、@Scope注解，配置 Bean 的作用域和类名
 * 扫描指定包 ：查找所有带有@Component注解的类
 * 解析作用域 ：读取@Scope注解确定Bean的作用域
 * 确定名称 ：从@Component注解或类名生成Bean名称
 * 注册Bean ：将Bean定义注册到Spring容器中
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析 Bean 的作用域 singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }

        // 注册处理注解的 BeanPostProcessor（@Autowired、@Value）
        registry.registerBeanDefinition("com.springleaf.springframework.context.annotation.internalAutowiredAnnotationProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) return scope.value();
        return StrUtil.EMPTY;
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
