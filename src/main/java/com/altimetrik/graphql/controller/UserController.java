package com.altimetrik.graphql.controller;

import com.altimetrik.graphql.exception.AccountCreateException;
import com.altimetrik.graphql.exception.DuplicateResourceException;
import com.altimetrik.graphql.exception.UserNotFount;
import com.altimetrik.graphql.model.Account;
import com.altimetrik.graphql.model.User;
import com.altimetrik.graphql.model.request.AccountRequest;
import com.altimetrik.graphql.model.request.UserRequest;
import com.altimetrik.graphql.model.response.GraphqlResponse;
import com.altimetrik.graphql.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {
    private static final String FAILED_MESSAGE = "Operation Failed";
    private static final String SUCCESS_MESSAGE = "Operation Successful";
    @Autowired
    private UserService userService;

    @QueryMapping(name = "allUsers")
    public List<User> allUsers() {
        return userService.getUserAndAccount();
    }

    @MutationMapping(name = "createAccount")
    public GraphqlResponse<Account> createAccount(@Argument AccountRequest account) {
        GraphqlResponse<Account> graphqlResponse = null;
        try {
            graphqlResponse = new GraphqlResponse<>(userService.createAccount(new Account("",
                    account.getAccountType(), new User(account.getUserId()))), SUCCESS_MESSAGE, null);
        } catch (UserNotFount | AccountCreateException | DuplicateResourceException e) {
            graphqlResponse = new GraphqlResponse<>(null, FAILED_MESSAGE, e.getMessage());
        }
        return graphqlResponse;
    }

    @MutationMapping(name = "createUser")
    public GraphqlResponse<User> createUser(@Argument UserRequest user) {
        GraphqlResponse<User> graphqlResponse = null;
        try {
            User userInput = new User();
            BeanUtils.copyProperties(user, userInput);
            graphqlResponse = new GraphqlResponse<>(userService.createUser(userInput), SUCCESS_MESSAGE, null);
        } catch (DuplicateResourceException e) {
            e.printStackTrace();
            graphqlResponse = new GraphqlResponse<>(null, FAILED_MESSAGE, e.getMessage());
        } catch (Exception e) {
            graphqlResponse = new GraphqlResponse<>(null, FAILED_MESSAGE, e.getMessage());
        }
        return graphqlResponse;
    }

    @QueryMapping(name = "getUser")
    public User getUser(@Argument Long userId) throws UserNotFount {
        return userService.getUser(userId);
    }
}
