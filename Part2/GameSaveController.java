package Part2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GameSaveController {

    public static void saveGame(Race race) {
        // Save the game
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter("Part2/save.txt");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println("Race data:");
        printWriter.println(race.getRaceLength());
        printWriter.println(race.getLaneType());

        printWriter.println("Player data:");
        printWriter.println(race.getPlayer().getMoney());
        for(Breed breed : race.getPlayer().getUnlockedBreeds()) {
            printWriter.println(breed);
        }

        printWriter.println("");

        printWriter.println("Horses:");
        for(Horse horse : race.getHorses()) {
            printWriter.println(horse.getName());
            printWriter.println(horse.getBreed());
            printWriter.println(horse.getConfidence());
            printWriter.println(horse.getRacesWon());
        }

        printWriter.close();
    }

    public static Race loadGame() {
        // Load the game
        Race race = new Race(10, new Player());

        FileReader fileReader = null;
        try{
            fileReader = new FileReader("Part2/save.txt");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        try{
            if(bufferedReader.readLine() == null) {
                bufferedReader.close();
                return race;
            }

            int raceLength = Integer.parseInt(bufferedReader.readLine());
            Lane laneType = Lane.valueOf(bufferedReader.readLine());
            race.setRaceLength(raceLength);
            race.setLaneType(laneType);

            bufferedReader.readLine();

            Player player = new Player();
            int money = Integer.parseInt(bufferedReader.readLine());
            player.setMoney(money);
            String unlockedBreed = bufferedReader.readLine();
            while(!unlockedBreed.equals("")) {
                player.addUnlockedBreed(Breed.valueOf(unlockedBreed));
                unlockedBreed = bufferedReader.readLine();
            }
            race.setPlayer(player);

            String name;
            while((name=bufferedReader.readLine())!= null) {
                Breed breed = Breed.valueOf(bufferedReader.readLine());
                double confidence = Double.parseDouble(bufferedReader.readLine());
                int racesWon = Integer.parseInt(bufferedReader.readLine());
                Horse horse = new Horse(name, confidence, breed);
                horse.setRacesWon(racesWon);
                race.addHorse(horse);
            }
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return race;
    }

    public static void deleteGame() {
        // Delete the game
        try{
            FileWriter fileWriter = new FileWriter("Part2/save.txt");
            fileWriter.write("");
            fileWriter.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}