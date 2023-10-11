package com.example.finances;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
public class AuthIntegrationTests {

    private final String baseUrl = "http://localhost:8080/api/v1/auth";

    @Test
    public void registerShouldBe200WithValidCredentials() {

        Response res = given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"user\", \"password\": \"123\"}")
                .when()
                .post(baseUrl + "/register")
                .then()
                .extract().response();

        Assertions.assertEquals(200, res.statusCode());
    }

    @Test
    public void registerShouldBe403WithInvalidCredentials() {

        Response res = given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"user\", \"password\": \"123\"}")
                .when()
                .post(baseUrl + "/register")
                .then()
                .extract().response();

        Assertions.assertEquals(403, res.statusCode());
    }

    @Test
    public void loginShouldBe200WithValidCredentials() {

        Response res = given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"user\", \"password\": \"123\"}")
                .when()
                .post(baseUrl + "/login")
                .then()
                .extract().response();

        Assertions.assertEquals(200, res.statusCode());
    }

    @Test
    public void loginShouldBe403WithInvalidCredentials() {

        Response res = given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"invalid user\", \"password\": \"123\"}")
                .when()
                .post(baseUrl + "/login")
                .then()
                .extract().response();

        Assertions.assertEquals(403, res.statusCode());
    }
}