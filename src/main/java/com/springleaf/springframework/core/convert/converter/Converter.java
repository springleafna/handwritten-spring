package com.springleaf.springframework.core.convert.converter;

/**
 * 类型转换处理接口
 */
public interface Converter<S, T>  {

    /** 将类型为 S 的源对象转换为目标类型 T. */
    T convert(S source);

}
