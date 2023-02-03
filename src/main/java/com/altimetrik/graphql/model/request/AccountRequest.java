package com.altimetrik.graphql.model.request;

import com.altimetrik.graphql.model.AccountType;

public class AccountRequest {
    private AccountType accountType;
    private Long userId;

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
