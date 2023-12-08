import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AOC08 {

    private List<String> input;
    private List<Boolean> moves;
    private Map<String, List<String>> maps;

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
        Long movesTaken = 0L;
        Integer moveIndex = 0;

        List<String> currentLocation = maps.keySet().stream().filter(x -> x.charAt(2) == 'A').collect(Collectors.toList());

        while(!reachedEnd) {
            Boolean nextMove = moves.get(moveIndex);
            movesTaken++;
            if (movesTaken % 100000000 == 0) System.out.print(movesTaken + " ");

            currentLocation = currentLocation.stream().map(
                    m -> nextMove ? maps.get(m).get(0) : maps.get(m).get(1)
                    ).collect(Collectors.toList());

            if (currentLocation.stream().filter(x -> x.charAt(2) == 'Z').count() == currentLocation.size()) reachedEnd = true;

            moveIndex++;
            if (moveIndex == moves.size()) moveIndex = 0;

        }

        return movesTaken;
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
