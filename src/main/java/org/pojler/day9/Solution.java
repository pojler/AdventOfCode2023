package org.pojler.day9;

import org.pojler.utils.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public void execute() {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.getLines("day9");
        List<List<Long>> values = lines
                .stream()
                .map(x -> Arrays.stream(x.split(" "))
                        .map(Long:: parseLong)
                        .collect(Collectors.toList()))
                .toList();
        long sumOfAproximations = 0;
        for(List<Long> row : values){
            List<List<Long>> buffer = new ArrayList<>();
            buffer.add(row);
            int j = 0;
            while(true){
                List<Long> extrapolationRow = new ArrayList<>();
                for(int i = 1; i < buffer.get(j).size(); i++) {
                    extrapolationRow.add(buffer.get(j).get(i) - buffer.get(j).get(i-1));
                }
                buffer.add(extrapolationRow);
                j++;
                List<Long> zeroes = new ArrayList<>();
                for(int i = 0; i < buffer.get(j).size(); i++) {
                    zeroes.add(0L);
                }
                if(buffer.get(j).equals(zeroes)) {
                    break;
                }
            }
            for(int i = buffer.size()-1; i > 0; i--) {
                buffer.get(i-1).add(buffer.get(i).get(buffer.get(i).size()-1) + buffer.get(i-1).get(buffer.get(i-1).size()-1));
            }
            sumOfAproximations += buffer.get(0).get(buffer.get(0).size()-1);
        }
        System.out.println(sumOfAproximations);

        List<List<Long>> reversedValues = new ArrayList<>();
        for(List<Long> value : values) {
            List<Long> reversedLine = new ArrayList<>();
            for(int i = value.size()-1 ; i > -1; i--){
                reversedLine.add(value.get(i));
            }
            reversedValues.add(reversedLine);
        }
        long sumOfReversedApproximation = 0;
        for(List<Long> row : reversedValues){
            List<List<Long>> buffer = new ArrayList<>();
            buffer.add(row);
            int j = 0;
            while(true){
                List<Long> extrapolationRow = new ArrayList<>();
                for(int i = 1; i < buffer.get(j).size(); i++) {
                    extrapolationRow.add(buffer.get(j).get(i) - buffer.get(j).get(i-1));
                }
                buffer.add(extrapolationRow);
                j++;
                List<Long> zeroes = new ArrayList<>();
                for(int i = 0; i < buffer.get(j).size(); i++) {
                    zeroes.add(0L);
                }
                if(buffer.get(j).equals(zeroes)) {
                    break;
                }
            }
            for(int i = buffer.size()-1; i > 0; i--) {
                buffer.get(i-1).add(buffer.get(i).get(buffer.get(i).size()-1) + buffer.get(i-1).get(buffer.get(i-1).size()-1));
            }
            sumOfReversedApproximation += buffer.get(0).get(buffer.get(0).size()-1);
        }

        System.out.println(sumOfReversedApproximation);
    }

}
