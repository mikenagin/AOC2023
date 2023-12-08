import java.util.*;
import java.util.stream.Collectors;

public class AOC07 {

    private List<String> input;
    public List<Character> cardValues;
    public final String highCard = "High Card";
    public final String pair = "Pair";
    public final String twoPairs = "Two Pairs";
    public final String threeOfAKind = "Three of a Kind";
    public final String fullHouse = "Full House";
    public final String fourOfAKind = "Four of a Kind";
    public final String fiveOfAKind = "Five of a Kind";
    public enum typesOfHands {
        highCard,
        pair,
        twoPairs,
        threeOfAKind,
        fullHouse,
        fourOfAKind,
        fiveOfAKind
    }

    public List<typesOfHands> orderOfHands;
    private List<HandOfCards> handOfCardsList;

    public AOC07(List<String> input) {
        this.input = input;
        orderOfHands = List.of(typesOfHands.highCard, typesOfHands.pair, typesOfHands.twoPairs,typesOfHands.threeOfAKind,
                typesOfHands.fullHouse, typesOfHands.fourOfAKind, typesOfHands.fiveOfAKind);
        cardValues = List.of('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');
        handOfCardsList = readInput();
    }

    public Integer sumOfHands() {
        Integer sumOfHands = 0;
        List<HandOfCards> sortedHandOfCardsList = handOfCardsList.stream().sorted().collect(Collectors.toList());

        for (int i=1; i<=sortedHandOfCardsList.size(); i++) {
            sumOfHands += i*sortedHandOfCardsList.get(i-1).getBet();
            System.out.println(String.format("%d * %d [%d] {%s}", i, sortedHandOfCardsList.get(i-1).bet, sumOfHands, sortedHandOfCardsList.get(i-1)));
        }
        return sumOfHands;
    }

    private List<HandOfCards> readInput() {
        List<HandOfCards> inputRead = new ArrayList<>();

        input.stream().forEach(s -> inputRead.add(
                new HandOfCards(s.split(" ")[0].trim(), Integer.parseInt(s.split(" ")[1].trim()))));

        return inputRead;
    }

    class HandOfCards implements Comparable<HandOfCards> {

        private String cards;
        private typesOfHands typeOfHand;
        private String sortedHand;
        private Integer bet;
        private List<Character> handList;

        public HandOfCards(String cards, Integer bet) {
            this.cards = cards;
            this.sortedHand = sortedHand(cards);
            this.typeOfHand = typeOfHand(sortedHand);
            this.bet = bet;
        }

        public String getCards() { return cards; }
        public String getSortedHand() { return sortedHand; }
        public typesOfHands getTypeOfHand() { return typeOfHand; }
        public Integer getBet() { return bet; }

        private String sortedHand(String unsortedHand) {
            handList = unsortedHand.chars().mapToObj(l -> (char) l).collect(Collectors.toList());

            handList.sort(Comparator.comparingInt(l -> cardValues.indexOf(l)));
            return handList.stream().map(String::valueOf).collect(Collectors.joining());
        }

        private typesOfHands typeOfHand(String sortedHand) {
            Map<Character, Integer> cardCounts = new HashMap<>();

            handList.forEach( c -> {
                if (cardCounts.containsKey(c)) {
                    cardCounts.put(c, cardCounts.get(c)+1);
                } else {
                    cardCounts.put(c, 1);
                }
            });

            Integer jCount = cardCounts.containsKey('J') ? cardCounts.get('J') : 0;
            cardCounts.put('J', 0);
            Integer maxNonJAlikeCount = cardCounts.values().stream().mapToInt(i -> i).max().getAsInt();

            if ((maxNonJAlikeCount == 5) || ((maxNonJAlikeCount + jCount) == 5))  {
                return typesOfHands.fiveOfAKind;
            } else if ((maxNonJAlikeCount == 4) || ((maxNonJAlikeCount + jCount) == 4)) {
                return typesOfHands.fourOfAKind;
            } else if ((maxNonJAlikeCount == 3) || ((maxNonJAlikeCount + jCount) == 3)) {
                // J4433 is a full house   maxNonJAlikeCount = 2; jCount = 1
                // J4423 is not a full house
                if ((cardCounts.values().contains(2) && (jCount != 1)) ||
                        (cardCounts.values().stream().filter(i -> i.equals(2)).count() == 2) && (jCount == 1)) return typesOfHands.fullHouse;

                return typesOfHands.threeOfAKind;
            } else if ((maxNonJAlikeCount == 2) || (jCount == 1)) {
                if (maxNonJAlikeCount == 2)  {
                    if (cardCounts.values().stream().filter(i -> i.equals(2)).count() == 2) return typesOfHands.twoPairs;
                }

                return typesOfHands.pair;
            }

            return typesOfHands.highCard;
        }

        @Override
        public int compareTo(HandOfCards hand) {
            if (orderOfHands.indexOf(this.typeOfHand) > orderOfHands.indexOf(hand.typeOfHand)) return 1;
            if (orderOfHands.indexOf(this.typeOfHand) < orderOfHands.indexOf(hand.typeOfHand)) return -1;

            for (int i=0; i<5; i++) {
                if (cardValues.indexOf(this.getCards().charAt(i)) > cardValues.indexOf(hand.getCards().charAt(i))) return 1;
                if (cardValues.indexOf(this.getCards().charAt(i)) < cardValues.indexOf(hand.getCards().charAt(i))) return -1;
            }

            return 0;
        }

        public String toString() {
            return String.format("Cards %s, sorted %s, type %s, bet %d", cards, sortedHand, typeOfHand, bet);
        }

    }


}