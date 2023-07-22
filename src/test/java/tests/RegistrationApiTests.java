package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegistrationApiTests extends TestBase {
    @Test
    void registrationTest() {
        String requestBody = """
                {
                    "email": "eve.holt@reqres.in",
                    "password": "pistol"
                }
                """; //BAD PRACTICE

        given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", is(4));
    }

    @Test
    void emailIsNullRegistrationTest() {
        String requestBody = """
                {
                    "email": null,
                    "password": "pistol"
                }
                """; //BAD PRACTICE

        given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void missingPasswordRegistrationTest() {
        String requestBody = """
                {
                    "email": "sydney@fife"
                }
                """; //BAD PRACTICE

        given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unsupportedMediaTypeRegistrationTest() {
        given()
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(415);
    }
}
