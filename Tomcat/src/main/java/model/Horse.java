package model;

public class Horse {
    private int horseId;
    private String name;
    private int age;
    private int wins;
    private int races;

    public Horse() {
    }

    public Horse(int horseId, String name, int age, int wins, int races) {
        this.horseId = horseId;
        this.name = name;
        this.age = age;
        this.wins = wins;
        this.races = races;
    }

    public int getHorseId() {
        return horseId;
    }

    public void setHorseId(int horseId) {
        this.horseId = horseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getRaces() {
        return races;
    }

    public void setRaces(int races) {
        this.races = races;
    }

}
