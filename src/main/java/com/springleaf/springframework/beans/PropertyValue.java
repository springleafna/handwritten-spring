package com.springleaf.springframework.beans;

/**
 * 封装一个bean的某个属性值（如 XML 配置中的 <property name="xxx" value="yyy"/>）
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
