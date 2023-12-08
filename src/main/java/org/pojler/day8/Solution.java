package org.pojler.day8;

import org.pojler.utils.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    public void execute () {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.getLines("day8");
        Map<String, Node> nodes = new HashMap<>();
        String sequence = lines.get(0);
        for(int i = 2; i < lines.size(); i++) {
            String[] nodeSplit = lines.get(i).split("\s=\s\\(|,\s|\\)");
            nodes.put(nodeSplit[0], new Node(nodeSplit[1],nodeSplit[2]));
        }
        int steps = 0;
        List<String> checkedNodes = new ArrayList<>();
        for(String nodeName : nodes.keySet()) {
            if(nodeName.charAt(2) == 'A') {
                checkedNodes.add(nodeName);
            }
        }
        String current = "AAA";
        boolean isZZZ = false;
        boolean allInPosition = false;

        for (int i = 0; i<sequence.length(); i++) {
            char currentDirection = sequence.charAt(i);
            if(!isZZZ){

                Node currentNode = nodes.get(current);
                if(currentDirection == 'L') {
                    current = currentNode.left;
                    steps ++;
                }
                else {
                    current = currentNode.right;
                    steps ++;
                }
                if(current.equals("ZZZ")) {
                    isZZZ = true;
                }
            }
            if(isZZZ){
                break;
            }
            if(i == sequence.length() -1) {
                i = -1;
            }
        }
        System.out.println(steps);
        findShortestPathForEveryNode(checkedNodes, nodes,sequence);
    }

    public void findShortestPathForEveryNode(List<String> nodeList, Map<String, Node> nodes, String sequence) {
        List<Integer> shortestPaths = new ArrayList<>();
        for(String node : nodeList) {
            int steps = 0;
            String current = node;
            for(int i = 0; i < sequence.length(); i++) {
                char currentDirection = sequence.charAt(i);

                Node currentNode = nodes.get(current);
                if(currentDirection == 'L') {
                    current = currentNode.left;
                    steps ++;
                }
                else {
                    current = currentNode.right;
                    steps ++;
                }
                if(current.charAt(2) == 'Z') {
                    break;
                }
                if(i == sequence.length()-1) {
                    i = -1;
                }
            }
            shortestPaths.add(steps);
        }
        long currentLcm = shortestPaths.get(0);
        for(int i = 1; i < shortestPaths.size(); i++) {
            currentLcm = lowestCommonMultiple(currentLcm, shortestPaths.get(i));
        }
        System.out.println(currentLcm);
    }

    public static long lowestCommonMultiple(long steps1, long steps2) {

        if( steps1 == steps2) {
            return steps1;
        }

        long higherSteps;
        long lowerSteps;
        if(steps1 > steps2) {
            higherSteps = steps1;
            lowerSteps = steps2;
        }
        else {
            higherSteps = steps2;
            lowerSteps = steps1;
        }

        long lcm = higherSteps;
        while (lcm % lowerSteps != 0) {
            lcm += higherSteps;
        }
        return lcm;
    }


    private boolean checkIfAllInDestination(List<String> currentPositions) {
        System.out.println(currentPositions);
        for(String node : currentPositions) {
            if(node.charAt(2) != 'Z') {
                return false;
            }
        }
        return true;
    }

    private class Node {
        String left;
        String right;

        public Node (String left, String right) {
            this.left = left;
            this.right = right;
        }
    }

}
