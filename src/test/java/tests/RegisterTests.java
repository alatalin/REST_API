package tests;

import io.restassured.RestAssured;
import models.RegisterBodyModel;
import models.RegisterMissingElementModel;
import models.RegisterResponseModel;
import models.RegisterWrongBodyModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegisterSpec.*;

@Tag("api")
public class RegisterTests {

@BeforeAll
static void beforeAll() {
    RestAssured.baseURI = "https://reqres.in/";
}

    @Test
    void successfulRegisterLombokTest() {
        RegisterBodyModel regData = new RegisterBodyModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");

        RegisterResponseModel regResponse = step("Make request", ()->
          given(registerRequestSpec)
                .body(regData)

                .when()
                .post()

                .then()
                .spec(registerResponseSpec)
                .extract().as(RegisterResponseModel.class));

        step("Check response", ()->{
        assertEquals(4, regResponse.getId());
        assertEquals("QpwL5tke4Pnpja7X4", regResponse.getToken());
        });
    }

    @Test
    void unsuccessfulEmptyPasswordTest() {
        RegisterBodyModel regData = new RegisterBodyModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("");

        RegisterMissingElementModel regMissResponse = step("Make request", ()->
                given(registerRequestSpec)
                .body(regData)

                .when()
                .post()

                .then()
                .spec(missingElementResponseSpec)
                .extract().as(RegisterMissingElementModel.class));

        step("Check response", ()-> {
            assertEquals("Missing password", regMissResponse.getError());
        });
    }

    @Test
    void unsuccessfulEmptyEmailTest() {
        RegisterBodyModel regData = new RegisterBodyModel();
        regData.setEmail("");
        regData.setPassword("pistol");

        RegisterMissingElementModel regMissResponse = step("Make request", ()->
                given(registerRequestSpec)
                .body(regData)

                .when()
                .post()

                .then()
                .spec(missingElementResponseSpec)
                .extract().as(RegisterMissingElementModel.class));

        step("Check response", ()-> {
            assertEquals("Missing email or username", regMissResponse.getError());
        });
    }

    @Test
    void wrongBodyTest() {
        RegisterWrongBodyModel regData = new RegisterWrongBodyModel();
        regData.setData("{12$3%3543}");

        RegisterMissingElementModel regMissResponse = step("Make request", ()->
                given(registerRequestSpec)
                .body(regData)

                .when()
                .post()

                .then()
                .spec(missingElementResponseSpec)
                .extract().as(RegisterMissingElementModel.class));

        step("Check response", ()-> {
            assertEquals("Missing email or username", regMissResponse.getError());
        });
    }

    @Test
    void unsuccessfulNotDefinedUserTest() {

        RegisterBodyModel regData = new RegisterBodyModel();
        regData.setEmail("eve45.holt@reqres.in");
        regData.setPassword("pistol55");

        RegisterMissingElementModel regMissResponse = step("Make request", ()->
                given(registerRequestSpec)
                .body(regData)

                .when()
                .post()

                .then()
                .spec(missingElementResponseSpec)
                .extract().as(RegisterMissingElementModel.class));

        step("Check response", ()-> {
            assertEquals("Note: Only defined users succeed registration", regMissResponse.getError());
        });
    }
}
