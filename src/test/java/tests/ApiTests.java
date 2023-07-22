package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ApiTests extends TestBase {
    @Test
    void loginTest() {
        String requestBody = """
                {
                    "email": "eve.holt@reqres.in",
                    "password": "cityslicka"
                }"""; //BAD PRACTICE

        given()
                .log().method()
                .log().uri()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void getUserTest() {
        given()
                .log().method()
                .log().uri()
                .param("page", 1)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("data.id", hasItems(1, 2, 3));
    }

    @Test
    void userNotFoundTest() {
        given()
                .log().method()
                .log().uri()
                .param("page", 1)
                .when()
                .get("/users/23")
                .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    void deleteUserTest() {
        given()
                .log().method()
                .log().uri()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .statusCode(204);
    }

    @Test
    void createUserTest() {
        String requestBody = """
                {
                    "name": "morpheus",
                    "job": null
                }"""; //BAD PRACTICE
        given()
                .log().method()
                .log().uri()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", nullValue());
    }
}
