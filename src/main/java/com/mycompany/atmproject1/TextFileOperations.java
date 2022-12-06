package com.mycompany.atmproject1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TextFileOperations {
  private final static String DIRECTORY = "receipts";
  private static File dir;

  private TextFileOperations() {
    dir = mkdir(DIRECTORY);
  }

  private static void writeBasicCustomerInformation(HashMap<String, Object> data, Path path) {
    data.forEach((k, v) -> {
      try {
        Files.writeString(path, k + ": " + v + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  public static File mkdir(String directory) {
    File dir = new File(directory);

    if (!dir.exists()) {
      dir.mkdirs();
      System.out.println("Created directory");
    }

    return dir;
  }

  public static void write(String filepath, HashMap<String, Object> customerInfo) throws IOException {
    Path path = Paths.get(filepath);

    writeBasicCustomerInformation(customerInfo, path);
  }

  /**
   * Writes transactions in a text file.
   *
   * @param customer        The current customer.
   * @param account         The current account the {@code customer} is performing
   *                        actions to.
   * @param transactionType The type of {@link Transaction#getFullType()
   *                        transaction} the {@code customer} is performing.
   */
  public static void write(Customer customer, Account account, String transactionType) {
    // Creates a new directory for the customer with subdirectories for every
    List<String> directories = new ArrayList<>() {
      {
        add("receipts/" + customer.getId() + "/" + account.getNumber());
      }
    };

    // Creates a file for every transaction type for each account
    directories.forEach(dir -> {
      String filepath = dir + "/" + transactionType + ".txt";
      Path path = Paths.get(filepath);
      File file = new File(filepath);
      HashMap<String, Object> basicCustomerInfo = customer.createTextObj(false);
      Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
      NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(locale);
      Transaction transaction = account.getTransactions().get(account.getTransactions().size() - 1);
      String message = "\n" + transaction.getFullType() + " (" + transaction.getTransactionNumber() + ")"
          + "\n\tDate: " + transaction.getDate() + "\n\tAmount: " + moneyFormat.format(transaction.getAmount())
          + "\n\tBalance after: " + moneyFormat.format(account.getBalance()) + "\n\n";

      mkdir(dir);

      // Adds the customer information to the file
      if (!file.exists())
        writeBasicCustomerInformation(basicCustomerInfo, path);

      // Appends the account information to the file
      try {
        Files.writeString(path, message, StandardOpenOption.CREATE,
            StandardOpenOption.APPEND);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }
}
