package model;

public class BetType {
    private int betTypeId;
    private int raceId;
    private int horseId;
    private double odd;
    private boolean is_first;

    public BetType() {
    }

    public BetType(int betTypeId, int raceId, int horseId, double odd, boolean isWin) {
        this.betTypeId = betTypeId;
        this.raceId = raceId;
        this.horseId = horseId;
        this.odd = odd;
        this.is_first = isWin;
    }

    public int getBetTypeId() {
        return betTypeId;
    }

    public void setBetTypeId(int betTypeId) {
        this.betTypeId = betTypeId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getHorseId() {
        return horseId;
    }

    public void setHorseId(int horseId) {
        this.horseId = horseId;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public boolean isIs_first() {
        return is_first;
    }

    public void setIs_first(boolean is_first) {
        this.is_first = is_first;
    }

}
