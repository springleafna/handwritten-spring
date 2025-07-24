package com.springleaf.springframework.test;

import com.springleaf.springframework.beans.BeansException;
import com.springleaf.springframework.beans.factory.config.BeanPostProcessor;
import com.springleaf.springframework.context.support.ClassPathXmlApplicationContext;
import com.springleaf.springframework.test.bean.IUserService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ApiTest {
    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

}
