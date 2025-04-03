import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegisterTests {

    @Test
    void successfulRegisterTest() {
        String regData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().uri()

        .when()
                .post("https://reqres.in/api/register")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulEmptyPasswordTest() {
        String regData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"\"}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unsuccessfulEmptyEmailTest() {
        String regData = "{\"email\": \"\", \"password\": \"pistol\"}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void wrongBodyTest() {
        String regData = "{12$3%3543}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @Test
    void unsuccessfulNotDefinedUserTest() {
        String regData = "{\"email\": \"eve45.holt@reqres.in\", \"password\": \"pistol55\"}";

        given()
                .body(regData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }
}
