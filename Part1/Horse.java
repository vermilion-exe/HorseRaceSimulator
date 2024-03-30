package Part1;

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
    
}
