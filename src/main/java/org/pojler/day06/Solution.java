package org.pojler.day06;

import org.pojler.utils.FileReader;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public void execute() {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.getLines("day6");
        List<Integer> time = new ArrayList<>();
        List<Integer> distance = new ArrayList<>();
        String[] timeStrings = lines.get(0).split("\s+");
        String[] distanceStrings = lines.get(1).split("\s+");
        StringBuilder timeBuilder = new StringBuilder();
        StringBuilder distanceBuilder = new StringBuilder();
        for(int i = 1; i < timeStrings.length; i++) {
            time.add(Integer.parseInt(timeStrings[i]));
            distance.add(Integer.parseInt(distanceStrings[i]));
            timeBuilder.append(timeStrings[i]);
            distanceBuilder.append(distanceStrings[i]);
        }
        long combinedTime = Long.parseLong(timeBuilder.toString());
        long combinedDistance = Long.parseLong(distanceBuilder.toString());
        int possibilities;
        int possibilitiesCombined = 0;
        List<Integer> possibilitiesList = new ArrayList<>();
        for(int i = 0; i < time.size(); i++) {
            possibilities = 0;
            for(int j = 0; j < time.get(i); j++) {
                if(j * (time.get(i) -j) > distance.get(i)) {
                    possibilities ++;
                }
            }
            possibilitiesList.add(possibilities);
        }
        for(long j = 0; j < combinedTime; j++) {
            if(j * (combinedTime -j) > combinedDistance) {
                possibilitiesCombined ++;
            }
        }
        int result = 1;
        for(Integer possibility : possibilitiesList) {
            result *= possibility;
        }
        System.out.println(result);
        System.out.println(possibilitiesCombined);
    }

}
