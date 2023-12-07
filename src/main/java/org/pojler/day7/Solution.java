package org.pojler.day7;

import org.pojler.utils.FileReader;

import java.util.*;

public class Solution {

    public void execute() {
        FileReader fileReader = new FileReader();
        List<String> lines = fileReader.getLines("day7");
        List<Hand> hands = new ArrayList<>();
        long totalWinings = 0;
        long totalWiningsWithJoker = 0;
        for (String line : lines) {
            String[] splitLine = line.split(" ");
            hands.add(new Hand(splitLine[0], Integer.parseInt(splitLine[1])));
        }
        Collections.sort(hands);
        for (int i = 0; i < hands.size(); i++) {
            totalWinings += hands.get(i).points * (i + 1);
        }
        System.out.println(totalWinings);
        Collections.sort(hands, new HandWithJokersComparator());
        for (int i = 0; i < hands.size(); i++) {
            totalWiningsWithJoker += hands.get(i).points * (i + 1);
        }
        System.out.println(totalWiningsWithJoker);
    }


    private class Hand implements Comparable<Hand> {
        public static Map<Character, Integer> CARD_VALUES_MAP = Map.ofEntries(
                Map.entry('A', 13),
                Map.entry('K', 12),
                Map.entry('Q', 11),
                Map.entry('J', 10),
                Map.entry('T', 9),
                Map.entry('9', 8),
                Map.entry('8', 7),
                Map.entry('7', 6),
                Map.entry('6', 5),
                Map.entry('5', 4),
                Map.entry('4', 3),
                Map.entry('3', 2),
                Map.entry('2', 1)
        );
        String cards;
        int points;

        //6 - five of a kind, 5 - four of a kind, 4 -full house, 3- 3 of a kind, 2 - two pair, 1 - one pair, 0 - high card
        int type;
        int jokerType;

        public Hand(String cards, int points) {
            this.cards = cards;
            this.points = points;
            type = determineType(cards);
            jokerType = determineJokerType(cards);
        }

        private int determineJokerType(String cards) {
            Map<Character, Integer> repeatedCards = new HashMap<>();
            List<Integer> cardCombination = new ArrayList<>();
            for (char card : cards.toCharArray()) {
                if (repeatedCards.containsKey(card)) {
                    repeatedCards.replace(card, repeatedCards.get(card) + 1);
                } else {
                    repeatedCards.put(card, 1);
                }
            }
            if (repeatedCards.keySet().contains('J') && repeatedCards.keySet().size() > 1) {
                int jokers = repeatedCards.get('J');
                repeatedCards.remove('J');
                int maximum = Integer.MIN_VALUE;
                char maxType = 'X';
                for (char card : repeatedCards.keySet()) {
                    if (repeatedCards.get(card) > maximum) {
                        maximum = repeatedCards.get(card);
                        maxType = card;
                    }
                }
                repeatedCards.replace(maxType, repeatedCards.get(maxType) + jokers);
            }
            for (Character card : repeatedCards.keySet()) {
                cardCombination.add(repeatedCards.get(card));
            }
            Collections.sort(cardCombination, Collections.reverseOrder());

            if (cardCombination.size() == 1) {
                return 6;
            }
            if (cardCombination.size() == 2 && cardCombination.get(0) == 4) {
                return 5;
            }
            if (cardCombination.size() == 2 && cardCombination.get(0) == 3) {
                return 4;
            }
            if (cardCombination.size() == 3 && cardCombination.get(0) == 3) {
                return 3;
            }
            if (cardCombination.size() == 3 && cardCombination.get(0) == 2) {
                return 2;
            }
            if (cardCombination.size() == 4) {
                return 1;
            }
            return 0;
        }

        private int determineType(String cards) {
            Map<Character, Integer> repeatedCards = new HashMap<>();
            List<Integer> cardCombination = new ArrayList<>();
            for (char card : cards.toCharArray()) {
                if (repeatedCards.containsKey(card)) {
                    repeatedCards.replace(card, repeatedCards.get(card) + 1);
                } else {
                    repeatedCards.put(card, 1);
                }
            }
            for (Character card : repeatedCards.keySet()) {
                cardCombination.add(repeatedCards.get(card));
            }
            Collections.sort(cardCombination, Collections.reverseOrder());

            if (cardCombination.size() == 1) {
                return 6;
            }
            if (cardCombination.size() == 2 && cardCombination.get(0) == 4) {
                return 5;
            }
            if (cardCombination.size() == 2 && cardCombination.get(0) == 3) {
                return 4;
            }
            if (cardCombination.size() == 3 && cardCombination.get(0) == 3) {
                return 3;
            }
            if (cardCombination.size() == 3 && cardCombination.get(0) == 2) {
                return 2;
            }
            if (cardCombination.size() == 4) {
                return 1;
            }
            return 0;
        }

        @Override
        public int compareTo(Hand o) {
            if (this.type > o.type) {
                return 1;
            } else if (this.type < o.type) {
                return -1;
            } else {
                for (int i = 0; i < this.cards.length(); i++) {
                    if (this.cards.charAt(i) == o.cards.charAt(i)) {
                        continue;
                    } else if (CARD_VALUES_MAP.get(this.cards.charAt(i)) > CARD_VALUES_MAP.get(o.cards.charAt(i))) {
                        return 1;
                    }
                    return -1;
                }
            }
            return 0;
        }

    }


    public class HandWithJokersComparator implements Comparator<Hand> {
        public static Map<Character, Integer> CARD_VALUES_MAP = Map.ofEntries(
                Map.entry('A', 13),
                Map.entry('K', 12),
                Map.entry('Q', 11),
                Map.entry('J', 0),
                Map.entry('T', 9),
                Map.entry('9', 8),
                Map.entry('8', 7),
                Map.entry('7', 6),
                Map.entry('6', 5),
                Map.entry('5', 4),
                Map.entry('4', 3),
                Map.entry('3', 2),
                Map.entry('2', 1)
        );

        @Override
        public int compare(Hand first, Hand second) {
            if (first.jokerType > second.jokerType) {
                return 1;
            } else if (first.jokerType < second.jokerType) {
                return -1;
            } else {
                for (int i = 0; i < first.cards.length(); i++) {
                    if (first.cards.charAt(i) == second.cards.charAt(i)) {
                        continue;
                    } else if (CARD_VALUES_MAP.get(first.cards.charAt(i)) > CARD_VALUES_MAP.get(second.cards.charAt(i))) {
                        return 1;
                    }
                    return -1;
                }
            }
            return 0;
        }
    }

}


