package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RegistrationSpec {
    public static RequestSpecification registrationRequestSpec = with()
            .log().uri()
            .log().method()
            .log().body()
            .filter(withCustomTemplates())
            .contentType(JSON);

    public static RequestSpecification registrationRequestWithoutBodySpec = with()
            .log().uri()
            .log().method()
            .log().body()
            .filter(withCustomTemplates());
    public static ResponseSpecification registrationResponseSpecStatusCodeIs200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("success-registration-response-schema.json"))
            .build();

    public static ResponseSpecification registrationResponseSpecStatusIs400 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath("error-registration-response-schema.json"))
            .build();

    public static ResponseSpecification registrationResponseSpecStatusIs415 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(415)
            .build();
}
