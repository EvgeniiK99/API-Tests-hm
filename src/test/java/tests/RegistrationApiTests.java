package tests;

import models.UnsuccessfulRegistrationResponseModel;
import models.RegistrationBodyModel;
import models.RegistrationResponseModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.RegistrationSpec.*;

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
        step("Check token in not null", () -> assertNotNull(regResponse.getToken()));
    }

    @Test
    void emailIsNullRegistrationTest() {
        RegistrationBodyModel regData = new RegistrationBodyModel();
        regData.setEmail(null);
        regData.setPassword("pistol");

        UnsuccessfulRegistrationResponseModel missingPasswordResponse = step("Registration request", () ->
                given(registrationRequestSpec)
                        .body(regData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registrationResponseSpecStatusIs400)
                        .extract().as(UnsuccessfulRegistrationResponseModel.class));
        step("Check message in error", ()-> assertEquals("Missing email or password", missingPasswordResponse.getError()));
    }
    @Test
    void missingPasswordRegistrationTest() {
        RegistrationBodyModel regData = new RegistrationBodyModel();
        regData.setEmail("sydney@fife");

        UnsuccessfulRegistrationResponseModel missingPasswordResponse = step("Registration request", () ->
                given(registrationRequestSpec)
                        .body(regData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registrationResponseSpecStatusIs400)
                        .extract().as(UnsuccessfulRegistrationResponseModel.class));
        step("Check message in error", ()-> assertEquals("Missing password", missingPasswordResponse.getError()));
    }

    @Test
    void unsupportedMediaTypeRegistrationTest() {
        given()
                .when()
                .post("/register")
                .then()
                .spec(registrationResponseSpecStatusIs415);
    }
}
