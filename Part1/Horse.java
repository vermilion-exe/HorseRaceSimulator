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
    private Character symbol;
    private double confidence;
    private int distanceTravelled;
    private boolean hasFallen;
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        name = horseName;
        symbol = horseSymbol;
        confidence = horseConfidence;
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
    
    public String getName()
    {
        return name;
    }
    
    public char getSymbol()
    {
        return symbol;
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
    
    public void setSymbol(char newSymbol)
    {
        symbol = newSymbol;
    }
    
}
