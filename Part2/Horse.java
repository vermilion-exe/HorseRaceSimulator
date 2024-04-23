package Part2;

/**
 * Write a description of class Horse here.
 * 
 * @author Farhad Garaisa
 * @version 1.0
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private String[] imageLinks;
    private double confidence;
    private int distanceTravelled;
    private boolean hasFallen;
    private Breed breed;
    private int racesWon;
    private int speed;
    private int bet;
    private double chanceOfWinning;
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(String horseName, double horseConfidence, Breed horseBreed)
    {
        name = horseName;
        breed = horseBreed;
        imageLinks = new String[5];
        for(int i  = 0; i<5; i++)
        {
            imageLinks[i] = "resources/horse-sprites/" + horseBreed.toString() + "_" + i + ".png";
        }
        confidence = horseConfidence;
        breed = horseBreed;
        speed = getBreedSpeed(breed);
    }
    
    private static int getBreedSpeed(Breed breed){
        switch(breed){
            case Arabian:
                return 3;
            case Morgan:
                return 3;
            case Caspian:
                return 2;
            case Andalusian:
                return 2;
            case Connemara:
                return 2;
            case Aegidienberger:
                return 1;
            case Haflinger:
                return 1;
            case Eriskay:
                return 1;
            default:
                return 0;
        }
    }

    public static int getBreedPrice(Breed breed){
        switch(breed){
            case Arabian:
                return 2000;
            case Morgan:
                return 2000;
            case Caspian:
                return 1000;
            case Andalusian:
                return 1000;
            case Connemara:
                return 1000;
            case Aegidienberger:
                return 500;
            case Haflinger:
                return 500;
            case Eriskay:
                return 500;
            default:
                return 0;
        }
    }
    
    //Other methods of class Horse
    public void fall()
    {
        hasFallen = true;
        if(getConfidence() > 0.1)
        {
            setConfidence(getConfidence() - 0.1);
        }
    }
    
    public double getConfidence()
    {
        return confidence;
    }

    public double getChanceOfWinning(){
        return chanceOfWinning;
    }

    public void setChanceOfWinning(double chanceOfWinning){
        this.chanceOfWinning = chanceOfWinning;
    }

    public int getBet(){
        return bet;
    }

    public void setBet(int bet){
        this.bet = bet;
    }
    
    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }

    public void setDistanceTravelled(int distanceTravelled){
        this.distanceTravelled = distanceTravelled;
    }

    public int getSpeed(){
        return speed;
    }

    public Breed getBreed()
    {
        return breed;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String[] getImageLinks()
    {
        return imageLinks;
    }
    
    public void goBackToStart()
    {
        distanceTravelled = 0;
        hasFallen = false;
    }
    
    public boolean hasFallen()
    {
        return hasFallen;
    }

    public void moveForward()
    {
        distanceTravelled += speed;   
    }

    public void setConfidence(double newConfidence)
    {
        confidence = newConfidence;
    }

    public int getRacesWon(){
        return racesWon;
    }

    public void setRacesWon(int racesWon){
        this.racesWon = racesWon;
    }

    public void countVictory(){
        racesWon++;

        if(getRacesWon() % 3 == 0 && getConfidence() < 1){
            setConfidence(getConfidence() + 0.1);
        }
    }

    public static Horse copyOf(Horse horse){
        Horse newHorse = new Horse(horse.getName(), horse.getConfidence(), horse.getBreed());
        newHorse.setBet(horse.getBet());
        newHorse.setChanceOfWinning(horse.getChanceOfWinning());
        newHorse.setRacesWon(horse.getRacesWon());
        newHorse.setDistanceTravelled(horse.getDistanceTravelled());
        if(horse.hasFallen()){
            newHorse.fall();
        }

        return newHorse;
    }
    
}
