package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.example.model.Courier;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CourierCreationTests extends BaseTest {

    private Courier courier;

    @Test
    @DisplayName("Курьер может быть создан")
    public void testCourierCanBeCreated(){
        courier = generateRandomCourier();

        courierSteps.createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Курьер может быть создан без имени")
    public void testCourierCanBeCreatedWithoutFirstName(){
        courier = generateRandomCourier()
                .setFirstName(null);

        courierSteps.createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Невозможно создать курьера без логина")
    public void testCourierCannotBeCreatedWithoutLogin(){
        courier = generateRandomCourier()
                .setLogin(null);

        courierSteps.createCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможно создать курьера без пароля")
    public void testCourierCannotBeCreatedWithoutPassword(){
        courier = generateRandomCourier()
                .setPassword(null);

        courierSteps.createCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможно создать курьеров с одинаковым логином")
    public void testCannotCreateCourierWithExistingLogin(){
        courier = generateRandomCourier();
        courierSteps.createCourier(courier);

        Courier duplicateLogin = generateRandomCourier()
                .setLogin(courier.getLogin());

        courierSteps.createCourier(duplicateLogin)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

}