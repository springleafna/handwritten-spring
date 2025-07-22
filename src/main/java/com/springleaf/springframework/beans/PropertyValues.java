package com.springleaf.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装一组待注入的属性值（如 XML 配置中的 <property name="xxx" value="yyy"/>），是多个 PropertyValue 的容器。
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

}
