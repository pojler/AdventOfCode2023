package org.pojler.day2;

import org.pojler.utils.FileReader;

import java.util.List;

public class Solution {

    private static final int MAX_GREEEN = 13;
    private static final int MAX_BLUE = 14;
    private static final int MAX_RED = 12;
    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";

    public void execute() {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.getLines("day2");
        int idSum = 0;
        int sumOfColorPowers = 0;
        int minRed = Integer.MIN_VALUE;
        int minGreen = Integer.MIN_VALUE;
        int minBlue = Integer.MIN_VALUE;
        boolean notPossibleFlag = false;
        for (String line : lines) {
            String[] lineSplit = line.split("(; |: )");
            for (int i = 1; i < lineSplit.length; i++) {
                String[] recordSplit = lineSplit[i].split(", ");
                for (int j = 0; j < recordSplit.length; j++) {
                    String[] pickedBallsSplit = recordSplit[j].split(" ");
                    int ballCount = Integer.parseInt(pickedBallsSplit[0]);
                    String ballColor = pickedBallsSplit[1];
                    if (ballColor.equals(RED)) {
                        if (ballCount > minRed) {
                            minRed = ballCount;
                        }
                    }
                    if (ballColor.equals(GREEN)) {
                        if (ballCount > minGreen) {
                            minGreen = ballCount;
                        }
                    }
                    if (ballColor.equals(BLUE)) {
                        if (ballCount > minBlue) {
                            minBlue = ballCount;
                        }
                    }
                    if (ballColor.equals(RED) && ballCount > MAX_RED ||
                            ballColor.equals(BLUE) && ballCount > MAX_BLUE ||
                            ballColor.equals(GREEN) && ballCount > MAX_GREEEN) {
                        notPossibleFlag = true;
                    }
                }
            }
            sumOfColorPowers += minRed * minBlue * minGreen;
            minRed = Integer.MIN_VALUE;
            minGreen = Integer.MIN_VALUE;
            minBlue = Integer.MIN_VALUE;
            if (!notPossibleFlag) {
                int gameId = Integer.parseInt(lineSplit[0].split(" ")[1]);
                idSum += gameId;
            }
            notPossibleFlag = false;
        }
        System.out.println(idSum);
        System.out.println(sumOfColorPowers);
    }
}
