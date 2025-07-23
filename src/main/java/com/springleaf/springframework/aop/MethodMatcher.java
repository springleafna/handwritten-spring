package com.springleaf.springframework.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配，找到表达式范围内匹配下的方法
 */
public interface MethodMatcher {

    /**
     * 判断某个方法是否匹配切点表达式
     */
    boolean matches(Method method, Class<?> targetClass);
    
}
