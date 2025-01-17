import java.io.FileNotFoundException;
import java.util.*;

import static java.awt.Color.GREEN;
import static java.awt.Color.YELLOW;

public class Wordle_Main {
    public static void main(String[] args) throws FileNotFoundException {

        Player player = new Player();

        Game game = new Game();

        writeSlots(game);

        for (int i = 0; i < 5; i++) {
            boolean input;

            do {
                input = getAnswer(player, game);
            } while (!input);

            findColors(player, game);

            writeSlots(game);

            player.row++;

            if (player.answer.equals(game.answer)) {
                player.correct = true;
                break;
            }
        }

        if (player.correct) System.out.println("\nCongratulations!");
        else {
            System.out.printf("The correct answer is %S.%n", game.answer);
            System.out.println("\nTry Again.");
        }
    }

    static void writeSlots(Game game) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Slot currentSlot = game.slots[i][j];

                switch (currentSlot.color) {
                    case GREEN -> System.out.printf("\u001b[42;1m %C \033[0m ", currentSlot.Char);
                    case YELLOW -> System.out.printf("\u001b[43;1m %C \033[0m ", currentSlot.Char);
                    default -> System.out.printf("\u001b[40;1m %C \033[0m ", currentSlot.Char);
                }
            }
            System.out.println("\n");
        }
    }

    static boolean getAnswer(Player player, Game game) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your answer: ");
        player.answer = sc.next().toUpperCase();

        if (game.dictList.contains(player.answer)) {
            for (int i = 0; i < 5; i++) {
                game.slots[player.row][i].Char = player.answer.charAt(i);
            }
            return true;
        } else {
            System.out.println("You entered an invalid word!");
            return false;
        }
    }

    static void findColors(Player player, Game game) {
        LinkedHashMap<Character, Integer> letters = new LinkedHashMap<>(game.letters);
        String answer = game.answer;

        for (int i = 0; i < 5; i++) {
            Slot currentSlot = game.slots[player.row][i];
            char currentChar = currentSlot.Char;

            if (currentChar == answer.charAt(i) && letters.get(currentChar) >= 1) {
                currentSlot.color = Colors.GREEN;
                letters.put(currentChar, letters.get(currentChar) - 1);
            } else if (answer.contains(String.valueOf(currentChar)) && letters.get(currentChar) >= 1) {
                currentSlot.color = Colors.YELLOW;
                letters.put(currentChar, letters.get(currentChar) - 1);
            }
        }
    }
}

