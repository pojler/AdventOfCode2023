package org.pojler.day4;

import org.pojler.utils.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public void execute() {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.getLines("day4");
        int sumOfWinningTicket = 0;
        int sumOfAllScratchCards = 0;
        boolean addPoints = false;
        int[] couponMultipliers = new int[lines.size()];
        Arrays.fill(couponMultipliers, 1);

        for(int i = 0; i < lines.size(); i++) {
            String[] recordSplit = lines.get(i).split(": |\s\\|\s");
            List<Integer> coupon = getCoupon(recordSplit[2].strip());
            Set<Integer> draw = getDraw(recordSplit[1].strip());
            int currentPoints = 1;
            int matches = 1;
            for(Integer number : coupon) {
                if (draw.contains(number)) {
                    if(addPoints) {
                        currentPoints *= 2;
                        matches++;
                    }
                    addPoints = true;
                }
            }
            if(addPoints) {
                sumOfWinningTicket += currentPoints;
                for(int j = i+1; j <=  i+matches; j++) {
                    couponMultipliers[j]+= couponMultipliers[i];
                }

            }
            addPoints = false;
        }
        for(int i = 0; i < couponMultipliers.length; i++){
            sumOfAllScratchCards += couponMultipliers[i];
        }
        System.out.println(sumOfWinningTicket);
        System.out.println(sumOfAllScratchCards);
    }

    public List<Integer> getCoupon(String coupon) {
        return Arrays.stream(coupon.split("\s+")).map(String::strip).map(Integer::parseInt).toList();
    }

    public Set<Integer> getDraw (String draw) {
        return Arrays.stream(draw.split("\s+")).map(String::strip).map(Integer::parseInt).collect(Collectors.toSet());
    }


}
