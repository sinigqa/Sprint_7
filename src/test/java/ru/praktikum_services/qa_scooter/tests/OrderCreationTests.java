package ru.praktikum_services.qa_scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.model.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class OrderCreationTests extends BaseTest {

    protected Order order;
    private final List<String> colors;
    private String trackNumber;

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
    @Description("Cоздание заказов с разными комбинациями цветов (BLACK, GREY, BLACK and GREY, null)")
    public void testOrderCreationWithColorVariations() {
        order = generateRandomOrder().setColor(colors);
        trackNumber = orderSteps.createOrder(order)
                .statusCode(SC_CREATED)
                .body("track", notNullValue())
                .extract().path("track").toString();
    }

    @After
    public void cancelCreatedOrder() {

        if (trackNumber != null && !trackNumber.isEmpty()) {
            orderSteps.cancelOrder(trackNumber);

        }
    }
}
