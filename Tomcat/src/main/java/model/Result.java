package model;

public class Result {
    private int resultId;
    private int raceId;
    private int horseId;
    private int position;

    public Result() {
    }

    public Result(int resultId, int raceId, int horseId, int position) {
        this.resultId = resultId;
        this.raceId = raceId;
        this.horseId = horseId;
        this.position = position;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
