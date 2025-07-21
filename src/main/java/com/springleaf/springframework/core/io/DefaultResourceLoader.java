package com.springleaf.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 根据传入的路径字符串，返回相应类型的资源对象。
 */
public class DefaultResourceLoader implements ResourceLoader {
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        // 检查给定的路径是否以特定的类路径前缀开头
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            // 从location中去掉前缀部分，并使用剩余部分创建一个ClassPathResource对象
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        else {
            // 尝试将路径解析为一个URL
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                // 如果解析失败，则认为路径是一个文件系统路径，并创建一个FileSystemResource对象
                return new FileSystemResource(location);
            }
        }
    }
}
