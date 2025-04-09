package specs;

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

        public static ResponseSpecification responseSpecStatus200 = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(ALL)
                .build();

        public static ResponseSpecification responseSpecStatus204 = new ResponseSpecBuilder()
                .expectStatusCode(204)
                .log(ALL)
                .build();

        public static ResponseSpecification responseSpecStatus400 = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .log(ALL)
                .build();

        public static ResponseSpecification responseSpecStatus404 = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .log(ALL)
                .build();



}