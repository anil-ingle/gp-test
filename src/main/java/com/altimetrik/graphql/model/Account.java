package com.altimetrik.graphql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_type")
    private AccountType accountType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account(String accountNumber, AccountType accountType, User user) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.user = user;
    }

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", user=" + user +
                '}';
    }
}
