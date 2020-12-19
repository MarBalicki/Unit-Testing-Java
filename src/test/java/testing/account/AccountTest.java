package testing.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@Tag("fries")
class AccountTest {

    private Account newAccount;

    @BeforeEach
    void setUp() {
        newAccount = new Account();
    }

    @Test
    void newAccountShouldNotBeActiveAfterCreation() {
//        Account newAccount = new Account();
        assertFalse(newAccount.isActive());
        assertThat(newAccount.isActive(), is(false));

    }

    @Test
    void newAccountShouldBeActiveAfterActivated() {
//        Account newAccount = new Account();
        newAccount.activate();
        assertTrue(newAccount.isActive());
        assertThat(newAccount.isActive(), is(true));
    }

    @Test
    void newlyCreatedAccountShouldNotHaveDeliveryAddressSet() {
        Address defaultDeliveryAddress = newAccount.getDefaultDeliveryAddress();
        assertNull(defaultDeliveryAddress);
        assertThat(defaultDeliveryAddress, is(nullValue()));
    }

    @Test
    void deliveryAddressShouldNotBeNullAfterBeingSet() {
        Address address = new Address("Olchowa", "32");
        newAccount.setDefaultDeliveryAddress(address);
        Address deliveryAddress = newAccount.getDefaultDeliveryAddress();
        assertNotNull(deliveryAddress);
        assertThat(deliveryAddress, is(notNullValue()));
    }

    @RepeatedTest(5)
    void newAccountWithNotNullAddressShouldBeActive() {
        Address address = new Address("Olchowa", "32");
        Account account = new Account(address);
        assumingThat(address != null, () -> {
            assertTrue(account.isActive());
        });
    }

    @Test
    void invalidEmailShouldThrowException() {
        Account account = new Account();
        assertThrows(IllegalArgumentException.class, ()-> account.setEmail("wrongEmail"));
        assertThrows(IllegalArgumentException.class, ()-> account.setEmail("wrong@Email"));
        assertThrows(IllegalArgumentException.class, ()-> account.setEmail("wrongEmail.com"));
    }

    @Test
    void validEmailShouldBeSet() {
        Account account = new Account();
        account.setEmail("kontakt@gmail.com");
        assertThat(account.getEmail(), is("kontakt@gmail.com"));
    }

}