package Part1;

public class Main {
    public static void main(String[] args) {
        Race race = new Race(10);
        race.addHorse(new Horse('a',"a", 0.5));
        race.addHorse(new Horse('b',"b", 0.7));
        race.addHorse(new Horse('c',"c", 0.4));
        race.startRace();
    }
}
