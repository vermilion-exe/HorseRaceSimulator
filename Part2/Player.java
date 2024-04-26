package Part2;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private int money;
    private Set<Breed> unlockedBreeds;

    public Player() {
        this.money = 5000; //default money

        this.unlockedBreeds = new HashSet<Breed>() {{
            add(Breed.Eriskay);
            add(Breed.Haflinger);
            add(Breed.Aegidienberger);
        }}; // default breeds
    }
    
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Set<Breed> getUnlockedBreeds() {
        return unlockedBreeds;
    }

    public void addUnlockedBreed(Breed breed) {
        unlockedBreeds.add(breed);
    }

    public boolean buy(Breed breed){
        if(money >= Horse.getBreedPrice(breed)){
            money -= Horse.getBreedPrice(breed);
            unlockedBreeds.add(breed);
            
            return true;
        }

        return false;
    }

}
