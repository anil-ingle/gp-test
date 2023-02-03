package com.altimetrik.graphql.service;

import com.altimetrik.graphql.exception.AccountCreateException;
import com.altimetrik.graphql.exception.InternalServerException;
import com.altimetrik.graphql.exception.UserNotFount;
import com.altimetrik.graphql.model.Account;
import com.altimetrik.graphql.model.User;

import java.util.List;

/**
 * This contract
 */
public interface UserService {

    String EMAIL_ALREADY_EXIST = "Email already exist";

    /**
     * This method used for creating new user
     *
     * @param user check entity details {@link com.altimetrik.graphql.model.User}
     * @return created user
     */
    User createUser(User user) throws InternalServerException;

    /**
     * This method used for create user account
     *
     * @param accountRequest check entity details {@link com.altimetrik.graphql.model.request.AccountRequest}
     * @return created user account
     * @throws UserNotFount           if we pass wrong user id
     * @throws AccountCreateException expense and salary not match bossiness requirement
     */
    Account createAccount(Account accountRequest) throws UserNotFount, AccountCreateException;

    /**
     * This method used for get all user account details
     *
     * @return List of user and associated account details
     */
    List<User> getUserAndAccount();

    /**
     * This method used for get User details by user id
     *
     * @param userId user id
     * @return user
     */
    User getUser(Long userId) throws UserNotFount;


}
