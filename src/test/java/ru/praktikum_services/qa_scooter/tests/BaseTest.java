package ru.praktikum_services.qa_scooter.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.http.ContentType;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.config.RestConfig;
import org.example.model.Courier;
import org.example.model.Order;
import org.example.steps.CourierSteps;
import org.example.steps.OrderSteps;
import org.junit.After;
import org.junit.Before;

import java.util.Random;


public class BaseTest {

    protected final CourierSteps courierSteps = new CourierSteps();
    protected final OrderSteps orderSteps = new OrderSteps();
    protected Courier courier;

    @Before
    public void startUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(RestConfig.HOST)
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.config = RestAssured
                .config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }

    protected Courier generateRandomCourier() {
        return new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphanumeric(12))
                .setFirstName(RandomStringUtils.randomAlphabetic(12));
    }

    protected Order generateRandomOrder() {
        return new Order()
                .setFirstName(RandomStringUtils.randomAlphabetic(10))
                .setLastName(RandomStringUtils.randomAlphabetic(10))
                .setAddress(RandomStringUtils.randomAlphanumeric(16))
                .setMetroStation(String.valueOf(new Random().nextInt(200) + 1))
                .setPhone("+7" + RandomStringUtils.randomNumeric(10))
                .setRentTime(new Random().nextInt(10) + 1)
                .setDeliveryDate(java.time.LocalDate.now().plusDays(3).toString())
                .setComment(RandomStringUtils.randomAlphanumeric(25));
    }
    @After
    public void tearDown() {
        if (courier != null && courier.getLogin() != null && courier.getPassword() != null) {
            Integer id = courierSteps.loginCourier(courier)
                    .extract().body().path("id");
            if (id != null) {
                courier.setId(id);
                courierSteps.deleteCourier(courier);
            }
        }
    }
}