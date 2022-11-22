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
import java.util.concurrent.atomic.AtomicReference;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.Specs.*;

@Owner("yuliaorlova")
@Feature("API тесты")
@DisplayName("API тесты для сайта Reqres.in")
public class ReqresInTests {

    @Test
    @Tags({@Tag("api"), @Tag("high")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Позитивная проверка регистрации пользователя")
    void checkRegisterSuccessful() {
        AtomicReference<RegLogResponseModel> response = new AtomicReference();

        step("Регистрация пользователя", () -> {
            RegLogBodyModel body = new RegLogBodyModel();
            body.setEmail("eve.holt@reqres.in");
            body.setPassword("pistol");

            response.set(given()
                .spec(requestSpecJson)
                .body(body)
                .when()
                .post("/api/register")
                .then()
                .spec(responseSpecToken)
                .statusCode(200)
                .extract()
                .as(RegLogResponseModel.class));
        });

        step("Проверка, что пользователь зарегистрирован", () -> {
            assertThat(response.get().getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
            assertThat(response.get().getId()).isEqualTo(4);
        });
    }

    @Test
    @Tags({@Tag("api"), @Tag("high")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Позитивная проверка авторизации пользователя")
    void checkLoginSuccessful() {
        AtomicReference<RegLogResponseModel> response = new AtomicReference();

        step("Авторизация пользователя", () -> {
            RegLogBodyModel body = new RegLogBodyModel();
            body.setEmail("eve.holt@reqres.in");
            body.setPassword("pistol");

            response.set(given()
                .spec(requestSpecJson)
                .body(body)
                .when()
                .post("/api/login")
                .then()
                .spec(responseSpecToken)
                .statusCode(200)
                .extract()
                .as(RegLogResponseModel.class));
        });
        step("Проверка, что пользователь авторизован", () ->
            assertThat(response.get().getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @Tags({@Tag("api"), @Tag("high")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка получения информации по пользователю")
    void checkGetSingleUserTest() {
        AtomicReference<SingleResponseModel> response = new AtomicReference();

        step("Получение данных пользователя", () -> {
            response.set(given()
                .spec(requestSpec)
                .when()
                .get("/api/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract()
                .as(SingleResponseModel.class));
        });
        step("Проверка, что данные пользователя корректные", () -> {
            assertThat(response.get().getData().getId()).isEqualTo(2);
            assertThat(response.get().getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(response.get().getData().getFirstName()).isEqualTo("Janet");
            assertThat(response.get().getData().getLastName()).isEqualTo("Weaver");
            assertThat(response.get().getData().getAvatar())
                    .isEqualTo("https://reqres.in/img/faces/2-image.jpg");
            assertThat(response.get().getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
            assertThat(response.get().getSupport().getText())
                    .isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
        });
    }

    @Test
    @Tags({@Tag("api"), @Tag("high")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест на создание имени и работы пользователя")
    void checkPostCreateTest() {
        AtomicReference<JobResponseModel> response = new AtomicReference();

        step("Создание имени и работы пользователя", () -> {
            JobBodyModel body = new JobBodyModel();
            body.setName("Cat");
            body.setJob("walk around the house");

            response.set(given()
                .spec(requestSpecJson)
                .body(body)
                .when()
                .post("/api/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract()
                .as(JobResponseModel.class));
        });
        step("Проверка, что имя и работа пользователя созданы", () -> {
            assertThat(response.get().getName()).isEqualTo("Cat");
            assertThat(response.get().getJob()).isEqualTo("walk around the house");
        });
    }

    @Test
    @Tags({@Tag("api"), @Tag("high")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Негативная проверка создания имени и работы пользователя")
    void checkNegativePostCreateTest() {
        step("Отправка некорректного запроса и проверка статуса кода в ответе", () -> {
            given()
                .spec(requestSpec)
                .post("/api/users")
                .then()
                .spec(responseSpec)
                .statusCode(415);
        });
    }

    @Test
    @Tags({@Tag("api"), @Tag("high")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест на редактирование имени и работы пользователя")
    void checkPutUpdateTest() {
        AtomicReference<JobResponseModel> response = new AtomicReference();

        step("Редактирование имени и работы пользователя", () -> {
            JobBodyModel body = new JobBodyModel();
            body.setName("Kitty");
            body.setJob("sleep all day");

            response.set(given()
                .spec(requestSpecJson)
                .body(body)
                .when()
                .put("/api/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract()
                .as(JobResponseModel.class));
        });
        step("Проверка, что имя и работа пользователя отредактированы", () -> {
            assertThat(response.get().getName()).isEqualTo("Kitty");
            assertThat(response.get().getJob()).isEqualTo("sleep all day");
        });
    }

    @Test
    @Tags({@Tag("api"), @Tag("high")})
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Тест на удаление пользователя")
    void checkDeleteTest() {
        step("Отправка запроса на удаление пользователя и проверка статуса кода в ответе", () -> {
            given()
                .spec(requestSpec)
                .when()
                .delete("/api/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(204);
        });
    }
}

