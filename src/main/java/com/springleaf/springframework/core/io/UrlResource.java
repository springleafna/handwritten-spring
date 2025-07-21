package com.springleaf.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 从 URL 地址读取远程资源
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url) {
        Assert.notNull(url,"URL must not be null");
        this.url = url;
    }

    /**
     * 打开一个 URL 连接，获取该资源的输入流，用于读取远程资源内容。
     * 如果发生异常，主动断开 HTTP 连接并抛出异常。
     */
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        try {
            return con.getInputStream();
        }
        catch (IOException ex){
            // 如果当前连接是是 HttpURLConnection 类型，就关闭连接，避免资源泄露等问题
            if (con instanceof HttpURLConnection){
                ((HttpURLConnection) con).disconnect();
            }
            throw ex;
        }
    }
}
