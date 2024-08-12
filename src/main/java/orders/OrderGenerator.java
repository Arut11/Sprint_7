package orders;
import org.apache.commons.lang3.RandomStringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class OrderGenerator {

    public static int getRandomRentTime() {
        Random random = new Random();
        return random.nextInt(30) + 1; // Генерирует случайное число от 1 до 30
    }

    public static String getRandomDeliveryDate() {
        Random random = new Random();
        LocalDate today = LocalDate.now();
        LocalDate deliveryDate = today.plusDays(random.nextInt(30) + 1); // Добавляет от 1 до 30 дней
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Формат даты
        return deliveryDate.format(formatter);
    }

    public static Orders getRandom() {
        return new Orders()
                .setFirstName(RandomStringUtils.randomAlphanumeric(7))
                .setLastName(RandomStringUtils.randomAlphanumeric(7))
                .setAddress(RandomStringUtils.randomAlphanumeric(7))
                .setMetroStation(RandomStringUtils.randomAlphanumeric(7))
                .setPhone(RandomStringUtils.randomAlphanumeric(7))
                .setRentTime(getRandomRentTime())
                .setDeliveryDate(getRandomDeliveryDate())
                .setComment(RandomStringUtils.randomAlphanumeric(7))
                .setColor(List.of(RandomStringUtils.randomAlphanumeric(7)));
    }

}
