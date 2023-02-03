package com.altimetrik.graphql.model.request;

public class UserRequest {
    private Long userId;
    private String name;
    private String email;
    private double salary;
    private double monthlyExpenses;

    public UserRequest() {
    }

    public UserRequest(Long userId, String name, String email, double salary, double monthlyExpenses) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.monthlyExpenses = monthlyExpenses;
    }

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

    @Override
    public String toString() {
        return "UserRequest{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", monthlyExpenses=" + monthlyExpenses +
                '}';
    }
}
