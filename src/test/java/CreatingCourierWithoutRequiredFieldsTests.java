import courier.Courier;
import courier.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreatingCourierWithoutRequiredFieldsTests {

    private CourierClient courierClient;
    private final Courier courier;
    private int createStatusCode;
    private ValidatableResponse createResponse;

    @Before
    public void setUp() {
        courierClient = new CourierClient();

    }

    @After
    public void cleanUp() {

        if (createStatusCode == HttpStatus.SC_CREATED) {
            int courierId = createResponse.extract().path("id");
            courierClient.delete(courierId);
        }

    }

    public CreatingCourierWithoutRequiredFieldsTests(String login, String password, String firstName) {
        courier = new Courier()
                .setLogin(login)
                .setPassword(password)
                .setFirstName(firstName);

    }


    @Parameterized.Parameters
    public static Object[][] getCourierTest() {
        return new Object[][] {
                {"", RandomStringUtils.randomAlphanumeric(7), RandomStringUtils.randomAlphabetic(7)},
                {RandomStringUtils.randomAlphanumeric(7), "", RandomStringUtils.randomAlphabetic(7)},
                {"", "", RandomStringUtils.randomAlphabetic(7)}
        };

    }

    @Test
    @DisplayName("Создание курьера без логина и/или пароля")
    public void CreatingCourierWithoutRequiredFieldsTest() {
        createResponse = courierClient.create(courier);
        createStatusCode = createResponse.extract().statusCode();
        String message = createResponse.extract().path("message");
        assertEquals("Статус код вернулся не 400 при создании курьера",
                HttpStatus.SC_BAD_REQUEST, createStatusCode);
        assertEquals("Некорректное сообщение об ошибке",
                "Недостаточно данных для создания учетной записи", message);

    }

}
