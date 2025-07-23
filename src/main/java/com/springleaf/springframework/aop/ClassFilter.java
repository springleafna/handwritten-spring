package com.springleaf.springframework.aop;

/**
 * 类匹配，找到表达式范围内匹配下的类
 */
public interface ClassFilter {

    /**
     * 判断该类中是否有连接点可能匹配此切点表达式
     */
    boolean matches(Class<?> clazz);

}
