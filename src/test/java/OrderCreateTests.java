import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.Orders;
import orders.OrdersClient;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static orders.OrderGenerator.getRandom;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class OrderCreateTests {

    private OrdersClient ordersClient;
    private Orders orders;
    private List<String> colour;
    private int trackId;
    private ValidatableResponse createResponse;
    private int createStatusCode;

    @Before
    public void setup(){
        ordersClient = new OrdersClient();
    }

    @After
    public void tearDown() {
        if (createStatusCode == HttpStatus.SC_CREATED) {
            ordersClient.cancelOrders(trackId);
        }

    }


    public OrderCreateTests(List<String> colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderTest() {
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("GREY","BLACK")},
                {List.of()},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void OrderCreateTest(){
        orders = getRandom();
        orders.setColor(colour);
        createResponse = ordersClient.createOrders(orders);
        createStatusCode = createResponse.extract().statusCode();
        trackId = createResponse.extract().path("track");
        assertEquals("Статус код вернулся не 201 при создании заказа",
                HttpStatus.SC_CREATED, createStatusCode);
        assertNotEquals("Не вернулся trackId",
                null, trackId);

    }

}
