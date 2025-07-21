package com.springleaf.springframework.bean;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "小叶");
        hashMap.put("10002", "小王");
        hashMap.put("10003", "小新");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}
