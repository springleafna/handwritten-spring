package com.springleaf.springframework.context;

import com.springleaf.springframework.beans.factory.HierarchicalBeanFactory;
import com.springleaf.springframework.beans.factory.ListableBeanFactory;
import com.springleaf.springframework.core.io.ResourceLoader;

/**
 * 应用上下文
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
