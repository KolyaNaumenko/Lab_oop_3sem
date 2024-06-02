package model;
public class User {
    private int userId;
    private String name;
    private String password;
    private String email;
    private String role;
    private double money;

    public User() {
    }

    public User(int userId, String name, String password, String email, String role, double money) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.money = money;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

}