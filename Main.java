public class Main {
    public static void main(String[] args) {
        Race race = new Race(10);
        race.addHorse(new Horse('a',"a", 0.5), 1);
        race.addHorse(new Horse('b',"b", 0.7), 2);
        race.addHorse(new Horse('c',"c", 0.4), 3);
        race.startRace();
    }
}
