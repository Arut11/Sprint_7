import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourierCreateTests {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();

    }

    @After
    public void cleanUp() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код при авторизации курьера не 200",
                HttpStatus.SC_OK, loginStatusCode);
        int courierId = loginResponse.extract().path("id");
        assertNotEquals("Id курьера равен 0",
                0, courierId);
        courierClient.delete(courierId);

    }


    @Test
    @DisplayName("Проверка создания курьера в системе")
    public void courierCreatedTest() {
        ValidatableResponse createResponse = courierClient.create(courier);
        int createStatusCode = createResponse.extract().statusCode();
        assertEquals("Статус код вернулся не 201 при создании курьера",
                HttpStatus.SC_CREATED, createStatusCode);
        boolean created = createResponse.extract().path("ok");
        assertTrue("В теле ответа вернулось значение false",created);

    }

    @Test
    @DisplayName("Проверка создания двух одинаковых курьеров")
    public void CreationTwoIdenticalCouriersTest() {
        ValidatableResponse createResponse = courierClient.create(courier);
        int createStatusCode = createResponse.extract().statusCode();
        assertEquals("Статус код вернулся не 201 при создании курьера", HttpStatus.SC_CREATED ,
                createStatusCode);
        ValidatableResponse createSecondResponse = courierClient.create(courier);
        int createSecondStatusCode = createSecondResponse.extract().statusCode();
        assertEquals("Статус код вернулся не 409 при создании двух одинаковых курьеров",
                HttpStatus.SC_CONFLICT, createSecondStatusCode);
        String message = createSecondResponse.extract().path("message");
        assertEquals("Некорректное сообщение об ошибке",
                "Этот логин уже используется. Попробуйте другой.", message);

    }

}
