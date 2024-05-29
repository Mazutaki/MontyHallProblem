import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String winningChar = "W";
    public static String losingChar = "L";

    public static void main(String[] args) {
        int amountOfGames = 100;
        int amountOfDoors = 10;
        int sleepMS = 1000;
        boolean sleep = false;

        testWithProbability(amountOfGames, amountOfDoors);
        testVisually(sleep, sleepMS, amountOfGames, amountOfDoors);
    }

    public static void testWithProbability(int amountOfGames, int amountOfDoors) {
        List<String> doors = new ArrayList<>();
        for (int i = 0; i < amountOfDoors; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < amountOfDoors; j++) {
                if (i == j) {
                    sb.append(winningChar);
                } else {
                    sb.append(losingChar);
                }
            }
            doors.add(sb.toString());
        }

        List<String> scenarios = new ArrayList<>();
        for (int i = 0; i < amountOfGames; i++) {
            // Select one string out of "arr" in a !/3 probability
            int index = (int) (Math.random() * amountOfDoors);
            scenarios.add(doors.get(index));
        }

        int winsWithoutSwitchingSameDoor = 0;
        int winsWithoutSwitchingDifferentDoor = 0;
        int winsWithSwitchingSameDoor = 0;
        int winsWithSwitchingDifferentDoor = 0;
        int staticPickedDoor = (int) (Math.random() * 3);
        for (String scenario : scenarios) {
            int pickedDoor = (int) (Math.random() * 3);

            int winningCharIsAt = scenario.indexOf(winningChar);

            if (staticPickedDoor == winningCharIsAt) {
                winsWithoutSwitchingSameDoor++;
            }

            if (pickedDoor == winningCharIsAt) {
                winsWithoutSwitchingDifferentDoor++;
            }

            if (staticPickedDoor != winningCharIsAt) {
                winsWithSwitchingSameDoor++;
            }

            if (pickedDoor != winningCharIsAt) {
                winsWithSwitchingDifferentDoor++;
            }
        }

        System.out.println("Wins without switching same door: " + winsWithoutSwitchingSameDoor);
        System.out.println("Wins with switching same door: " + winsWithSwitchingSameDoor);
        System.out.println("Wins without switching different door: " + winsWithoutSwitchingDifferentDoor);
        System.out.println("Wins with switching different door: " + winsWithSwitchingDifferentDoor);
    }

    // WARNING: This method is not optimized and will take a long time to run
    public static void testVisually(boolean sleep, int sleepMS, int amountOfGames, int amountOfDoors) {
        List<String> doors = new ArrayList<>();
        for (int i = 0; i < amountOfDoors; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < amountOfDoors; j++) {
                if (i == j) {
                    sb.append(winningChar);
                } else {
                    sb.append(losingChar);
                }
            }
            doors.add(sb.toString());
        }

        List<String> scenarios = new ArrayList<>();
        for (int i = 0; i < amountOfGames; i++) {
            // Select one string out of "arr" in a !/3 probability
            int index = (int) (Math.random() * amountOfDoors);
            scenarios.add(doors.get(index));
        }

        int winsWithoutSwitchingSameDoor = 0;
        int winsWithoutSwitchingDifferentDoor = 0;
        int winsWithSwitchingSameDoor = 0;
        int winsWithSwitchingDifferentDoor = 0;
        int staticDoor = (int) (Math.random() * 3);
        for (String scenario : scenarios) {
            System.out.println(scenario);
            int randomDoor = (int) (Math.random() * 3);

            String scenarioWithRandomDoor = scenario.substring(0, randomDoor) + "(" + scenario.charAt(randomDoor) + ")" + scenario.substring(randomDoor + 1);
            String randomChar = String.valueOf(scenario.charAt(randomDoor));
            System.out.println("Selected Door (random):" + scenarioWithRandomDoor);
            String scenarioWithoutRandomDoor = scenario.substring(0, randomDoor) + scenario.substring(randomDoor + 1);
            System.out.println("All others (random): " + scenarioWithoutRandomDoor);
            System.out.println("Random door: " + randomDoor + " = " + randomChar);

            String scenarioWithStaticDoor = scenario.substring(0, staticDoor) + "(" + scenario.charAt(staticDoor) + ")" + scenario.substring(staticDoor + 1);
            String staticChar = String.valueOf(scenario.charAt(staticDoor));
            System.out.println("Selected Door (static):" + scenarioWithStaticDoor);
            String scenarioWithoutStaticDoor = scenario.substring(0, staticDoor) + scenario.substring(staticDoor + 1);
            System.out.println("All others (static): " + scenarioWithoutStaticDoor);
            System.out.println("Static door: " + staticDoor + " = " + staticChar);

            sleep(sleep, sleepMS);

            System.out.println("Now remove all other doors except a winning one");
            for (int i = 0; i < amountOfDoors - 2; i++) {
                scenarioWithoutRandomDoor = scenarioWithoutRandomDoor.replaceFirst(losingChar, "");
                scenarioWithoutStaticDoor = scenarioWithoutStaticDoor.replaceFirst(losingChar, "");
                System.out.println("All others (random): " + scenarioWithoutRandomDoor);
                System.out.println("All others (static): " + scenarioWithoutStaticDoor);
            }

            System.out.println("Our random door is: " + randomChar);
            System.out.println("Our left over door is: " + scenarioWithoutRandomDoor);
            System.out.println("Our static door is: " + staticChar);
            System.out.println("Our left over door is: " + scenarioWithoutStaticDoor);

            winsWithoutSwitchingSameDoor += staticChar.equals(winningChar) ? 1 : 0;
            winsWithoutSwitchingDifferentDoor += randomChar.equals(winningChar) ? 1 : 0;
            winsWithSwitchingSameDoor += scenarioWithoutStaticDoor.contains(winningChar) ? 1 : 0;
            winsWithSwitchingDifferentDoor += scenarioWithoutRandomDoor.contains(winningChar) ? 1 : 0;

            sleep(sleep, sleepMS);
        }

        System.out.println("Wins without switching same door: " + winsWithoutSwitchingSameDoor);
        System.out.println("Wins with switching same door: " + winsWithSwitchingSameDoor);
        System.out.println("Wins without switching different door: " + winsWithoutSwitchingDifferentDoor);
        System.out.println("Wins with switching different door: " + winsWithSwitchingDifferentDoor);
    }

    private static void sleep(boolean sleep, int ms) {
        if (!sleep) {
            return;
        }
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}