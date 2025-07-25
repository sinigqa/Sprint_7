package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.model.Courier;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class CourierCreationTests extends BaseTest {

    private Courier courier;

    @Test
    @DisplayName("Курьер может быть создан")
    @Description("Создание курьера с валидными данными (логин, пароль, имя)")
    public void testCourierCanBeCreated(){
        courier = generateRandomCourier();

        courierSteps.createCourier(courier)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Курьер может быть создан без имени")
    @Description("Создание курьера без указания имени (необязательное поле)")
    public void testCourierCanBeCreatedWithoutFirstName(){
        courier = generateRandomCourier()
                .setFirstName(null);

        courierSteps.createCourier(courier)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Невозможно создать курьера без логина")
    @Description("Попытка создания курьера без обязательного поля 'логин'")
    public void testCourierCannotBeCreatedWithoutLogin(){
        courier = generateRandomCourier()
                .setLogin(null);

        courierSteps.createCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможно создать курьера без пароля")
    @Description("Попытка создания курьера без обязательного поля 'пароль'")
    public void testCourierCannotBeCreatedWithoutPassword(){
        courier = generateRandomCourier()
                .setPassword(null);

        courierSteps.createCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможно создать курьеров с одинаковым логином")
    @Description("Попытка создания дубликата курьера с существующим логином")
    public void testCannotCreateCourierWithExistingLogin(){
        courier = generateRandomCourier();
        courierSteps.createCourier(courier);

        Courier duplicateLogin = generateRandomCourier()
                .setLogin(courier.getLogin());

        courierSteps.createCourier(duplicateLogin)
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

}