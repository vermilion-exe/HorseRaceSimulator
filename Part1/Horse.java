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
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        hasFallen = true;
    }
    
    public double getConfidence()
    {
        return confidence;
    }
    
    public int getDistanceTravelled()
    {
        return distanceTravelled;
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
    }
    
    public boolean hasFallen()
    {
        return hasFallen;
    }

    public void moveForward()
    {
        distanceTravelled++;   
    }

    public void setConfidence(double newConfidence)
    {
        confidence = newConfidence;
    }
    
}
