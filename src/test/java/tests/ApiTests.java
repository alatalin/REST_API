package tests;

import io.restassured.RestAssured;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.ApiSpec.*;

@Tag("api")
public class ApiTests {

@BeforeAll
static void beforeAll() {
    RestAssured.baseURI = "https://reqres.in";
    RestAssured.basePath= "/api";
}

    @Test
    @DisplayName("Проверка успешной регистрации пользователя")
    void successfulRegisterTest() {
        RegisterBodyModel regData = new RegisterBodyModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");

        RegisterResponseModel regResponse = step("Отправляем запрос на регистрацию", ()->
          given(requestSpec)
                .body(regData)

                .when()
                .post("/register")

                .then()
                .spec(responseSpecStatus200)
                .extract().as(RegisterResponseModel.class));

        step("Проверяем ответ", ()->{
        assertThat(regResponse.getId()).isEqualTo(4);
        assertThat(regResponse.getToken()).isNotNull();
        });
    }

    @Test
    @DisplayName("Проверка регистрации пользователя без пароля")
    void unsuccessfulEmptyPasswordTest() {
        RegisterBodyModel regData = new RegisterBodyModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("");

        RegisterMissingElementModel regMissResponse = step("Отправляем запрос на регистрацию", ()->
                given(requestSpec)
                .body(regData)

                .when()
                .post("/register")

                .then()
                .spec(responseSpecStatus400)
                .extract().as(RegisterMissingElementModel.class));

        step("Проверяем ответ", ()-> {
            assertThat(regMissResponse.getError()).isEqualTo("Missing password");
        });
    }

    @Test
    @DisplayName("Проверка регистрации пользователя без email")
    void unsuccessfulEmptyEmailTest() {
        RegisterBodyModel regData = new RegisterBodyModel();
        regData.setEmail("");
        regData.setPassword("pistol");

        RegisterMissingElementModel regMissResponse = step("Отправляем запрос на регистрацию", ()->
                given(requestSpec)
                .body(regData)

                .when()
                .post("/register")

                .then()
                .spec(responseSpecStatus400)
                .extract().as(RegisterMissingElementModel.class));

        step("Проверяем ответ", ()-> {
            assertThat(regMissResponse.getError()).isEqualTo("Missing email or username");
        });
    }

    @Test
    @DisplayName("Проверка регистрации пользователя c некорректным body")
    void wrongBodyTest() {
        RegisterWrongBodyModel regData = new RegisterWrongBodyModel();
        regData.setData("{12$3%3543}");

        RegisterMissingElementModel regMissResponse = step("Отправляем запрос на регистрацию", ()->
                given(requestSpec)
                .body(regData)

                .when()
                .post("/register")

                .then()
                .spec(responseSpecStatus400)
                .extract().as(RegisterMissingElementModel.class));

        step("Проверяем ответ", ()-> {
            assertThat(regMissResponse.getError()).isEqualTo("Missing email or username");
        });
    }

    @Test
    @DisplayName("Проверка регистрации случайного пользователя")
    void unsuccessfulNotDefinedUserTest() {

        RegisterBodyModel regData = new RegisterBodyModel();
        regData.setEmail("eve45.holt@reqres.in");
        regData.setPassword("pistol55");

        RegisterMissingElementModel regMissResponse = step("Отправляем запрос на регистрацию", ()->
                given(requestSpec)
                .body(regData)

                .when()
                .post("/register")

                .then()
                .spec(responseSpecStatus400)
                .extract().as(RegisterMissingElementModel.class));

        step("Проверяем ответ", ()-> {
            assertThat(regMissResponse.getError()).isEqualTo("Note: Only defined users succeed registration");
        });
    }

    @Test
    @DisplayName("Проверка наличия пользователя")
    void successfulSingleUserHaveTest() {
        SingleUserBodyModel singleUserResponse = step("Отправляем запрос на проверку пользователя", () ->
                given(requestSpec)

                .get("/users/2")

                .then()
                .spec(responseSpecStatus200)
                .extract().as(SingleUserBodyModel.class));

        step("Проверяем ответ", () -> {
            assertThat(singleUserResponse.getData().getId()).isEqualTo(2);
            assertThat(singleUserResponse.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(singleUserResponse.getData().getFirst_name()).isEqualTo("Janet");
            assertThat(singleUserResponse.getData().getLast_name()).isEqualTo("Weaver");
            assertThat(singleUserResponse.getData().getAvatar()).isNotNull();
            assertThat(singleUserResponse.getSupport().getUrl()).isNotNull();
            assertThat(singleUserResponse.getSupport().getText()).isNotNull();
        });
    }

    @Test
    @DisplayName("Проверка отсутствия пользователя")
    void unsuccessfulSingleUserHaveTest() {
        String respose = step("Отправляем запрос на проверку пользователя", () ->
                given(requestSpec)

                .get("/users/111")

                .then()
                .spec(responseSpecStatus404)
                .extract().asString());
        assertThat(respose).isEqualTo("{}");
    }

    @Test
    @DisplayName("Проверка удаления пользователя")
    void unSingleUsersHaveTest() {
        String respose = step("Отправляем запрос на удаление пользователя", () ->
                given(requestSpec)

                .delete("/users/123")

                .then()
                .spec(responseSpecStatus204)
                .extract().asString());
        assertThat(respose).isEqualTo("");
    }
}
