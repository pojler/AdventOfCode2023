package org.pojler.day1;

import org.pojler.utils.FileReader;

import java.util.List;

public class Solution {

    public void execute () {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.getLines("day1");
        int sum = 0;
        int sumForParsedDigits = 0;
        for(String line : lines){
            String onlyNumeric = line.replaceAll("\\D", "");
            String parsedDigitsLine = stringToNumberReplacer(line);
            String onlyNumericWithParsedDigits = parsedDigitsLine.replaceAll("\\D", "");
            if(!onlyNumeric.isEmpty()){
                String number = String.valueOf(onlyNumeric.charAt(0)) + onlyNumeric.charAt(onlyNumeric.length()-1);
                sum += Integer.parseInt(number);
            }
            String parsedDigitsNumber = String.valueOf(onlyNumericWithParsedDigits.charAt(0)) + onlyNumericWithParsedDigits.charAt(onlyNumericWithParsedDigits.length()-1);
            sumForParsedDigits += Integer.parseInt(parsedDigitsNumber);
        }
        System.out.println(sum);
        System.out.println(sumForParsedDigits);
    }

    public String stringToNumberReplacer (String line) {
        String modifiedLine = line.replace("one", "one1one");
        modifiedLine = modifiedLine.replace("two", "two2two");
        modifiedLine = modifiedLine.replace("three", "three3three");
        modifiedLine = modifiedLine.replace("four", "four4four");
        modifiedLine = modifiedLine.replace("five", "five5five");
        modifiedLine = modifiedLine.replace("six", "six6six");
        modifiedLine = modifiedLine.replace("seven", "seven7seven");
        modifiedLine = modifiedLine.replace("eight", "eight8eight");
        modifiedLine = modifiedLine.replace("nine", "nine9nine");
        modifiedLine = modifiedLine.replace("zero", "zero0zero");
        return modifiedLine;
    }

}
