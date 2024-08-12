package courier;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {


    public static Courier getRandom() {
        return new Courier()
                .setLogin(RandomStringUtils.randomAlphanumeric(7))
                .setPassword(RandomStringUtils.randomAlphanumeric(7))
                .setFirstName(RandomStringUtils.randomAlphabetic(7));
    }

}
