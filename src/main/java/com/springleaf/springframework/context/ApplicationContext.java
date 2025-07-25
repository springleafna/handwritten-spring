package com.springleaf.springframework.context;

import com.springleaf.springframework.beans.factory.HierarchicalBeanFactory;
import com.springleaf.springframework.beans.factory.ListableBeanFactory;
import com.springleaf.springframework.core.io.ResourceLoader;

/**
 * 应用上下文
 * 可以立即为：ApplicationContext = Spring容器完整运行时环境的封装
 * 它封装了：
 * 1. 完整的启动流程
 * 2. Bean生命周期管理
 * 3. 配置资源加载
 * 4. 企业级特性集成
 * 5. 扩展机制支持
 * 如果说BeanFactory是Spring的心脏，那么ApplicationContext就是完整的身躯。
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
