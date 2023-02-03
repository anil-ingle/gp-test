package com.altimetrik.graphql.service;

import com.altimetrik.graphql.exception.AccountCreateException;
import com.altimetrik.graphql.exception.DuplicateResourceException;
import com.altimetrik.graphql.exception.UserNotFount;
import com.altimetrik.graphql.model.Account;
import com.altimetrik.graphql.model.AccountType;
import com.altimetrik.graphql.model.User;
import com.altimetrik.graphql.repository.AccountRepository;
import com.altimetrik.graphql.repository.UserRepository;
import com.altimetrik.graphql.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Account account;

    @BeforeEach
    public void setup() {
        user = new User(1L, "Anil", "aingle@altimetrik.com", 2345d, 1344d, null);
        account = new Account("12345678", AccountType.DEBIT, user);
    }

    // JUnit test for add user method
    @DisplayName("JUnit test for create account method")
    @Test
    void givenAccountObject_whenSave_thenAccountObject() throws Exception {
        // given - precondition or setup
        given(userRepository.findById(user.getUserId())).willReturn(Optional.ofNullable(user));
        given(accountRepository.save(account)).willReturn(account);
        //when actual behaviour
        Account savedAccount = userService.createAccount(account);
        //verify output
        Assertions.assertNotNull(savedAccount);
    }

    // JUnit test for add user method
    @DisplayName("JUnit test for pass invalid user id fro create account throw exception ")
    @Test
    void givenAccountObject_whenSaveAccount_thenThrowException() throws Exception {
        // given - precondition or setup
        given(userRepository.findById(user.getUserId())).willReturn(Optional.empty());

        //when actual behaviour
        Assertions.assertThrows(UserNotFount.class, () -> {
            userService.createAccount(account);
        });
        //verify output
        verify(accountRepository, never()).save(any(Account.class));
    }

    // JUnit test for add user method
    @DisplayName("JUnit test for create account throw exception for less expenses ")
    @Test
    void givenAccountObject_whenSaveAccount_thenThrowExceptionForInvalidExpenses() throws Exception {
        // given - precondition or setup
        User user1 = new User();
        user1.setUserId(user.getUserId());
        user1.setName(user.getName());
        user1.setSalary(1000);
        user1.setMonthlyExpenses(1000);
        given(userRepository.findById(user.getUserId())).willReturn(Optional.of(user1));
        Account account1 = new Account();
        account1.setAccountType(account.getAccountType());
        account1.setAccountNumber(account.getAccountNumber());
        account1.setUser(user1);
        account1.getUser().setMonthlyExpenses(2345);
        System.out.println("acc " + account1);
        //when actual behaviour
        Assertions.assertThrows(AccountCreateException.class, () -> {
            userService.createAccount(account1);
        });
        //verify output
        verify(accountRepository, never()).save(any(Account.class));
    }

    // JUnit test for add user method
    @DisplayName("JUnit test for add user method")
    @Test
    void givenUserObject_whenSave_thenUserObject() throws Exception {
        // given - precondition or setup
        given(userRepository.findUserByEmail(user.getEmail())).willReturn(null);
        given(userRepository.save(user)).willReturn(user);
        //when actual behaviour
        User savedUser = userService.createUser(user);
        //verify output
        Assertions.assertNotNull(savedUser);
    }

    @DisplayName("JUnit test for add user method throw exception while adding duplicate email")
    @Test
    void givenUserObject_whenSaveDuplicateEMail_thenThrowException() throws Exception {
        // given - precondition or setup
        given(userRepository.findUserByEmail(user.getEmail())).willReturn(user);
        //when actual behaviour
        Assertions.assertThrows(DuplicateResourceException.class, () -> {
            userService.createUser(user);
        });
        //verify output
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for get all User")
    @Test
    void givenUserList_whenFindAllUser_thenUserList() throws Exception {
        // given - precondition or setup
        User user1 = new User(2L, "AMol", "amol@altimetrik.com", 123D, 43D, new Account("12345678", AccountType.DEBIT, null));
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        given(userRepository.findAll()).willReturn(users);
        //when actual behaviour
        List<User> userList = userService.getUserAndAccount();
        //verify output
        org.assertj.core.api.Assertions.assertThat(userList).hasSize(2);

    }

    @DisplayName("JUnit test for get all User empty record")
    @Test
    void givenEmptyUserList_whenFindAllUser_thenEmptyUserList() throws Exception {
        // given - precondition or setup
        given(userRepository.findAll()).willReturn(new ArrayList<>());
        //when actual behaviour
        List<User> userList = userService.getUserAndAccount();
        //verify output
        org.assertj.core.api.Assertions.assertThat(userList).isEmpty();
    }

    @DisplayName("JUnit test for User by userId")
    @Test
    void givenUser_whenByUserId_thenUser() throws Exception {
        // given - precondition or setup
        given(userRepository.findById(user.getUserId())).willReturn(Optional.ofNullable(user));
        //when actual behaviour
       User user1 = userService.getUser(user.getUserId());
        //verify output
        org.assertj.core.api.Assertions.assertThat(user1).isNotNull();
    }

    @DisplayName("JUnit test for throw exception while passing wrong id")
    @Test
    void givenUser_whenUserId_thenThrowException() throws Exception {
        // given - precondition or setup
        Optional<User> optionalUser = Optional.empty();
        given(userRepository.findById(user.getUserId())).willReturn(optionalUser);
        //when actual behaviour
        Assertions.assertThrows(UserNotFount.class, () -> {
            userService.getUser(user.getUserId());
        });
        //verify output

    }
}
