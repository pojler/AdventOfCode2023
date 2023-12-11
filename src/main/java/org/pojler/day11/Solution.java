package org.pojler.day11;

import org.pojler.utils.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public void execute () {
        FileReader fileReader = new FileReader();
        char[][] universe = fileReader.linesAsCharArray("day11");
        char[][] expandedUniverse = expandUniverse(universe);
        for (int i = 0; i < expandedUniverse.length; i++) {
            for(int j = 0; j < expandedUniverse[i].length; j++) {
                System.out.print(expandedUniverse[i][j]);
            }
            System.out.println();
        }
        List<Integer[]> galaxiesCoordinates = findGalaxyCoordinates(expandedUniverse);
        int result = 0;
        for (int i = 0; i < galaxiesCoordinates.size(); i++) {
            for (int j = i+1; j < galaxiesCoordinates.size(); j++){
                result += stepsBetween(galaxiesCoordinates.get(i), galaxiesCoordinates.get(j));
            }
        }
        System.out.println(result);
        List<Integer[]> notExpandedGalaxiesCoordinates = findGalaxyCoordinates(universe);
        List<Integer> emptyRows = emptyRowsCoordinates(universe);
        List<Integer> emptyColumns = emptyColumns(universe);
        long expandedResult = 0L;
        for (int i = 0; i < notExpandedGalaxiesCoordinates.size(); i++) {
            for (int j = i+1; j < notExpandedGalaxiesCoordinates.size(); j++){
                expandedResult += stepsBetweenWithSuperExpansion(notExpandedGalaxiesCoordinates.get(i), notExpandedGalaxiesCoordinates.get(j), emptyRows, emptyColumns);
            }
        }
        System.out.println(expandedResult);

    }

    private long stepsBetweenWithSuperExpansion (Integer[] aCoordinates, Integer[] bCoordinates, List<Integer> emptyRows, List<Integer> emptyColumns) {
        int currentRowMultiplier = 0;
        int currentColumnMultiplier = 0;
        int bRowMultiplier = 0;
        int bColumnMultiplier = 0;
        for(int i = 0; i < emptyRows.size(); i++){
            if(aCoordinates[0] > emptyRows.get(i)) {
                currentRowMultiplier++;
            }
            if(bCoordinates[0] > emptyRows.get(i)) {
                bRowMultiplier++;
            }
        }
        for(int i = 0; i < emptyColumns.size(); i++){
            if(aCoordinates[1] > emptyColumns.get(i)) {
                currentColumnMultiplier++;
            }
            if(bCoordinates[1] > emptyColumns.get(i)) {
                bColumnMultiplier++;
            }
        }
        long deltaY =  Math.abs((bCoordinates[1] +( bColumnMultiplier * 1000000) ) - (aCoordinates[1] + (currentColumnMultiplier*1000000)));
        long deltaX = Math.abs((bCoordinates[0] + ( bRowMultiplier *1000000 )) - (aCoordinates[0] + (currentRowMultiplier*1000000)));
        return deltaX + deltaY - Math.abs(bRowMultiplier- currentRowMultiplier) - Math.abs(bColumnMultiplier- currentColumnMultiplier);
    }

    List<Integer> emptyRowsCoordinates (char universe [][]) {
        List<Integer> emptyRows = new ArrayList<>();
        for (int i = 0; i < universe.length; i++) {
            boolean notContainsAnything = true;
            for(int j = 0; j < universe[i].length; j++) {
                if(universe[i][j] == '#'){
                    notContainsAnything = false;
                    break;
                }
            }
            if(notContainsAnything) {
                emptyRows.add(i);
            }
        }
        return emptyRows;
    }
    List<Integer> emptyColumns (char universe [][]) {
        List<Integer> emptyColumns = new ArrayList<>();
        for (int i = 0; i < universe[0].length; i++) {
            boolean notContainsAnything = true;
            for(int j = 0; j < universe.length; j++) {
                if(universe[j][i] == '#'){
                    notContainsAnything = false;
                    break;
                }
            }
            if(notContainsAnything) {
                emptyColumns.add(i);
            }
        }
        return emptyColumns;
    }

    private List<Integer[]> findGalaxyCoordinates(char[][] expandedUniverse) {
        List<Integer[]> result = new ArrayList<>();
        for(int i = 0; i< expandedUniverse.length; i++) {
            for (int j = 0; j < expandedUniverse[i].length; j++) {
                if(expandedUniverse[i][j] == '#') {
                    Integer[] coordinates = {i, j};
                    result.add(coordinates);
                }
            }
        }
        return result;
    }

    private int stepsBetween (Integer[] aCoordinates, Integer[] bCoordinates) {
        int deltaY =  Math.abs(bCoordinates[0] - aCoordinates[0]);
        int deltaX = Math.abs(bCoordinates[1] - aCoordinates[1]);
        return deltaX + deltaY;
    }

    private char[][] expandUniverse(char[][] universe) {
        int emptyRows = 0;
        for (int i = 0; i < universe.length; i++) {
            boolean notContainsAnything = true;
            for(int j = 0; j < universe[i].length; j++) {
                if(universe[i][j] == '#'){
                    notContainsAnything = false;
                    break;
                }
            }
            if(notContainsAnything) {
                emptyRows++;
            }
        }

        char[][] rowExpandedUniverse = new char[universe.length+emptyRows][universe[0].length];
        int offset = 0;
        for(int i = 0; i < universe.length; i++) {
            boolean notContainsAnything = true;
            for(int j = 0; j < universe[i].length; j++) {
                if(universe[i][j] == '#'){
                    notContainsAnything = false;
                    break;
                }
            }
            if(notContainsAnything) {
                emptyRows++;
                rowExpandedUniverse[i+offset] = universe[i];
                offset++;
                rowExpandedUniverse[i+offset] = universe[i];
            }
            else {
                rowExpandedUniverse[i+offset] = universe[i];
            }
        }
        int emptyColumns = 0;
        for (int i = 0; i < rowExpandedUniverse[0].length; i++) {
            boolean notContainsAnything = true;
            for (int j = 0; j < rowExpandedUniverse.length; j++) {
                if(rowExpandedUniverse[j][i] == '#'){
                    notContainsAnything = false;
                    break;
                }
            }
            if(notContainsAnything) {
                emptyColumns++;
            }
        }

        char[][] result = new char[rowExpandedUniverse.length][rowExpandedUniverse[0].length+emptyColumns];
        for(int i = 0; i < result.length; i++) {
            Arrays.fill(result[i], '.');
        }
        int columnOffset = 0;

        for(int i = 0; i < rowExpandedUniverse[0].length; i++) {
            boolean notContainsAnything = true;
            for(int j = 0; j < rowExpandedUniverse.length; j++) {
               if(rowExpandedUniverse[j][i] == '#'){
                   notContainsAnything = false;
               }
               result[j][i+columnOffset] = rowExpandedUniverse[j][i];
            }
            if(notContainsAnything) {
                columnOffset++;
                for(int j = 0; j < rowExpandedUniverse.length; j++) {
                    result[j][i+columnOffset] = '.';
                }
            }
        }
        return result;
    }
}
