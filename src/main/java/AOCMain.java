import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AOCMain {

    public static void main(String[] args) {
        // String fileNameAOC02 = "D:\\Documents\\Course\\AdventOfCode\\src\\main\\java\\aoc02input.txt";
        // aoc02(readFile(fileNameAOC02));
       // aoc03(readFile("D:\\Documents\\Course\\AdventOfCode\\src\\main\\java\\aoc03input.txt"));
        // Part 1: 533229 is too low; 539537 is too high
        // Part 2: 74,455,545 is too low

        // aoc04(readFile("D:\\Documents\\Course\\AdventOfCode\\src\\main\\java\\aoc04test.txt"));
        // aoc04(readFile("D:\\Documents\\Course\\AdventOfCode\\src\\main\\java\\aoc04input.txt"));


       //aoc05(readFile("D:\\Documents\\Course\\AdventOfCode\\src\\main\\java\\aoc05test.txt"));
        aoc05(readFile("D:\\Documents\\Course\\AdventOfCode\\src\\main\\java\\aoc05input.txt"));


    }

    private static void aoc05(List<String> fileLines) {
        AOC05 aoc05 = new AOC05(fileLines);
       // System.out.println("AOC 5 Part 1: " + aoc05.getLowestLocationPart1()); // Answer 251346198
       System.out.println("AOC 5 Part 2: " + aoc05.getLowestLocationPart2()); // Part 2: 72263011
    }

    private static void aoc04(List<String> fileLines) {
        AOC04 aoc04 = new AOC04(fileLines);
        System.out.println("AOC 4 Part 1: " + aoc04.winningSum());
    }

    private static void aoc03(List<String> fileLines) {
        AOC03 aoc03 = new AOC03(fileLines);
      //  System.out.println("AOC 3 Part 1: " + aoc03.sumOfSymbolAdjacent());
        System.out.println("AOC 3 Part 2: " + aoc03.sumOfGears());
    }

    private static void aoc02(List<String> fileLines) {
        AOC02 aoc02 = new AOC02();
        System.out.println("PART 2 ANSWER: " + aoc02.playTheGame(fileLines));
    }

    private static List<String> readFile(String fileName) {
        List<String> fileLines = new ArrayList<>();

        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                fileLines.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return fileLines;
    }
}
