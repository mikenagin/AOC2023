import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AOC04 {
    List<String> input;

    public AOC04 (List<String> input) {
        this.input = input;
    }

    public Integer winningSum() {
        List<Integer> lineValues = new ArrayList<Integer>(Collections.nCopies(input.size(), 1));

        for (Integer i=0; i<input.size(); i++) {
            Integer lineWinners = getLineValue(input.get(i));

            for (Integer j=i+1; j<(i+lineWinners+1) && j < lineValues.size(); j++) {
                    lineValues.set(j, lineValues.get(j) + lineValues.get(i));
            }

        }

        return lineValues.stream().mapToInt(Integer::intValue).sum();
    }

    private Integer getLineValue(String inputLine) {
        Integer lineValue = 0;

        String[] numbers = inputLine.split(":")[1].split("\\|");

        List<Integer> winningNumbers = stringToListOfNumbers(numbers[0]);
        List<Integer> myNumbers = stringToListOfNumbers(numbers[1]);

        for (Integer myNumber : myNumbers) {
            if (winningNumbers.contains(myNumber)) {
                // From part I: if (lineValue > 0) { lineValue *= 2; } else { lineValue = 1; }
                lineValue++;
            }
        }

        return lineValue;
    }

    private List<Integer> stringToListOfNumbers(String input) {
        String[] stringNumbers = input.trim().split("\s+");
        List<Integer> numbers = new ArrayList<>();

        for (String number : stringNumbers) {
            numbers.add(Integer.parseInt(number.trim()));
        }

        return numbers;
    }
}
