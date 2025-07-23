package com.springleaf.springframework.context.event;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.BeanFactory;
import com.springleaf.springframework.beans.factory.BeanFactoryAware;
import com.springleaf.springframework.context.ApplicationEvent;
import com.springleaf.springframework.context.ApplicationListener;
import com.springleaf.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    // 存放所有监听器的容器
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    /**
     * 监听器是否对该事件感兴趣：即判断监听器是否应该接收和处理该事件。
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        // 1. 获取监听器的Class类型
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 2. 处理代理类情况：按照 CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy 不同的实例化类型，需要判断后获取目标 class
        // 如果监听器是通过CGLIB代理创建的，需要获取原始类
        // 例如：
        // 代理类 ：UserService$$EnhancerBySpringCGLIB$$12345678
        // 原始类 ：UserService
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

        // 3. 获取泛型接口信息
        Type genericInterface = targetClass.getGenericInterfaces()[0];

        // 4. 提取泛型参数类型
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];

        // 5. 获取事件类的Class对象
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }

        // 6. 判断监听器声明的事件类型是否与实际事件类型兼容
        // 判定此 eventClassName 对象所表示的类或接口与指定的 event.getClass() 参数所表示的类或接口是否相同，或是否是其超类或超接口。
        // isAssignableFrom是用来判断子类和父类的关系的，或者接口的实现类和接口的关系的，默认所有的类的终极父类都是Object。如果A.isAssignableFrom(B)结果是true，证明B可以转换成为A,也就是A可以由B转换而来。
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
