
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.OrdersClient;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OrderListTests {

    private OrdersClient ordersClient;
    private ValidatableResponse createResponseList;
    private int createStatusCode;
    List<Map<String, Object>> listOrders;

    @Before
    public void setup(){
        ordersClient = new OrdersClient();

    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getListOrdersTest(){
        createResponseList = ordersClient.getOrders();
        createStatusCode = createResponseList.extract().statusCode();
        listOrders = createResponseList.extract().path("orders");

        assertEquals("Статус код неверный при получении списка заказов",
                HttpStatus.SC_OK, createStatusCode);
        assertNotEquals("Список заказов пустой",
               null, listOrders);

    }

}
