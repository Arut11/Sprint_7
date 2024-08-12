import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginCourierTests {

    private CourierClient courierClient;
    private Courier courier;
    private int createStatusCode;
    private ValidatableResponse createResponse;
    private int courierId;
    private String message;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        createResponse = courierClient.create(courier);
        createStatusCode = createResponse.extract().statusCode();

    }

    @After
    public void cleanUp() {
        if (createStatusCode == HttpStatus.SC_CREATED) {
            courierClient.delete(courierId);
        }

    }

    @Test
    @DisplayName("Проверка авторизации курьера в системе")
    public void LoginCourierTest() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код при авторизации курьера не 200",
                HttpStatus.SC_OK, loginStatusCode);
        courierId = loginResponse.extract().path("id");
        assertNotEquals("Id курьера равен 0",
                0, courierId);

    }

    @Test
    @DisplayName("Проверка авторизации курьера в системе без пароля")
    public void CourierLoginWithoutPasswordTest() {
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), null);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код при авторизации курьера без пароля не 400",
                HttpStatus.SC_BAD_REQUEST, loginStatusCode);
        message = loginResponse.extract().path("message");
        assertNotEquals("Некорректное сообщение об ошибке",
                " Недостаточно данных для входа", message);

    }

    @Test
    @DisplayName("Проверка авторизации курьера в системе без логина")
    public void CourierLoginWithoutLoginTest() {
        CourierCredentials courierCredentials = new CourierCredentials(null, courier.getPassword());
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код при авторизации курьера без логина не 400",
                HttpStatus.SC_BAD_REQUEST, loginStatusCode);
        message = loginResponse.extract().path("message");
        assertNotEquals("Некорректное сообщение об ошибке",
                " Недостаточно данных для входа", message);

    }

    @Test
    @DisplayName("Проверка авторизации курьера в системе без логина и пароля")
    public void CourierLoginWithoutLoginAndPasswordTest() {
        CourierCredentials courierCredentials = new CourierCredentials(null, null);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код при авторизации курьера без логина и пароля не 400",
                HttpStatus.SC_BAD_REQUEST, loginStatusCode);
        message = loginResponse.extract().path("message");
        assertNotEquals("Некорректное сообщение об ошибке",
                " Недостаточно данных для входа", message);

    }

    @Test
    @DisplayName("Проверка авторизации с несуществующим логином курьера в системе")
    public void LoginNonExistentLoginCourierTest() {
        CourierCredentials courierCredentials = new CourierCredentials(RandomStringUtils.randomAlphanumeric(7), courier.getPassword());
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код при авторизации курьера с несуществующим логином не 404",
                HttpStatus.SC_NOT_FOUND, loginStatusCode);

    }

    @Test
    @DisplayName("Проверка авторизации с несуществующим паролем курьера в системе")
    public void LoginNonExistentPasswordCourierTest() {
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), RandomStringUtils.randomAlphanumeric(7));
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код при авторизации курьера с несуществующим паролем не 404",
                HttpStatus.SC_NOT_FOUND, loginStatusCode);

    }

    @Test
    @DisplayName("Проверка авторизации с несуществующим паролем и логином курьера в системе")
    public void LoginNonExistentCourierTest() {
        CourierCredentials courierCredentials = new CourierCredentials(RandomStringUtils.randomAlphanumeric(7), RandomStringUtils.randomAlphanumeric(7));
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код при авторизации курьера с несуществующим паролем не 404",
                HttpStatus.SC_NOT_FOUND, loginStatusCode);

    }

}
