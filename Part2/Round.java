package Part2;

import java.util.Map;

public class Round {
    private int roundNumber;
    private Lane laneType;
    private int raceLength;
    private boolean raceFinished = false;
    private Map<Horse, Integer> horseBets;
    private Horse winner;
    private Integer totalProfit;

    public Round(int roundNumber, Lane laneType, int raceLength, Map<Horse, Integer> horseBets, Integer totalProfit) {
        this.roundNumber = roundNumber;
        this.laneType = laneType;
        this.raceLength = raceLength;
        this.horseBets = horseBets;
        this.totalProfit = totalProfit;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Lane getLaneType() {
        return laneType;
    }

    public int getRaceLength() {
        return raceLength;
    }

    public boolean wasRaceFinished()
    {
        return raceFinished;
    }

    public void setRaceFinished(boolean raceFinished)
    {
        this.raceFinished = raceFinished;
    }

    public Map<Horse, Integer> getHorseBets() {
        return horseBets;
    }

    public Horse getWinner() {
        return winner;
    }

    public void setWinner(Horse winner) {
        this.winner = winner;
    }

    public Integer getTotalProfit() {
        return totalProfit;
    }
    
}
