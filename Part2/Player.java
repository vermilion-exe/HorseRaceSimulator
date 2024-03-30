package Part2;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int money;
    private List<Breed> unlockedBreeds;

    public Player() {
        this.money = 5000; //default money
        this.unlockedBreeds = new ArrayList<Breed>() {{
            add(Breed.Eriskay);
            add(Breed.Haflinger);
            add(Breed.Aegidienberger);
        }};
    }
    
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Breed> getUnlockedBreeds() {
        return unlockedBreeds;
    }

    public void addUnlockedBreed(Breed breed) {
        unlockedBreeds.add(breed);
    }

}
