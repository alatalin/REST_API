package utils;

import models.LoginModel;

public class TestData {

    public static final String USERNAME = System.getProperty("userName");
    public static final String PASSWORD = System.getProperty("userPassword");
    public static LoginModel loginModel = new LoginModel(USERNAME, PASSWORD);
    public static final String BASE_URL = "https://demoqa.com";
    public static final String LOGIN_URL = "/Account/v1/Login";
    public static final String PROFILE_URL = "/profile";
    public static final String BOOKS_URL = "/BookStore/v1/Books";

}
