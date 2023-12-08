import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AOC08 {

    private List<String> input;
    private List<Boolean> moves;
    private Map<String, List<String>> maps;
    private List<Long> endCounts;
    private List<String> startingPoints;

    public AOC08(List<String> input) {
        this.input = input;
        readInput();
    }

    public Integer stepsTakenPart1() {
        Boolean reachedEnd = false;
        Integer movesTaken = 0;
        Integer moveIndex = 0;
        String currentLocation = "AAA";

        while(!reachedEnd) {
            Boolean nextMove = moves.get(moveIndex);
            movesTaken++;
            System.out.print(movesTaken + " ");

            currentLocation = nextMove ? maps.get(currentLocation).get(0) : maps.get(currentLocation).get(1);

            if (currentLocation.equals("ZZZ")) reachedEnd = true;

            moveIndex++;
            if (moveIndex == moves.size()) moveIndex = 0;

        }

        return movesTaken;
    }

    public Long stepsTakenPart2() {
        Boolean reachedEnd = false;
        endCounts = new ArrayList<>();

        startingPoints = maps.keySet().stream().filter(x -> x.charAt(2) == 'A').collect(Collectors.toList());

        startingPoints.parallelStream().forEach(start -> {
            Long movesTaken = 0L;
            Integer moveIndex = 0;
            Boolean finished = false;
            String currentLocation = start;

            while(!finished) {
                Boolean nextMove = moves.get(moveIndex);
                movesTaken++;
                if (movesTaken % 100000000 == 0) System.out.print(movesTaken + " ");

                currentLocation = nextMove ? maps.get(currentLocation).get(0) : maps.get(currentLocation).get(1);

                if (currentLocation.charAt(2) == 'Z') {
                    // This wouldn't work for everything but works for this input
                    // Should save this movesTaken associated with this start, and continue
                    // until it develops a pattern that would repeat
                    final Long movesTakenCopy = movesTaken;
                    endCounts.add(movesTakenCopy);
                    finished = true;
                }

                moveIndex++;
                if (moveIndex == moves.size()) moveIndex = 0;
            }
        });

        // There is one value for each start point in this example
        // Lowest common multiple for these six is the answer

        return lowestCommonMultiple(endCounts);
    }

    private Long lowestCommonMultiple(List<Long> values) {
        Long lcm = 0L;
        Boolean found = false;
        Long multiplyBy = 1L;

        while (!found) {
            lcm = multiplyBy*values.get(0);
            final Long lcmFinal = lcm;

            // So I know it's still chugging
            if (multiplyBy % 100000000 == 0) System.out.print(multiplyBy + " ");

            if (values.stream().allMatch(x -> (lcmFinal % x) == 0)) found = true;

            multiplyBy++;
        }

        return lcm;
    }

    private void readInput() {
        moves = new ArrayList<>();

        List.of(input.get(0).split("")).stream().forEach(m -> {
            if (m.equals("L")) moves.add(true); else moves.add(false);
        });

        maps = new HashMap<>();

        input.stream().skip(2).forEach(line -> {
            String mapEntry = line.split("=")[0].trim();
            String[] destinations = line.substring(7, 15).split(",");
            maps.put(mapEntry,
                    List.of(destinations[0].trim(), destinations[1].trim()));
        });

        System.out.println(input);
        System.out.println(maps);
    }
}