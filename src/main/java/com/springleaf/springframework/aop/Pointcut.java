package com.springleaf.springframework.aop;

/**
 * 切入点接口
 * 定义用于获取 ClassFilter、MethodMatcher 的两个类，这两个接口获取都是切点表达式提供的内容。
 */
public interface Pointcut {

    /**
     * 返回此切入点的类筛选器
     */
    ClassFilter getClassFilter();

    /**
     * 返回此切入点的方法匹配器
     */
    MethodMatcher getMethodMatcher();
}
