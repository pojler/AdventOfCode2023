package org.pojler.day03;

import org.pojler.utils.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    public void execute() {
        Map<List<Integer>, List<Integer>> gearsWithAdjecent = new HashMap<>();
        FileReader fileReader = new FileReader();
        char[][] lines = fileReader.linesAsCharArray("day3");
        List<Integer> indicesOfNumber = new ArrayList<>();
        boolean thereIsANumber = false;
        int sum = 0;
        long sumOfGears = 0;
        StringBuilder numberBuilder;
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length; j++) {
                if (Character.isDigit(lines[i][j])) {
                    indicesOfNumber.add(j);
                    thereIsANumber = true;
                }
                if (thereIsANumber && (!Character.isDigit(lines[i][j]) || j == lines[i].length - 1)) {

                        numberBuilder= new StringBuilder();
                        for(Integer index:indicesOfNumber) {
                            numberBuilder.append(lines[i][index]);
                        }
                    if (validateIfAdjecent(i, indicesOfNumber, lines, gearsWithAdjecent, Integer.parseInt(numberBuilder.toString()))) {
                        sum += Integer.parseInt(numberBuilder.toString());
                        thereIsANumber = false;
                        indicesOfNumber = new ArrayList<>();
                    }
                    else {
                        thereIsANumber = false;
                        indicesOfNumber = new ArrayList<>();
                    }
                }
            }
        }
        System.out.println(sum);
        for(List<Integer> key : gearsWithAdjecent.keySet()) {
            List<Integer> currentGearAdjutants = gearsWithAdjecent.get(key);
            System.out.println(key + " values: " + currentGearAdjutants);
            if(currentGearAdjutants.size() > 1){
                sumOfGears += currentGearAdjutants.get(0) * currentGearAdjutants.get(1);
            }
        }
        System.out.println(sumOfGears);
    }

    private boolean validateIfAdjecent(int i, List<Integer> indicesOfNumber, char[][] lines, Map<List<Integer>, List<Integer>> gears, int currentNumber) {
        boolean isAdjecent = false;
        List<Integer> currentCoordinates;
        if (i != 0) {
            for (Integer index : indicesOfNumber) {
                char current = lines[i - 1][index];
                if (current != '.' ) {
                    if(current == '*') {
                        currentCoordinates = List.of(i-1, index);
                        if(gears.containsKey(currentCoordinates)){
                            gears.get(currentCoordinates).add(currentNumber);
                        }
                        else {
                            List<Integer> numbers = new ArrayList<>();
                            numbers.add(currentNumber);
                            gears.put(currentCoordinates, numbers);
                        }
                    }
                    isAdjecent =  true;
                }
            }
        }

        if (i != lines.length - 1) {
            for (Integer index : indicesOfNumber) {
                char current = lines[i + 1][index];
                if (current != '.')  {
                    if(current == '*') {
                        currentCoordinates = List.of(i+1, index);
                        if(gears.containsKey(currentCoordinates)){
                            gears.get(currentCoordinates).add(currentNumber);
                        }
                        else {
                            List<Integer> numbers = new ArrayList<>();
                            numbers.add(currentNumber);
                            gears.put(currentCoordinates, numbers);
                        }
                    }
                    isAdjecent = true;
                }
            }
        }

        if (indicesOfNumber.get(0) != 0 && lines[i][indicesOfNumber.get(0) - 1] != '.') {

            if(lines[i][indicesOfNumber.get(0) - 1] == '*') {
                currentCoordinates = List.of(i, indicesOfNumber.get(0) - 1);
                if(gears.containsKey(currentCoordinates)){
                    gears.get(currentCoordinates).add(currentNumber);
                }
                else {
                    List<Integer> numbers = new ArrayList<>();
                    numbers.add(currentNumber);
                    gears.put(currentCoordinates, numbers);
                }
            }
            isAdjecent = true;
        }

        if (indicesOfNumber.get(indicesOfNumber.size() - 1) != lines[i].length - 1 && lines[i][indicesOfNumber.get(indicesOfNumber.size() - 1) + 1] != '.') {
            if(lines[i][indicesOfNumber.get(indicesOfNumber.size() - 1) + 1] == '*') {
                currentCoordinates = List.of(i,indicesOfNumber.get(indicesOfNumber.size() - 1) + 1);
                if(gears.containsKey(currentCoordinates)){
                    gears.get(currentCoordinates).add(currentNumber);
                }
                else {
                    List<Integer> numbers = new ArrayList<>();
                    numbers.add(currentNumber);
                    gears.put(currentCoordinates, numbers);
                }
            }
            isAdjecent =  true;
        }

        if (i != 0 && indicesOfNumber.get(0) != 0) {
            char current = lines[i-1][indicesOfNumber.get(0)  -1];
            if(current != '.' ){
                if(current == '*') {
                    currentCoordinates = List.of(i-1, indicesOfNumber.get(0)  -1);
                    if(gears.containsKey(currentCoordinates)){
                        gears.get(currentCoordinates).add(currentNumber);
                    }
                    else {
                        List<Integer> numbers = new ArrayList<>();
                        numbers.add(currentNumber);
                        gears.put(currentCoordinates, numbers);
                    }
                }
                isAdjecent = true;
            }
        }

        if (i != 0 && indicesOfNumber.get(indicesOfNumber.size() - 1) != lines[i].length - 1) {
            char current = lines[i - 1][indicesOfNumber.get(indicesOfNumber.size() - 1) + 1];
            if (current != '.') {
                if (current == '*') {
                    currentCoordinates = List.of(i - 1, indicesOfNumber.get(indicesOfNumber.size() - 1) + 1);
                    if (gears.containsKey(currentCoordinates)) {
                        gears.get(currentCoordinates).add(currentNumber);
                    } else {
                        List<Integer> numbers = new ArrayList<>();
                        numbers.add(currentNumber);
                        gears.put(currentCoordinates, numbers);
                    }
                    isAdjecent = true;
                }
                isAdjecent = true;
            }
        }

        if (i != lines.length - 1 &&  indicesOfNumber.get(0) != 0) {
            char current = lines[i + 1][indicesOfNumber.get(0) - 1];
            if (current != '.') {
                if (current == '*') {
                    currentCoordinates = List.of(i + 1, indicesOfNumber.get(0) - 1);
                    if (gears.containsKey(currentCoordinates)) {
                        gears.get(currentCoordinates).add(currentNumber);
                    } else {
                        List<Integer> numbers = new ArrayList<>();
                        numbers.add(currentNumber);
                        gears.put(currentCoordinates, numbers);
                    }
                }
                isAdjecent = true;
            }
        }

        if (i != lines.length - 1 && indicesOfNumber.get(indicesOfNumber.size() - 1) != lines[i].length - 1) {
            char current = lines[i + 1][indicesOfNumber.get(indicesOfNumber.size() - 1) + 1];
            if (current != '.') {
                if (current == '*') {
                    currentCoordinates = List.of(i + 1, indicesOfNumber.get(indicesOfNumber.size() - 1) + 1);
                    if (gears.containsKey(currentCoordinates)) {
                        gears.get(currentCoordinates).add(currentNumber);
                    } else {
                        List<Integer> numbers = new ArrayList<>();
                        numbers.add(currentNumber);
                        gears.put(currentCoordinates, numbers);
                    }
                }
                isAdjecent = true;
            }
        }

        return isAdjecent;
    }

}
