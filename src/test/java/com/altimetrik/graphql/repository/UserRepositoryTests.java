package com.altimetrik.graphql.repository;

import com.altimetrik.graphql.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    private void setup(){
       user= new User("anil", "anil@altimetrik.com", 1234, 1234, null);
    }

    @DisplayName("Junit for save user")
    @Test
    void givenUserObject_whenSaveThenReturnSaveObject() {
        // given precondition setup
        //when action or behaviour
        User savedUser = userRepository.save(user);

        //then verify output
        Assertions.assertThat(savedUser).isNotIn();
        Assertions.assertThat(savedUser.getUserId()).isPositive();
    }

    @DisplayName("Junit for find all method")
    @Test
    void givenUsers_whenFindAll_thenUserList() {
        //given precondition
        User user1 = new User("amol", "amol@altimetrik.com", 1234, 1234, null);

        userRepository.save(user);
        userRepository.save(user1);
        //when action or behaviour
        List<User> users = userRepository.findAll();
        //verify output
        Assertions.assertThat(users).isNotNull().hasSize(2);

    }

    @DisplayName("Junit test for get userById")
    @Test
    void given_userList_whenFindById_thenUser() {
        //precondition setup
        User user1 = new User("amol", "amol@altimetrik.com", 1234, 1234, null);

        userRepository.save(user);
        userRepository.save(user1);
        //when action or behaviour
        List<User> userList = userRepository.findAll();
        Long userId = userList.get(0).getUserId();
        Optional<User> getUserByIdOne = userRepository.findById(userId);
        //verify output
        Assertions.assertThat(getUserByIdOne).isPresent();
        Assertions.assertThat(getUserByIdOne.get().getUserId()).isEqualTo(userId);
    }

    @DisplayName("Junit test for get user email")
    @Test
    void given_userList_whenFindByEmail_thenUser() {
        //precondition setup
        User user1 = new User("Amol", "amol@altimetrik.com", 1234, 1234, null);

        userRepository.save(user);
        userRepository.save(user1);
        //when action or behaviour
        User getUserByIdOne = userRepository.findUserByEmail("anil@altimetrik.com");
        //verify output
        Assertions.assertThat(getUserByIdOne).isNotNull();
        Assertions.assertThat(getUserByIdOne.getEmail()).isEqualTo("anil@altimetrik.com");
    }
}
