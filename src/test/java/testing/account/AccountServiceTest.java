package testing.account;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@MockitoSettings(strictness = Strictness.STRICT_STUBS) //lepsze komunikaty
class AccountServiceTest {

    @Test
    void getAllActiveAccounts() {
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
//        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        given(accountRepository.getAllAccounts()).willReturn(accounts);

        List<Account> accountList = accountService.getAllActiveAccounts();
        assertThat(accountList, hasSize(2));
    }

    private List<Account> prepareAccountData() {
        Account account1 = new Account();
        Account account2 = new Account(new Address("Olchowa", "32"));
        Account account3 = new Account(new Address("Zawadzkiego", "2"));
        return Arrays.asList(account1, account2, account3);
    }

    @Test
    void getNoActiveAccounts() {
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
//        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        given(accountRepository.getAllAccounts()).willReturn(accounts);

        List<Account> accountList = accountService.getNoActiveAccounts();
        assertThat(accountList, hasSize(1));
    }

    @Test
    void getAccountsByName() {
        //given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
//        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        given(accountRepository.getByName("Marcin")).willReturn(Collections.singletonList("Balicki"));

        //when
        List<String> accountNames = accountService.findByName("Marcin");
        //then
        assertThat(accountNames, contains("Balicki"));
    }


}