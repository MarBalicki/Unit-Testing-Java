package testing;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderBackupExcecutionOrderTest {
    
    @Test
    void callingBackupWithoutCreatingAFileFirstShouldThrowException() throws IOException {
        OrderBackup orderBackup = new OrderBackup();
        assertThrows(IOException.class, ()->orderBackup.backupOrder(new Order()));
    }
}
