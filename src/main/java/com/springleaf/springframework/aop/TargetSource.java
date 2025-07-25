package com.springleaf.springframework.aop;

import com.springleaf.springframework.util.ClassUtils;

/**
 * 被代理的目标对象
 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取目标对象实现的所有接口。
     * 举例：
     * 原始类：
     * class UserServiceImpl implements UserService, InitializingBean {
     *     // 实现代码
     * }
     * CGLIB代理后 -> 【代理类】 extends UserServiceImpl
     * 但我们需要获取UserServiceImpl实现的接口
     * 结果返回：[UserService.class, InitializingBean.class]
     */
    public Class<?>[] getTargetClass() {
        // 1. 获取目标对象的实际运行时类型
        Class<?> clazz = this.target.getClass();
        // 因为这个 target 可能是 JDK代理 创建也可能是 CGlib创建，所以需要进行判断
        // 2. 判断是否为CGLIB代理类，如果是则获取其父类（原始类）
        // CGLIB代理特点：代理类继承自目标类，所以需要获取父类才是原始类
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        // 3. 返回该类实现的所有接口
        return clazz.getInterfaces();
    }

    /**
     * 返回目标实例。在 AOP 框架调用 AOP 方法调用的“目标”之前立即调用。
     */
    public Object getTarget(){
        return this.target;
    }
    
}
