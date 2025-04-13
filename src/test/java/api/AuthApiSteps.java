package api;

import io.qameta.allure.Step;
import models.AuthModel;
import models.LoginModel;

import static io.restassured.RestAssured.given;
import static specs.ApiSpec.getResponseSpecStatusCode;
import static specs.ApiSpec.requestSpec;
import static utils.TestData.*;

public class AuthApiSteps {

    @Step("API авторизация")
    public static AuthModel login(LoginModel loginModel) {

        return
                given(requestSpec)
                .body(loginModel)
                .when()
                .post(LOGIN_URL)
                .then()
                .spec(getResponseSpecStatusCode(200))
                .extract().as(AuthModel.class);
    }
}
