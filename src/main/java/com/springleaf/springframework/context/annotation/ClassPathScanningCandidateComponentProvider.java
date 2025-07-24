package com.springleaf.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.springleaf.springframework.beans.factory.config.BeanDefinition;
import com.springleaf.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathScanningCandidateComponentProvider {

    /**
     * 扫描到所有 @Component 注解的 Bean 对象
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
