package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest extends BaseTest {

    @Test
    @DisplayName("Проверка, что в ответе возвращается список заказов")
    public void testResponseContainsOrdersList() {
        orderSteps.getOrdersList()
                .statusCode(200)
                .body("orders", notNullValue()); // Проверяем, что поле "orders" существует
    }
}
