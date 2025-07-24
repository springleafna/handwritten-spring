package com.springleaf.springframework.aop.framework.autoproxy;

import com.springleaf.springframework.aop.*;
import com.springleaf.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.springleaf.springframework.aop.framework.ProxyFactory;
import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.PropertyValues;
import com.springleaf.springframework.beans.factory.BeanFactory;
import com.springleaf.springframework.beans.factory.BeanFactoryAware;
import com.springleaf.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.springleaf.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * 融入Bean生命周期的自动代理创建者
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

        if (isInfrastructureClass(beanClass)) return null;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();

            if (!classFilter.matches(beanClass)) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 填充对应属性信息：目标对象、拦截方法、匹配器
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            // 返回代理对象
            return new ProxyFactory(advisedSupport).getProxy();
        }

        return null;
    }

    /**
     * 判断给定的类是否是基础设施类：
     * 即是否是 Advice、Pointcut、Advisor 的子类或实现类
     * @return 任意一个条件满足则返回 true，否则 false
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }
}
