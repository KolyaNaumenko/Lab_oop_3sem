package model;

public class Bet {
    private int betId;
    private int userId;
    private int betTypeId;
    private double amount;
    private String status;
    private double potential_win;

    public Bet() {
    }

    public Bet(int betId, int userId, int betTypeId, double amount, String status, double potential_win) {
        this.betId = betId;
        this.userId = userId;
        this.betTypeId = betTypeId;
        this.amount = amount;
        this.status = status;
        this.potential_win = potential_win;
    }

    public int getBetId() {
        return betId;
    }

    public void setBetId(int betId) {
        this.betId = betId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBetTypeId() {
        return betTypeId;
    }

    public void setBetTypeId(int betTypeId) {
        this.betTypeId = betTypeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPotential_win() {
        return potential_win;
    }

    public void setPotential_win(double potential_win) {
        this.potential_win = potential_win;
    }

}
