import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AOC13 {

    private List<String> input;

    public AOC13 (List<String> input) {
        this.input = input;
    }

    public Integer calculateReflections() {
        Integer reflections = 0;
        List<char[]> pattern = new ArrayList<>();

        for (int i=0; i<input.size(); i++) {
            if (input.get(i) != "") {
                pattern.add(input.get(i).toCharArray());
            } else {
                reflections += examinePattern(pattern);
                pattern = new ArrayList<>();
            }
        }

        reflections += examinePattern(pattern);

        return reflections;
    }

    private Integer examinePattern(List<char[]> pattern) {
        Integer reflectionLine = getReflection(pattern);

        if (reflectionLine > 0) return reflectionLine*100;

        // No reflection in horizontal; Convert vertical
        List<char[]> verticalPattern = new ArrayList<>();

        for (int col=0; col<pattern.get(0).length; col++) {
            char[] newColumn = new char[pattern.size()];
            for (int row = 0; row < pattern.size(); row++) {
                newColumn[row] = pattern.get(row)[col];
            }
            verticalPattern.add(newColumn);
        }

        return getReflection(verticalPattern);
    }

    private Integer getReflection(List<char[]> pattern) {
        Integer reflectionLine = 0;
        Boolean reflectionFound = false;

        while ((reflectionLine < pattern.size()-1) && !reflectionFound) {
            reflectionLine++;
            if (reflectionLine < pattern.size()/2.0) {
                reflectionFound = isReflection(pattern.subList(0, reflectionLine * 2));
            } else {
                reflectionFound = isReflection(pattern.subList(reflectionLine - (pattern.size()-reflectionLine), pattern.size()));
            }
        }

        return reflectionFound ? reflectionLine : 0;
    }

    private Boolean isReflection(List<char[]> pattern) {
        Integer middle = pattern.size()/2;
        Integer current = 0;

        while ((current + middle) < pattern.size()) {
            if (!Arrays.equals(pattern.get(middle+current), pattern.get(middle-(current+1)))) {
                return false;
            }
            current++;
        }

        return true;
    }
}
