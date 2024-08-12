package courier;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import restClient.RestClient;
import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {

    private static final String COURIER_PATH = "/api/v1/courier";
    public static final String COURIER_LOGIN = "/api/v1/courier/login";
    public static final String COURIER_DELETE = "/api/v1/courier/:id";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                       .spec(getBaseSpec())
                       .body(courier)
                       .when()
                       .post(COURIER_PATH)
                       .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_DELETE + courierId)
                .then();
    }

}
