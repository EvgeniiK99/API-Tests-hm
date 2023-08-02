package tests;

import models.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.CreateSpec.createRequestSpec;
import static specs.CreateSpec.createResponseSpecStatusCodeIs201;
import static specs.DeleteUserSpec.deleteUserRequestSpec;
import static specs.DeleteUserSpec.deleteUserSpecStatusCodeIs201;
import static specs.GetUserSpec.*;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpecStatusCodeIs200;

public class ApiTests extends TestBase {
    @Test
    void loginTest() {
        LoginBodyModel reqBody = new LoginBodyModel();
        reqBody.setEmail("eve.holt@reqres.in");
        reqBody.setPassword("cityslicka");

        LoginResponseModel loginResponse = step("Login request", () ->
                given(loginRequestSpec)
                        .body(reqBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(loginResponseSpecStatusCodeIs200)
                        .extract().as(LoginResponseModel.class));
        step("Check token is not null", () -> assertNotNull(loginResponse.getToken()));
    }

    @Test
    void getUserTest() {
        Integer userId = 2;
        GetUserDataResponseModel getUserResponse = step("Get User request", () ->
                        given(getUserRequestSpec)
                                .param("page", 1)
                                .when()
                                .get(format("/users/%s", userId))
                                .then()
                                .spec(getUserResponseSpecStatusCodeIs200)
                                .extract().as(GetUserDataResponseModel.class));
        step("Check id", () -> assertEquals(userId, getUserResponse.getData().getId()));
    }

    @Test
    void userNotFoundTest() {
        step("Get User request - 404 User not found", () -> given(getUserRequestSpec)
                .param("page", 1)
                .when()
                .get("/users/23")
                .then()
                .spec(getUserResponseSpecStatusCodeIs404));
    }

    @Test
    void deleteUserTest() {
        step("Delete User", () ->
        given(deleteUserRequestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(deleteUserSpecStatusCodeIs201));
    }

    @Test
    void createUserTest() {
        CreateUserBodyModel regBody = new CreateUserBodyModel();
        regBody.setName("morpheus");
        regBody.setJob("leader");

        CreateUserResponseModel createResponse = step("Create request", () ->
                given(createRequestSpec)
                        .body(regBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpecStatusCodeIs201)
                        .extract().as(CreateUserResponseModel.class));

        step("Check name and job in response", () -> {
            assertEquals(regBody.getName(), createResponse.getName());
            assertEquals(regBody.getJob(), createResponse.getJob());
        });

    }
}
