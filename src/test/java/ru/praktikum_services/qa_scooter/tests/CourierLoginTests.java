package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.Courier;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

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
    public void testSuccessfulLogin() {
        courierSteps.loginCourier(validCourier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без логина")
    public void testLoginWithoutLogin() {
        Courier courierWithoutLogin = new Courier()
                .setLogin(null)
                .setPassword(originalPassword);

        courierSteps.loginCourier(courierWithoutLogin)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    public void testLoginWithoutPassword() {
        Courier courierWithoutPassword = new Courier()
                .setLogin(originalLogin)
                .setPassword(null);

        courierSteps.loginCourier(courierWithoutPassword)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    public void testLoginWithWrongLogin() {
        Courier courierWithWrongLogin = new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(10))
                .setPassword(originalPassword);

        courierSteps.loginCourier(courierWithWrongLogin)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    public void testLoginWithWrongPassword() {
        Courier courierWithWrongPassword = new Courier()
                .setLogin(originalLogin)
                .setPassword(RandomStringUtils.randomAlphanumeric(10));

        courierSteps.loginCourier(courierWithWrongPassword)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без логина и пароля")
    public void testLoginWithoutCredentials() {
        Courier emptyCourier = new Courier()
                .setLogin(null)
                .setPassword(null);

        courierSteps.loginCourier(emptyCourier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}