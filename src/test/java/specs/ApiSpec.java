package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;


public class ApiSpec {

        public static RequestSpecification requestSpec = with()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(JSON);

        public static ResponseSpecification getResponseSpecStatusCode(int statusCode) {
                return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(ALL)
                .build();
        }
}