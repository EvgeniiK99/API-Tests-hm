package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.STATUS;

public class DeleteUserSpec {
    public static RequestSpecification deleteUserRequestSpec = with()
            .log().uri()
            .log().method();
    public static ResponseSpecification deleteUserSpecStatusCodeIs201 = new ResponseSpecBuilder()
            .log(STATUS)
            .expectStatusCode(204)
            .build();
}
