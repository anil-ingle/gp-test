package com.altimetrik.graphql.repository;

import com.altimetrik.graphql.model.Account;
import com.altimetrik.graphql.model.AccountType;
import com.altimetrik.graphql.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountRepository;

    private User user;
    private Account account;

    @BeforeEach
    private void setup() {
        user = new User("anil", "anil@altimetrik.com", 1234, 1234, null);
        account = new Account("12345678", AccountType.DEBIT, user);
    }

    @DisplayName("Junit for save Account")
    @Test
    void givenAccountObject_whenSaveThenReturnSaveObject() {
        //precondition setup

        //when action or behaviour
        Account savedAccount = accountRepository.save(account);
        //verify output
        Assertions.assertThat(savedAccount).isNotNull();
        Assertions.assertThat(savedAccount.getAccountId()).isPositive();

    }
}
