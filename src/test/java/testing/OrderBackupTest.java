package testing;

import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;

class OrderBackupTest {

    private static OrderBackup orderBackup;

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        orderBackup = new OrderBackup();
        orderBackup.createFile();
    }

    @BeforeEach
    void appendTheStartOfTheLine() throws IOException {
        orderBackup.getWriter().append("New order: ");
    }

    @Tag("fries")
    @Test
    void backupOrderWithOneMeal() throws IOException {
        Meal meal = new Meal(7, "fries");
        Order order = new Order();
        order.addMealToOrder(meal);
        orderBackup.backupOrder(order);
        System.out.println("Order: " + order.toString() + " backed up.");
    }

    @AfterEach
    void appendTheEndOfTheLine() throws IOException {
        orderBackup.getWriter().append(" back up.");
    }

    @AfterAll
    static void tearDown() throws IOException {
        orderBackup.closeFile();
    }

}