import java.util.Random;
import java.util.Scanner;

public class MagicSquareGame {
    private int[][] square;
    private int size;
    private int moves;

    public MagicSquareGame(int size) {
        this.size = size;
        square = new int[size][size];
        moves = 0;
    }

    public void generateMagicSquare() {
        int x = 0;
        int y = (size + 1) / 2 - 1;

        for (int i = 1; i <= size * size; i++) {
            square[x][y] = i;
            int newX = (x - 1 + size) % size;
            int newY = (y - 1 + size) % size;

            if (square[newX][newY] == 0) {
                x = newX;
                y = newY;
            } else {
                x = (x + 1) % size;
            }
        }
    }

    public void shuffleMagicSquare() {
        Random random = new Random();
        int n = size * size;

        for (int i = 0; i < n; i++) {
            int x1 = random.nextInt(size);
            int y1 = random.nextInt(size);

            char direction;
            do {
                direction = "UDLR".charAt(random.nextInt(4));
            } while (!isValidMove(x1, y1, direction));

            int x2 = x1, y2 = y1;
            switch (direction) {
                case 'U':
                    x2--;
                    break;
                case 'D':
                    x2++;
                    break;
                case 'L':
                    y2--;
                    break;
                case 'R':
                    y2++;
                    break;
            }

            int temp = square[x1][y1];
            square[x1][y1] = square[x2][y2];
            square[x2][y2] = temp;
        }
    }

    private boolean isValidMove(int x, int y, char direction) {
        int newX = x, newY = y;
        switch (direction) {
            case 'U':
                newX--;
                break;
            case 'D':
                newX++;
                break;
            case 'L':
                newY--;
                break;
            case 'R':
                newY++;
                break;
        }
        return newX >= 0 && newX < size && newY >= 0 && newY < size;
    }

    public void displayMagicSquare() {
        // Display the column headers
        System.out.print("   | ");
        for (int i = 0; i < size; i++) {
            System.out.print(String.format("%-6d", i));
        }
        System.out.println();
    
        // Display the horizontal line
        System.out.print("---+");
        for (int i = 0; i < size; i++) {
            System.out.print("------");
        }
        System.out.println();
    
        // Display the row headers and square values
        for (int i = 0; i < size; i++) {
            System.out.print(String.format("%-3d| ", i));
            for (int j = 0; j < size; j++) {
                System.out.print(String.format("%-6d", square[i][j]));
            }
            System.out.println();
        }
    }
    

    public boolean isMagicSquare() {
        // Check rows, columns, and diagonals
        int targetSum = size * (size * size + 1) / 2;
        int[] rowSums = new int[size];
        int[] colSums = new int[size];
        int mainDiagonalSum = 0, antiDiagonalSum = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rowSums[i] += square[i][j];
                colSums[j] += square[i][j];
                if (i == j) mainDiagonalSum += square[i][j];
                if (i + j == size - 1) antiDiagonalSum += square[i][j];
            }
        }

        for (int i = 0; i < size; i++) {
            if (rowSums[i] != targetSum || colSums[i] != targetSum) return false;
        }
        return mainDiagonalSum == targetSum && antiDiagonalSum == targetSum;
    }

    public void makeMove(int x, int y, char direction) {
        int newX = x, newY = y;
        switch (direction) {
            case 'U':
                newX--;
                break;
            case 'D':
                newX++;
                break;
            case 'L':
                newY--;
                break;
            case 'R':
                newY++;
                break;
        }

        if (isValidMove(x, y, direction)) {
            int temp = square[x][y];
            square[x][y] = square[newX][newY];
            square[newX][newY] = temp;
            moves++;
        } else {
            System.out.println("Invalid move!");
        }
    }

    public int getMoves() {
        return moves;
    }

    public static void main(String[] args) {
        //Output instructions on how to play the game:
        System.err.println("How to play: Follow the instructions below and make your move by entering one input at a time.");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the size of the magic square (odd integer): ");
        //Game Parser
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            return;
        }
        int size = scanner.nextInt();
        if (size % 2 == 0) {
            System.out.println("Invalid input. Please enter an odd integer.");
            return;
        }
        if (size <= 1) {
            System.out.println("Invalid input. Please enter a odd integer greater than 1.");
            return;
        }

        MagicSquareGame game = new MagicSquareGame(size);
        game.generateMagicSquare();
        game.shuffleMagicSquare();
        game.displayMagicSquare();

        while (!game.isMagicSquare()) {
            System.out.println("");
            System.out.println("");
            System.out.println("                 [ |                U    ]");
            System.out.println("Enter your move: [ |  , ---->  ,  L   R  ]");
            System.out.println("                 [ V                D    ]");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            char direction = scanner.next().charAt(0);
            game.makeMove(row, col, direction);
            game.displayMagicSquare();
        }

        System.out.println("Congratulations! You solved the magic square in " + game.getMoves() + " moves.");
    }
}