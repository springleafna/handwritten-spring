package com.springleaf.springframework.test.bean;

public class UserService {

    private String userId;
    private String company;
    private String location;
    private IUserDao userDao;

    public String queryUserInfo() {
        return "userName:" + userDao.queryUserName(userId) + ",company:" + company + ",location:" + location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

}
