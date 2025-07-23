package com.springleaf.springframework.beans.factory.support;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.DisposableBean;
import com.springleaf.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 用来判断是否为空对象的标记
     */
    protected static final Object NULL_OBJECT = new Object();

    /**
     * 保存单例Bean的容器
     * key：beanName
     * value：单例对象
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 为了在容器关闭时，能够自动调用 Bean 的销毁方法（如释放数据库连接、关闭线程池等），防止资源泄漏。
     * 所以需要注册实现了 DisposableBean 接口的 Bean 对象
     * 使用的是 LinkedHashMap ，它是有序的 Map ，保持插入顺序，以便按照逆序进行销毁。
     * 当调用 registerDisposableBean(...) 时，Bean 按创建顺序被加入这个 Map。
     */
    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    /**
     * 按照 Bean 注册/创建的先后顺序的逆序 来销毁 --- 后创建的先销毁
     * 原因：
     * 后创建的 Bean 往往依赖先创建的 Bean，所以应该先销毁自己，再销毁被依赖的对象。
     * 否则可能会出现：
     * A 依赖 B
     * 先销毁了 B
     * 再销毁 A 时，A 想清理资源却发现 B 已经没了 → 报错或异常
     */
    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
