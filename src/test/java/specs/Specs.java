package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.notNullValue;

public class Specs {
    public static RequestSpecification requestSpecJson = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON);

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .baseUri("https://reqres.in")
            .log().uri()
            .log().body();

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static ResponseSpecification responseSpecToken = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .expectBody("token", notNullValue())
            .build();
}
