package orders;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import restClient.RestClient;
import static io.restassured.RestAssured.given;

public class OrdersClient extends RestClient {

    public static final String COURIER_ORDER_COUNT = "/api/v1/courier/:id/ordersCount";
    public static final String ORDERS_FINISH = "/api/v1/orders/finish/:id";
    public static final String ORDERS_CANCEL = "/api/v1/orders/cancel";
    public static final String ORDERS = "/api/v1/orders";
    public static final String ORDERS_TRACK = "/api/v1/orders/track";
    public static final String ORDERS_ACCEPT = "/api/v1/orders/accept/{id}";

    @Step("Создание заказа")
    public ValidatableResponse createOrders(Orders orders) {
        return given()
                .spec(getBaseSpec())
                .body(orders)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Отменить заказ")
    public ValidatableResponse cancelOrders(int trackId) {
        return given()
                .spec(getBaseSpec())
                .and()
                .when()
                .put(ORDERS_CANCEL + trackId)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrders(){
        return given()
                .spec(getBaseSpec())
                .get(ORDERS)
                .then();
    }

    @Step("Получить заказ по его номеру")
    public ValidatableResponse getTrack(String courierId){
        return given()
                .spec(getBaseSpec())
                .queryParam("t", courierId)
                .when()
                .get(ORDERS_TRACK)
                .then();
    }

    @Step("Принять заказ")
    public ValidatableResponse OrderAccept(String orderId, String courierId) {
        return given()
                .spec(getBaseSpec())
                .pathParam("id", Integer.parseInt(orderId))
                .queryParam("courierId", courierId)
                .when()
                .put(ORDERS_ACCEPT)
                .then();
    }

}
