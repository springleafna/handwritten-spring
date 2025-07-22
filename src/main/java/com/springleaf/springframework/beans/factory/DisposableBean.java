package com.springleaf.springframework.beans.factory;

/**
 * 销毁 Bean 接口
 */
public interface DisposableBean {

    /**
     * 当容器关闭时，释放 Bean 占用的资源，如关闭连接、停止线程、清理缓存等。
     */
    void destroy() throws Exception;

}
