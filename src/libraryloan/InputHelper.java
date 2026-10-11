package libraryloan;

import java.util.Scanner;

public final class InputHelper {
    private InputHelper() {
    }

    public static String readNonBlank(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be blank. Please try again.");
        }
    }

    public static int readNonNegativeInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Number cannot be negative. Please try again.");
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    public static int readMenuChoice(Scanner scanner) {
        return readNonNegativeInt(scanner, "Choose an option: ");
    }

    public static boolean readYesNo(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            }
            if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("Please enter yes or no.");
        }
    }
}
