package com.springleaf.springframework.test;

import com.springleaf.springframework.context.support.ClassPathXmlApplicationContext;
import com.springleaf.springframework.test.event.CustomEvent;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));

        applicationContext.registerShutdownHook();
    }
}
