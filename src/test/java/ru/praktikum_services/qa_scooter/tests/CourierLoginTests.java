package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.Courier;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class CourierLoginTests extends BaseTest {

    private Courier validCourier;
    private String originalPassword;
    private String originalLogin;

    @Before
    public void setUp() {

        validCourier = generateRandomCourier();
        originalPassword = validCourier.getPassword();
        originalLogin = validCourier.getLogin();

        courierSteps.createCourier(validCourier);
    }

    @Test
    @DisplayName("Успешная авторизация с валидными данными")
    @Description("Вход в систему с корректными логином и паролем")
    public void testSuccessfulLogin() {
        courierSteps.loginCourier(validCourier)
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Попытка входа без указания логина")
    public void testLoginWithoutLogin() {
        Courier courierWithoutLogin = new Courier()
                .setLogin(null)
                .setPassword(originalPassword);

        courierSteps.loginCourier(courierWithoutLogin)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Попытка входа без указания пароля")
    public void testLoginWithoutPassword() {
        Courier courierWithoutPassword = new Courier()
                .setLogin(originalLogin)
                .setPassword(null);

        courierSteps.loginCourier(courierWithoutPassword)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("Попытка входа с несуществующим логином")
    public void testLoginWithWrongLogin() {
        Courier courierWithWrongLogin = new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(10))
                .setPassword(originalPassword);

        courierSteps.loginCourier(courierWithWrongLogin)
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Попытка входа с некорректным паролем")
    public void testLoginWithWrongPassword() {
        Courier courierWithWrongPassword = new Courier()
                .setLogin(originalLogin)
                .setPassword(RandomStringUtils.randomAlphanumeric(10));

        courierSteps.loginCourier(courierWithWrongPassword)
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без логина и пароля")
    @Description("Попытка входа без указания учетных данных")
    public void testLoginWithoutCredentials() {
        Courier emptyCourier = new Courier()
                .setLogin(null)
                .setPassword(null);

        courierSteps.loginCourier(emptyCourier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}