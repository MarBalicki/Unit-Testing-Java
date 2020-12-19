package testing.account;

import java.util.Arrays;
import java.util.List;

public class AccountRepositoryStub implements AccountRepository {

    @Override
    public List<Account> getAllAccounts() {
        Account account1 = new Account();
        Account account2 = new Account(new Address("Olchowa", "32"));
        Account account3 = new Account(new Address("Zawadzkiego", "2"));
        return Arrays.asList(account1, account2, account3);
    }

    @Override
    public List<String> getByName(String name) {
        return null;
    }
}
