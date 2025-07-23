package com.springleaf.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.PropertyValue;
import com.springleaf.springframework.beans.factory.config.BeanDefinition;
import com.springleaf.springframework.beans.factory.config.BeanReference;
import com.springleaf.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.springleaf.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.springleaf.springframework.core.io.Resource;
import com.springleaf.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream inputStream = resource.getInputStream()){
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    /**
     * 从输入流中读取 XML 配置文件，解析 <bean> 标签，并注册 BeanDefinition
     * @param inputStream XML 文件的输入流。
     * @throws ClassNotFoundException 因为会用到 Class.forName() 加载类，如果找不到类就会抛出此异常。
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        // 使用工具类 XmlUtil.readXML() 将输入流解析成一个 Document（DOM 树）
        // Document 是 Java DOM 解析器生成的 XML 文档对象。
        Document doc = XmlUtil.readXML(inputStream);
        // 获取 XML 的根节点（通常是 <beans>）
        Element root = doc.getDocumentElement();
        // 遍历根节点下的所有子节点
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            // 判断元素
            if (!(childNodes.item(i) instanceof Element)) continue;
            // 判断对象
            if (!"bean".equals(childNodes.item(i).getNodeName())) continue;

            // 解析 <bean> 标签的属性，从 <bean> 标签中提取 id、name、class 属性。
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethod = bean.getAttribute("init-method");
            String destroyMethod = bean.getAttribute("destroy-method");
            String beanScope = bean.getAttribute("scope");

            // 使用类名加载对应的类（用于后续创建 Bean 实例）。
            Class<?> clazz = Class.forName(className);
            // 如果 <bean> 有 id，优先使用 id 作为 Bean 名称。
            // 如果没有 id，使用 name。
            // 如果都没有，就使用类名首字母小写作为默认 Bean 名称（如 UserService → userService）。
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 创建一个 BeanDefinition 对象，封装 Bean 的定义信息（类、属性等）。
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethod);

            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            // 读取属性并填充
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) continue;
                // 解析 <property> 标签并填充属性值
                Element property = (Element) bean.getChildNodes().item(j);
                // 读取属性的 name、value、ref：
                // value：基本类型或字符串值。
                // ref：引用另一个 Bean，用 BeanReference 封装。
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");
                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建 PropertyValue 对象，添加到 BeanDefinition 中。
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            // 检查 Bean 名称是否重复
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册 BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }
}
