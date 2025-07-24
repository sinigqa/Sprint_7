package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.junit4.DisplayName;
import org.example.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTests extends BaseTest {

    protected Order order;
    private final List<String> colors;

    public OrderCreationTests(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Цвета: {0}")
    public static Object[][] getColorData() {
        return new Object[][]{
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {null}
        };
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    public void testOrderCreationWithColorVariations() {
        order = generateRandomOrder().setColor(colors);
        orderSteps.createOrder(order)
                .statusCode(201)
                .body("track", notNullValue());
    }
}