package com.mycompany.atmproject1;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A set of utility functions to make things easier.
 */
public class Utils {
  public static String filepath = "./accounts.json";

  private Utils() {
  }

  /**
   * A helper method for accepting user input with a message. <strong>This will
   * only work with {@link String} input.</strong>
   *
   * @param scanner The scanner object to accept the user input.
   * @param message The message to show before user input is accepted.
   * @return {@link Scanner#nextLine()}.
   */
  static String inputHelper(Scanner scanner, String message) {
    System.out.print(message);

    return scanner.nextLine();
  }

  /**
   * A helper method for accepting user input with a message. Verifies the value
   * entered by the user is the same length as {@code minLength}. <strong>This
   * will
   * only work with {@link String} input.</strong>
   *
   * @param scanner   The scanner object to accept the user input.
   * @param message   The message to show before user input is accepted.
   * @param minLength The minimum length the value entered by the user can be.
   * @return {@link Scanner#nextLine()}.
   */
  static String inputHelper(Scanner scanner, String message, int minLength) {
    String value = inputHelper(scanner, message);

    System.out.print(message);

    if (value.length() != minLength) {
      System.out.println(
          "Please enter that value again, expected \"" + minLength + "\" characters but only got \"" + value.length()
              + "\" characters");
      return inputHelper(scanner, message);
    }

    return scanner.nextLine();
  }

  /**
   * A helper method for accepting user input with a message. Verifies the value
   * contains {@code toFind}. <strong>This will only work with {@link String}
   * input.</strong>
   *
   * @param scanner The scanner object to accept the user input.
   * @param message The message to show before user input is accepted.
   * @param toFind  The character to look for in the user inputted value.
   * @return {@link Scanner#nextLine()}.
   */
  static String inputHelper(Scanner scanner, String message, String... toFind) {
    String value = inputHelper(scanner, message);

    if (!value.contains(toFind[0]) && !value.contains(toFind[1])) {
      System.out.println(
          "Please enter that value again, expected a value that contains \"" + toFind[0] + "\" and \"" + toFind[1]
              + "\" but got \"" + value + "\"");
      return inputHelper(scanner, message);
    } else if (!value.contains(toFind[0])) {
      System.out.println(
          "Please enter that value again, expected a value that contains \"" + toFind[0] + "\" but got \"" + value
              + "\"");
      return inputHelper(scanner, message);
    } else if (!value.contains(toFind[1])) {
      System.out.println(
          "Please enter that value again, expected a value that contains \"" + toFind[1] + "\" but got \"" + value
              + "\"");
      return inputHelper(scanner, message);
    } else {
      return scanner.nextLine();
    }

  }

  /**
   * Creates a random number with {@code length} amount of digits.
   *
   * @param length The length of the random number.
   * @return A random {@link Integer}.
   */
  static public int generateRandomNumber(int length) {
    Random random = new Random();
    int number = random.nextInt(100000);
    String formatted = String.format("%0" + length + "d", number);

    return Integer.parseInt(formatted);
  }

  /**
   * Checks if a {@link String} is numeric.
   *
   * @param value The {@link String} to check.
   * @return If the {@code value} is numeric or not.
   */
  static boolean isNumeric(String value) {
    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    return value == null ? false : pattern.matcher(value).matches();
  }
}
