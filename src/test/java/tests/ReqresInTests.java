package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.Specs.*;

@Owner("yuliaorlova")
@Feature("API тесты")
@DisplayName("API тесты для сайта Reqres.in")
@Tags({@Tag("api"), @Tag("high"), @Tag("critical")})
@Severity(SeverityLevel.CRITICAL)
public class ReqresInTests {

    @Test
    @DisplayName("Позитивная проверка регистрации пользователя")
    void checkRegisterSuccessful() {
        RegLogBodyModel requestBody = RegLogBodyModel.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        RegLogResponseModel responseModel = step("Регистрация пользователя", () ->
                given().spec(requestSpecJson)
                        .body(requestBody)
                        .when()
                        .post("/api/register")
                        .then()
                        .spec(responseSpecToken)
                        .statusCode(200)
                        .extract()
                        .as(RegLogResponseModel.class));

        step("Проверка, что пользователь зарегистрирован", () -> {
            assertThat(responseModel.getToken()).isNotNull();
            assertThat(responseModel.getId()).isNotNull();
        });
    }

    @Test
    @DisplayName("Позитивная проверка авторизации пользователя")
    void checkLoginSuccessful() {
        RegLogBodyModel requestBody = RegLogBodyModel.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        RegLogResponseModel responseModel = step("Авторизация пользователя", () ->
                given().spec(requestSpecJson)
                        .body(requestBody)
                        .when()
                        .post("/api/login")
                        .then()
                        .spec(responseSpecToken)
                        .statusCode(200)
                        .extract()
                        .as(RegLogResponseModel.class));

        step("Проверка, что пользователь авторизован", () ->
                assertThat(responseModel.getToken()).isNotNull());
    }

    @Test
    @DisplayName("Проверка получения информации по пользователю")
    void checkGetSingleUserTest() {
        SingleResponseModel responseModel = step("Получение данных пользователя", () ->
                given().spec(requestSpecJson)
                        .when()
                        .get("/api/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract()
                        .as(SingleResponseModel.class));

        step("Проверка, что данные пользователя корректные", () -> {
            assertThat(responseModel.getData().getId()).isEqualTo(2);
            assertThat(responseModel.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(responseModel.getData().getFirstName()).isEqualTo("Janet");
            assertThat(responseModel.getData().getLastName()).isEqualTo("Weaver");
            assertThat(responseModel.getData().getAvatar())
                    .isEqualTo("https://reqres.in/img/faces/2-image.jpg");
            assertThat(responseModel.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
            assertThat(responseModel.getSupport().getText())
                    .isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
        });
    }

    @Test
    @DisplayName("Тест на создание имени и работы пользователя")
    void checkPostCreateTest() {
        JobBodyModel requestBody = JobBodyModel.builder()
                .name("Cat")
                .job("walk around the house")
                .build();

        JobResponseModel responseModel = step("Создание имени и работы пользователя", () ->
                given().spec(requestSpecJson)
                        .body(requestBody)
                        .when()
                        .post("/api/users")
                        .then()
                        .spec(responseSpec)
                        .statusCode(201)
                        .extract()
                        .as(JobResponseModel.class));

        step("Проверка, что имя и работа пользователя созданы", () -> {
            assertThat(responseModel.getName()).isEqualTo("Cat");
            assertThat(responseModel.getJob()).isEqualTo("walk around the house");
        });
    }

    @Test
    @DisplayName("Тест на редактирование имени и работы пользователя")
    void checkPutUpdateTest() {
        JobBodyModel requestBody = JobBodyModel.builder()
                .name("Kitty")
                .job("sleep all day")
                .build();

        JobResponseModel responseModel = step("Редактирование имени и работы пользователя", () ->
                given().spec(requestSpecJson)
                        .body(requestBody)
                        .when()
                        .put("/api/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract()
                        .as(JobResponseModel.class));

        step("Проверка, что имя и работа пользователя отредактированы", () -> {
            assertThat(responseModel.getName()).isEqualTo("Kitty");
            assertThat(responseModel.getJob()).isEqualTo("sleep all day");
        });
    }

    @Test
    @DisplayName("Тест на удаление пользователя")
    void checkDeleteTest() {
        step("Отправка запроса на удаление пользователя и проверка статуса кода в ответе", () ->
                given().spec(requestSpecJson)
                        .when()
                        .delete("/api/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(204));
    }
}

