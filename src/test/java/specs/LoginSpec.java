package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class LoginSpec {
    public static RequestSpecification loginRequestSpec = with()
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON);
    public static ResponseSpecification loginResponseSpecStatusCodeIs200 = new ResponseSpecBuilder()
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
