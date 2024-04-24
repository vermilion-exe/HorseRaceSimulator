package Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class GameSaveController {

    public static void saveGame(Race race) {
        // Save the game
        FileWriter fileWriter = null;
        try{
            validateSaveFile();
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
            printWriter.println(horse.getName()+" "+horse.getBreed()+" "
            +String.format("%.2f", horse.getConfidence())+" "+horse.getRacesWon()
            +" "+horse.getBet());
        }

        printWriter.println("");

        printWriter.println("Rounds:");
        for(Round round : race.getRounds()){
            printWriter.println(round.getRoundNumber()+" "+round.getRaceLength()+" "+round.getLaneType()+" "+round.getTotalProfit()+" "+round.wasRaceFinished());
            for(Horse horse : round.getHorseBets().keySet()) {
                printWriter.println(horse.getName()+" "+horse.getBreed()+" "
                +String.format("%.2f", horse.getConfidence())
                +" "+horse.getRacesWon()+" "+horse.getChanceOfWinning()+" "+horse.getDistanceTravelled()
                +" "+horse.hasFallen()+" "+round.getHorseBets().get(horse)
                +(horse==round.getWinner()?" 1":" 0"));
            }
            printWriter.println("");
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

            // Load player
            Player player = new Player();
            int money = Integer.parseInt(bufferedReader.readLine());
            player.setMoney(money);
            String unlockedBreed = bufferedReader.readLine();
            while(!unlockedBreed.isBlank()) {
                player.addUnlockedBreed(Breed.valueOf(unlockedBreed));
                unlockedBreed = bufferedReader.readLine();
            }
            race.setPlayer(player);

            bufferedReader.readLine();

            // Load horses
            String horseData;
            while(!(horseData=bufferedReader.readLine()).isBlank()) {
                String[] horseDataArray = horseData.split(" ");
                String name = horseDataArray[0];
                Breed breed = Breed.valueOf(horseDataArray[1]);
                double confidence = Double.parseDouble(horseDataArray[2]);
                confidence = Math.round(confidence * 10.0) / 10.0;
                int racesWon = Integer.parseInt(horseDataArray[3]);
                int bet = Integer.parseInt(horseDataArray[4]);
                Horse horse = new Horse(name, confidence, breed);
                horse.setRacesWon(racesWon);
                horse.setBet(bet);
                race.addHorse(horse);
            }

            bufferedReader.readLine();

            // Load rounds
            String roundData;
            while((roundData=bufferedReader.readLine()) != null) {
                String[] roundDataArray = roundData.split(" ");
                int roundNumber = Integer.parseInt(roundDataArray[0]);
                int roundRaceLength = Integer.parseInt(roundDataArray[1]);
                Lane roundLaneType = Lane.valueOf(roundDataArray[2]);
                int totalProfit = Integer.parseInt(roundDataArray[3]);
                boolean raceFinished = Boolean.parseBoolean(roundDataArray[4]);

                String pastHorseData;
                Map<Horse, Integer> horseBets = new HashMap<>();
                Horse winner = null;
                while(!(pastHorseData=bufferedReader.readLine()).isBlank()) {
                    String[] horseDataArray = pastHorseData.split(" ");
                    String horseName = horseDataArray[0];
                    Breed horseBreed = Breed.valueOf(horseDataArray[1]);
                    double horseConfidence = Double.parseDouble(horseDataArray[2]);
                    int horseRacesWon = Integer.parseInt(horseDataArray[3]);
                    double horseChanceOfWinning = Double.parseDouble(horseDataArray[4]);
                    int horseDistanceTravelled = Integer.parseInt(horseDataArray[5]);
                    boolean horseHasFallen = Boolean.parseBoolean(horseDataArray[6]);
                    int horseBet = Integer.parseInt(horseDataArray[7]);
                    boolean horseWon = horseDataArray[8].equals("1");
                    Horse horse = new Horse(horseName, horseConfidence, horseBreed);
                    horse.setRacesWon(horseRacesWon);
                    horse.setChanceOfWinning(horseChanceOfWinning);
                    horse.setDistanceTravelled(horseDistanceTravelled);
                    if(horseHasFallen){
                        horse.fall();
                    }
                    horseBets.put(horse, horseBet);
                    if(horseWon) {
                        winner = horse;
                    }
                }

                Round round = new Round(roundNumber, roundLaneType, roundRaceLength, horseBets, totalProfit);
                round.setWinner(winner);
                round.setRaceFinished(raceFinished);
                race.addRound(round);
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

    public static void validateSaveFile(){
        try{
            Path path = Paths.get("Part2/save.txt");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        
        }
    }
    
}