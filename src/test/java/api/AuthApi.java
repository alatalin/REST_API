package api;

import io.qameta.allure.Step;
import models.AuthModel;
import models.LoginModel;


import static io.restassured.RestAssured.given;
import static specs.ApiSpec.requestSpec;
import static specs.ApiSpec.responseSpecStatus200;
import static utils.TestData.*;

public class AuthApi {

    @Step("API авторизация")
    public AuthModel login() {

        LoginModel loginModel = new LoginModel();
        loginModel.setUserName(USERNAME);
        loginModel.setPassword(PASSWORD);

        return
                given(requestSpec)
                .body(loginModel)
                .when()
                .post(LOGIN_URL)
                .then()
                .spec(responseSpecStatus200)
                .extract().as(AuthModel.class);
    }
}
