package com.altimetrik.graphql.service.impl;

import com.altimetrik.graphql.exception.AccountCreateException;
import com.altimetrik.graphql.exception.DuplicateResourceException;
import com.altimetrik.graphql.exception.InternalServerException;
import com.altimetrik.graphql.exception.UserNotFount;
import com.altimetrik.graphql.model.Account;
import com.altimetrik.graphql.model.User;
import com.altimetrik.graphql.repository.AccountRepository;
import com.altimetrik.graphql.repository.UserRepository;
import com.altimetrik.graphql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Implements contract of {@link com.altimetrik.graphql.service.UserService}
 * This class have functionality of user and account related read and write operation
 * Author : aingle@altimetrik.com
 * Date : 31-Jan-2023
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public User createUser(User user) throws InternalServerException {
        User userResponse = userRepository.findUserByEmail(user.getEmail());
        if (userResponse != null) {
            throw new DuplicateResourceException(EMAIL_ALREADY_EXIST);
        }
        try {
            return userRepository.save(user);
        } catch (DataAccessException dataAccessException) {
            throw new InternalServerException("Error occurred while creating user, Please try again.");
        }
    }

    @Override
    public Account createAccount(Account accountRequest) throws UserNotFount, AccountCreateException {
        Optional<User> optionalUser = userRepository.findById(accountRequest.getUser().getUserId());
        if (!optionalUser.isPresent()) {
            throw new UserNotFount("User not found");
        }
        User user = optionalUser.get();
        if (isAllowToCreateAccount(user.getSalary(), user.getMonthlyExpenses())) {
            throw new AccountCreateException("Monthly salary less than expenses");
        }
        accountRequest.setUser(user);
        accountRequest.setAccountNumber(generateCardNumber());
        try {
            return accountRepository.save(accountRequest);
        } catch (DataAccessException e) {
            throw new DuplicateResourceException("Account already exist");
        }
    }

    @Override
    public List<User> getUserAndAccount() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) throws UserNotFount {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserNotFount("User not found");
        }
        return optionalUser.get();
    }

    /**
     * This method used for check if user allow to create account or not
     *
     * @param salary
     * @param monthlyExpenses
     * @return boolean
     */
    private boolean isAllowToCreateAccount(Double salary, Double monthlyExpenses) {
        return (salary - monthlyExpenses <= 1000);
    }

    /**
     * This method used for generate 10 digit card
     *
     * @return String card number
     */
    private String generateCardNumber() {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            builder.append(digit);
        }
        return builder.toString();
    }
}
