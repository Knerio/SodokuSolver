package de.derioo;

import de.derioo.utils.ConsoleClearer;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
public class Sodoku {

    private List<List<Integer>> current = new ArrayList<>();

    public Sodoku(String resource) {
        InputStream stream = Sodoku.class.getClassLoader().getResourceAsStream(resource);
        Stream<String> lines = new BufferedReader(new InputStreamReader(stream))
                .lines();
        lines.forEach(line -> {
            List<Integer> lineList = new ArrayList<>();
            for (String number : line.split(" ")) {
                lineList.add(Integer.parseInt(number));
            }
            current.add(lineList);
        });
        for (List<Integer> integers : current) {
            System.out.println(integers.size());
        }
    }

    public boolean isValid(int row, int col, int num) {
        // Check the row
        for (int i = 0; i < 9; i++) {
            if (current.get(row).get(i) == num) {
                return false;
            }
        }

        // Check the column
        for (int i = 0; i < 9; i++) {
            if (current.get(i).get(col) == num) {
                return false;
            }
        }

        // Check the 3x3 sub-grid
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (current.get(i).get(j) == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkList(List<List<Integer>> columns) {
        for (List<Integer> column : new ArrayList<>(columns)) {
            column = new ArrayList<>(column);
            column.removeIf(integer -> integer == -1);
            if (new HashSet<>(column).size() != column.size()) return true;
        }
        return false;
    }


    public void printBoard() {
        for (int i = 0; i < current.size(); i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("----------------------");
            }

            for (int j = 0; j < current.get(i).size(); j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }

                System.out.print(current.get(i).get(j) == -1 ? " " : current.get(i).get(j));

                if (j < current.get(i).size() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Sodoku sodoku = new Sodoku("sodoku.txt");
        solve(sodoku);
        sodoku.printBoard();
    }

    private static boolean solve(Sodoku sodoku) throws InterruptedException {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sodoku.current.get(row).get(col) == -1) {
                    for (int num = 1; num <= 9; num++) {
                        if (sodoku.isValid(row, col, num)) {
                            sodoku.update(200);
                            sodoku.current.get(row).set(col, num);
                            if (solve(sodoku)) {
                                return true;
                            }
                        } else {
                            sodoku.current.get(row).set(col, num);
                        }
                        sodoku.update(90);
                        sodoku.current.get(row).set(col, -1);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void update(long delay) throws InterruptedException {
        ConsoleClearer.clear();
        this.printBoard();
        Thread.sleep(delay);
    }
}
