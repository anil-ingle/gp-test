package com.altimetrik.graphql.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "name")
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "salary")
    private double salary;
    @Column(name = "monthly_expenses")
    private double monthlyExpenses;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(double monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public User(Long userId, String name, String email, double salary, double monthlyExpenses, Account account) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.monthlyExpenses = monthlyExpenses;
        this.account = account;
    }

    public User(String name, String email, double salary, double monthlyExpenses, Account account) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.monthlyExpenses = monthlyExpenses;
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", monthlyExpenses=" + monthlyExpenses +
                ", account=" + account +
                '}';
    }
}
