package Part2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.nio.charset.Charset;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private ArrayList<Horse> horses;
    private Player player;
    private Lane laneType;
    private List<Round> rounds;
    private boolean raceFinished = false;
    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, Player player)
    {
        // initialise instance variables

        if(distance < 0)
        {
            System.out.println("Distance cannot be negative");
            System.out.println("Race length set to 0");
            raceLength = 0;
        }
        else {
        raceLength = distance;
        }
        this.player = player;
        horses = new ArrayList<Horse>();
        rounds = new ArrayList<Round>();
        laneType = Lane.Dirt;
    }

    /**
     * Sets the length of the racetrack to a given distance
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public void setRaceLength(int distance)
    {
        if(distance < 0)
        {
            System.out.println("Distance cannot be negative");
            System.out.println("Race length wasn't changed");
        }
        else {
            raceLength = distance;
        }
    }

    public int getRaceLength(){
        return raceLength;
    }

    public ArrayList<Horse> getHorses(){
        return horses;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Lane getLaneType(){
        return laneType;
    }

    public void setLaneType(Lane lane){
        laneType = lane;
    }

    public List<Round> getRounds(){
        return rounds;
    }

    public void addRound(Round round){
        rounds.add(round);
    }

    public int finishRound(Map<Horse, Double> initialHorseConfidence){
        Map<Horse, Integer> horseBets = new HashMap<>();
        
        getHorses().forEach(h -> {
            Horse horse = Horse.copyOf(h);
            horse.setConfidence(initialHorseConfidence.get(h));
            horseBets.put(horse, h.getBet());
        });
        int totalProfit = calculateProfit();
        Round round = new Round(getRounds().size()+1, getLaneType(), getRaceLength(),
                horseBets, totalProfit);
        Optional<Horse> winner = round.getHorseBets().keySet().stream().filter(x->raceWonBy(x)).findFirst();
        if(winner.isPresent()){
            round.setWinner(winner.get());
            round.setRaceFinished(true);
        }
        else{
            round.setRaceFinished(false);
        }

        addRound(round);

        return totalProfit;
    }

    public boolean getRaceFinished(){
        return raceFinished;
    }

    public void setRaceFinished(boolean finished){
        raceFinished = finished;
    }
    
    /**
     * Adds a horse to the race in a given lane and returns true if horse was added
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public boolean addHorse(Horse theHorse){
        if(horses.size() < 10)
        {
            horses.add(theHorse);
            return true;
        }
        
        return false;
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void iterateRace()
    {      
        for(Horse horse : horses)
        {
            moveHorse(horse);
        }                

    }

    public boolean allHorsesFallen()
    {
        for(Horse horse : horses)
        {
            if(!horse.hasFallen())
            {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    public boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() >= raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void calculateOdds(){
        double sum = 0.0;

        for(Horse horse : horses){
            sum += horse.getConfidence()+(double)(horse.getSpeed()+horse.getRacesWon())/10.0;
        }

        for(Horse horse : horses){
            horse.setChanceOfWinning(((horse.getConfidence()+(double)(horse.getSpeed()+horse.getRacesWon())/10.0)/sum*100.0));
        }
    }

    private int calculateProfit(){
        int totalProfit = 0;

        for(Horse horse : horses){
            if(horse.getDistanceTravelled() >= raceLength){
                int gain = horse.getBet()*12;
                if(gain%10!=0){
                    gain = gain + 10 - gain%10;
                }
                gain = gain/10;
                player.setMoney(player.getMoney() + gain);
                totalProfit += gain-horse.getBet();
            }
            horse.setBet(0);
        }

        return totalProfit;
    }
    
    // /***
    //  * Print the race on the terminal
    //  */
    // private void printRace()
    // {
    //     System.out.print("\033[H\033[2J"); 
    //     System.out.flush();  //clear the terminal window
        
    //     multiplePrint('=',raceLength+3); //top edge of track
    //     System.out.println();
        
    //     for(Horse horse : horses)
    //     {
    //         printLane(horse);
    //         System.out.println();
    //     }
        
    //     multiplePrint('=',raceLength+3); //bottom edge of track
    //     System.out.println();    
    // }
    
    // /**
    //  * print a horse's lane during the race
    //  * for example
    //  * |           X                      |
    //  * to show how far the horse has run
    //  */
    // private void printLane(Horse theHorse)
    // {
    //     //calculate how many spaces are needed before
    //     //and after the horse
    //     int spacesBefore = theHorse.getDistanceTravelled();
    //     int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
    //     //print a | for the beginning of the lane
    //     System.out.print('|');
        
    //     //print the spaces before the horse
    //     multiplePrint(' ',spacesBefore);
        
    //     //if the horse has fallen then print dead
    //     //else print the horse's symbol
    //     if(theHorse.hasFallen())
    //     {
    //         //fix
    //         String fallSign = "\u263A";
    //         byte[] bytes = fallSign.getBytes(Charset.forName("UTF-16"));
    //         String utf8EncodedString = new String(bytes, Charset.forName("UTF-16"));
    //         System.out.print(utf8EncodedString);
    //     }
    //     else
    //     {
    //         System.out.print(theHorse.getSymbol());
    //     }
        
    //     //print the spaces after the horse
    //     multiplePrint(' ',spacesAfter);
        
    //     //print the | for the end of the track
    //     System.out.print('|');
    //     System.out.print(" " + theHorse.getName() + " (Current confidence " + theHorse.getConfidence() + ")");
    // }
        
    
    // /***
    //  * print a character a given number of times.
    //  * e.g. printmany('x',5) will print: xxxxx
    //  * 
    //  * @param aChar the character to Print
    //  */
    // private void multiplePrint(char aChar, int times)
    // {
    //     // use for loop
    //     int i = 0;
    //     while (i < times)
    //     {
    //         System.out.print(aChar);
    //         i = i + 1;
    //     }
    // }
}
