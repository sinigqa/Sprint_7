package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.config.Endpoints;
import org.example.model.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .body(order)
                .when()
                .post(Endpoints.Order.CREATE)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList() {
        return given()
                .when()
                .get(Endpoints.Order.LIST)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(String trackId) {
        return given()
                .body("{\"track\": \"" + trackId + "\"}")
                .when()
                .put(Endpoints.Order.CANCEL)
                .then();
    }
}

