package tests;

import models.RegistrationBodyModel;
import models.RegistrationResponseModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static specs.RegistrationSpec.registrationRequestSpec;
import static specs.RegistrationSpec.registrationResponseSpecStatusCodeIs200;

public class RegistrationApiTests extends TestBase {


    @Test
    void registrationTest() {
        RegistrationBodyModel regData = new RegistrationBodyModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");

        RegistrationResponseModel regResponse = step("Registration request", () ->
                given(registrationRequestSpec)
                        .body(regData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registrationResponseSpecStatusCodeIs200)
                        .extract().as(RegistrationResponseModel.class));
        // todo step("Check token in not null", () -> regResponse.getToken());
    }

    @Test
    void emailIsNullRegistrationTest() {
        RegistrationBodyModel regData = new RegistrationBodyModel();
        regData.setEmail(null);
        regData.setPassword("pistol");

        given()
                .contentType(JSON)
                .body(regData)
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void missingPasswordRegistrationTest() {
        RegistrationBodyModel regData = new RegistrationBodyModel();
        regData.setEmail("sydney@fife");
        regData.setPassword("pistol");

        given()
                .contentType(JSON)
                .body(regData)
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
