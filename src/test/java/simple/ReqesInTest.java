package simple;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

@Tag("simple")
public class ReqesInTest {

      /*
        1. Make Post request to https://reqres.in/api/login
        with body { "email": "eve.holt@reqres.in", "password": "cityslicka" }
        2.  Get response { "token": "QpwL5tke4Pnpja7X4"}
        3. Check total is 20
     */

    @Test
    void loginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    void unSupportedMediaTypeTest() {
        given()
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }

    @Test
    void missingUserNameTest() {
        given()
                .log().uri()
                .body("123")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void missingPasswordTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\"}";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
