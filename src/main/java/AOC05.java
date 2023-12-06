import java.util.*;
import java.util.stream.Collectors;

public class AOC05 {

    List<String> input;

    private String SEEDTOSOIL = "seed-to-soil map:";
    private String SOILTOFERTILIZER = "soil-to-fertilizer map:";
    private String FERTILIZERTOWATER = "fertilizer-to-water map:";
    private String WATERTOLIGHT = "water-to-light map:";
    private String LIGHTTOTEMPERATURE = "light-to-temperature map:";
    private String TEMPERATURETOHUMIDITY = "temperature-to-humidity map:";
    private String HUMIDITYTOLOCATION = "humidity-to-location map:";

    private List<String> mapOrder;
    private Map<String, List<MapEntry>> maps;

    private enum mapNames {
        SEEDTOSOIL,
        SOILTOFERTILIZER,
        FERTILIZERTOWATER,
        WATERTOLIGHT,
        LIGHTTOTEMPERATURE,
        TEMPERATURETOHUMIDITY,
        HUMIDITYTOLOCATION
    }

    public AOC05(List<String> input) {
        this.input = input;
        mapOrder = List.of(SEEDTOSOIL, SOILTOFERTILIZER, FERTILIZERTOWATER, WATERTOLIGHT, LIGHTTOTEMPERATURE, TEMPERATURETOHUMIDITY, HUMIDITYTOLOCATION);
        maps = readMaps();
    }

    public Long getLowestLocationPart1() {
        Long lowestLocation = Long.MAX_VALUE;
        Long currentSeedLocation;

        List<Long> seeds = List.of(input.get(0).substring(7).split(" ")).stream().map(Long::valueOf).collect(Collectors.toList());

        for (Long seed : seeds) {
            currentSeedLocation = seedLocation(seed);
            if (currentSeedLocation < lowestLocation) lowestLocation = currentSeedLocation;
        }

        return lowestLocation;
    }

    public Long getLowestLocationPart2() {
        Long lowestLocation = Long.MAX_VALUE;
        Long currentSeedLocation;

        String[] seeds = input.get(0).substring(7).split(" ");

        for (int i=0; (i+1) < seeds.length; i=i+2) {
            Long seed = Long.parseLong(seeds[i].trim());
            Long range = Long.parseLong(seeds[i+1].trim());

            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(String.format("Seed: %s, range: %s, calculating....", seed, range));


            for (Long j=seed; j<(seed + range); j++) {
                Long seedLocation = seedLocation(j);
                if (seedLocation < lowestLocation) lowestLocation = seedLocation;
                Double percentage = (j-seed)*100.0/range;
                if (j % 10000000 == 0) System.out.println(percentage + "% ");
            }
        }

        return lowestLocation;
    }

    private Long seedLocation(Long seed) {
        Long currentKey = seed;
        for (String map : mapOrder) {
            Boolean foundMatch = false;

            for (MapEntry mapEntry : maps.get(map)) {
                if ((mapEntry.getSource() <= currentKey) && (currentKey <= (mapEntry.getSource() + mapEntry.getRange())) && (!foundMatch)) {
                    // There is a key match in this range
                    // Eg Source 5, range 14, key 11 - 6th position
                    // 6th position of destination = destination + key - source
                    foundMatch = true;
                    currentKey = mapEntry.getDestination() + (currentKey - mapEntry.getSource());
                }
            }
        }

        return currentKey;
    }

    private Map<String, List<MapEntry>> readMaps () {
        Map<String, List<MapEntry>> maps = new HashMap<>();
        Boolean stillReading = true;
        Integer currentPosition = 2;

        while (currentPosition < input.size()) {
            String mapTitle = input.get(currentPosition++);

            if (mapTitle.trim() != "") {
                List<MapEntry> mapEntries = new ArrayList<>();

                // This map is done; skip to the next map
                for (; (currentPosition < input.size()) && (input.get(currentPosition).trim() != ""); currentPosition++) {
                    mapEntries.add(getMapFromPosition(currentPosition));
                }

                maps.put(mapTitle, mapEntries);
            }
        }

        return maps;
    }

    private MapEntry getMapFromPosition (Integer currentPosition) {
        MapEntry currentEntry;

        while ((currentPosition < input.size()) && (input.get(currentPosition).trim() != "")) {
            Long source = Long.parseLong(input.get(currentPosition).split(" ")[1]);
            Long destination = Long.parseLong(input.get(currentPosition).split(" ")[0]);
            Long range = Long.parseLong(input.get(currentPosition).split(" ")[2]);

            return new MapEntry(source, destination, range);
        }

        return null;
    }

    private class MapEntry {
        Long source;
        Long destination;
        Long range;

        public MapEntry(Long source, Long destination, Long range) {
            this.source = source;
            this.destination = destination;
            this.range = range;
        }

        public Long getSource() {
            return source;
        }

        public Long getDestination() {
            return destination;
        }

        public Long getRange() {
            return range;
        }
    }
}