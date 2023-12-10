package org.pojler.day10;

import org.pojler.utils.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solution {


    public void execute() {
        FileReader fileReader = new FileReader();
        char[][] pipeMap = fileReader.linesAsCharArray("day10");
        int[][][] loopsMap = new int[4][pipeMap.length*2][pipeMap[0].length*2];

        int[] startPoint = findS(pipeMap);
        int[][] entryPoints = findEntryPoints(startPoint, pipeMap);
        for(int i = 0; i < entryPoints.length; i++) {
            System.out.println(Arrays.toString(entryPoints[i]));
        }
        List<Integer> stepCount = new ArrayList<>();
        int currentLoop = 0;
        for(int i = 0; i < entryPoints.length; i++) {
            int[] currentPoints = entryPoints[i];
            int yOffset = currentPoints[0] - startPoint[0];
            int xOffset = currentPoints[1] - startPoint[1];
            loopsMap[currentLoop][currentPoints[0]*2-yOffset][currentPoints[1]*2-xOffset] = 9;
            int steps = 0;
            while(true){
                loopsMap[currentLoop][currentPoints[0]*2][currentPoints[1]*2] = 9;
                if(pipeMap[currentPoints[0]][currentPoints[1]] == '-'){
                    steps++;
                    loopsMap[currentLoop][currentPoints[0]*2][currentPoints[1]*2 + xOffset] = 9;
                }
                if(pipeMap[currentPoints[0]][currentPoints[1]] == '|'){
                    steps++;
                    loopsMap[currentLoop][currentPoints[0]*2 + yOffset][currentPoints[1]*2] = 9;
                }
                if(pipeMap[currentPoints[0]][currentPoints[1]] == '7'){
                    yOffset = yOffset < 0? 0:1;
                    xOffset = xOffset > 0? 0:-1;
                    steps++;
                    loopsMap[currentLoop][currentPoints[0]*2 + yOffset][currentPoints[1]*2 + xOffset] = 9;
                }
                if(pipeMap[currentPoints[0]][currentPoints[1]] == 'J'){
                    xOffset = xOffset > 0? 0:-1;
                    yOffset = yOffset > 0? 0:-1;
                    steps++;
                    loopsMap[currentLoop][currentPoints[0]*2 + yOffset][currentPoints[1]*2 + xOffset] = 9;
                }
                if(pipeMap[currentPoints[0]][currentPoints[1]] == 'L'){
                    yOffset = yOffset > 0? 0:-1;
                    xOffset = xOffset < 0? 0:1;
                    steps ++;
                    loopsMap[currentLoop][currentPoints[0]*2 + yOffset][currentPoints[1]*2 + xOffset] = 9;
                }
                if(pipeMap[currentPoints[0]][currentPoints[1]] == 'F'){
                    xOffset = xOffset < 0? 0:1;
                    yOffset = yOffset < 0? 0:1;
                    steps++;
                    loopsMap[currentLoop][currentPoints[0]*2 + yOffset][currentPoints[1]*2 + xOffset] = 9;
                }
                if(pipeMap[currentPoints[0]][currentPoints[1]] == 'S') {
                    break;
                }
                currentPoints[0] = currentPoints[0] + yOffset;
                currentPoints[1] = currentPoints[1] + xOffset;

            }
            currentLoop ++;
            stepCount.add(steps);
        }
        int maxLoop = Integer.MIN_VALUE;
        int maxIndex = 0;
        for(int i = 0; i < stepCount.size(); i++) {
            if (stepCount.get(i) > maxLoop){
                maxLoop = stepCount.get(i);
                maxIndex = i;
            }
        }
        if(stepCount.get(0) % 2 == 0){
            System.out.println(maxLoop/2);
        }
        else {
            System.out.println(maxLoop/2 + 1);
        }
        List<Integer> elementsInLoop =  countElementsInsideLoop(loopsMap);
        System.out.println(elementsInLoop.get(maxIndex));
    }
    public int[] findS (char[][] pipeMap) {
        int[] result = new int[2];
        for(int i = 0; i < pipeMap.length; i++) {
            for(int j = 0; j < pipeMap[i].length; j++){
                if(pipeMap[i][j] == 'S') {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }
    public int[][] findEntryPoints (int[]startPoint, char[][] pipeMap) {
        int[][] entryPoints = new int[4][2];
        int i = 0;
        if(startPoint[0] != 0 && pipeMap[startPoint[0]-1][startPoint[1]] != '.') {
            entryPoints[i][0] = startPoint[0]-1;
            entryPoints[i][1] = startPoint[1];
            i++;
        }
        if(startPoint[1] != 0 && pipeMap[startPoint[0]][startPoint[1]-1] != '.') {
            entryPoints[i][0] = startPoint[0];
            entryPoints[i][1] = startPoint[1]-1;
            i++;
        }
        if(startPoint[0] != pipeMap.length-1 && pipeMap[startPoint[0]+1][startPoint[1]] != '.') {
            entryPoints[i][0] = startPoint[0]+1;
            entryPoints[i][1] = startPoint[1];
            i++;
        }if(startPoint[1] != pipeMap[0].length-1 && pipeMap[startPoint[0]][startPoint[1]+1] != '.') {
            entryPoints[i][0] = startPoint[0];
            entryPoints[i][1] = startPoint[1]+1;
            i++;
        }
        int[][] result = new int[i][2];
        for(int j = 0; j < result.length; j++){
            result[j] = entryPoints[j];
        }
        return result;
    }

    private List<Integer> countElementsInsideLoop (int[][][] loopMap) {
        List<Integer> elements = new ArrayList<>();
        for(int k = 0; k < loopMap.length; k++) {
            for(int i = 0; i < loopMap[k].length; i++) {
                for(int j = 0; j < loopMap[k][i].length; j++){
                    if(loopMap[k][i][j] == 9) {
                        incrementNerbyFields(k, i, j, loopMap);
                    }
                }
            }
        }
        while(true) {
            for(int k = 0; k < loopMap.length; k++) {
                for(int i = 0; i < loopMap[k].length; i++) {
                    for(int j = 0; j < loopMap[k][i].length; j++){
                        if(loopMap[k][i][j] <4) {
                            zeroThisAndNearby(k, i, j, loopMap);
                        }
                    }
                }
            }
            if(validate(loopMap)) {
                break;
            }
        }
        for(int k = 0; k < loopMap.length; k++) {
            int elementsInLoop = 0;
            for(int i = 0; i < loopMap[k].length; i++) {
                for(int j = 0; j < loopMap[k][i].length; j++){
                    if(loopMap[k][i][j] == 4 && i%2 == 0 && j%2 == 0) {
                        elementsInLoop++;
                    }
                }
            }
            elements.add(elementsInLoop);
        }
        return elements;
    }

    private boolean validate(int[][][] loopMap) {
        for(int k = 0; k < loopMap.length; k++) {
            for(int i = 0; i < loopMap[k].length; i++) {
                for(int j = 0; j < loopMap[k][i].length; j++){
                    if(loopMap[k][i][j] == 4) {
                        if(i != 0) {
                            if(loopMap[k][i-1][j] < 4){
                                return false;
                            }
                        }
                        if(j != 0) {
                            if(loopMap[k][i][j-1]  < 4){
                                return false;
                            }
                        }
                        if(i != loopMap[k].length-1) {
                            if(loopMap[k][i+1][j] < 4){
                                return false;
                            }
                        }
                        if(j != loopMap[k][0].length-1) {
                            if(loopMap[k][i][j+1]  < 4){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void zeroThisAndNearby(int k, int i, int j, int[][][] loopMap) {
        loopMap[k][i][j] = 0;
        if(i != 0) {
            if(loopMap[k][i-1][j] != 9){
                loopMap[k][i-1][j] = 0;
            }
        }
        if(j != 0) {
            if(loopMap[k][i][j-1] != 9){
                loopMap[k][i][j-1] = 0;
            }
        }
        if(i != loopMap[k].length-1) {
            if(loopMap[k][i+1][j] != 9){
                loopMap[k][i+1][j] = 0;
            }
        }
        if(j != loopMap[k][0].length-1) {
            if(loopMap[k][i][j+1] != 9){
                loopMap[k][i][j+1] = 0;
            }
        }
    }

    private void incrementNerbyFields(int k, int i, int j, int[][][] loopMap) {
        for(int x = i-1; x > -1; x--) {
            if(loopMap[k][x][j] != 9) {
                loopMap[k][x][j]++;
            }
            else {
                break;
            }
        }
        for(int x = j-1; x > -1; x--) {
            if(loopMap[k][i][x] != 9) {
                loopMap[k][i][x]++;
            }
            else {
                break;
            }
        }
        for(int x = i+1 ; x < loopMap[k].length; x++){
            if(loopMap[k][x][j] != 9) {
                loopMap[k][x][j]++;
            }
            else {
                break;
            }
        }
        for(int x = j+1 ; x < loopMap[k][0].length; x++){
            if(loopMap[k][i][x] != 9) {
                loopMap[k][i][x]++;
            }
            else {
                break;
            }
        }
    }

}
