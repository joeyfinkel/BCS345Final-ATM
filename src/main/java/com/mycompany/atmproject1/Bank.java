package com.mycompany.atmproject1;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author joeyfinkel
 */
public class Bank {
  public static void firstVisit(Scanner scanner, List<Customer> customers) {
    String openAccountResponse = Utils.inputHelper(scanner, "Would you like to open up a new account? (y/n): ");

    if (openAccountResponse.equals("y")) {
      String name = Utils.inputHelper(scanner, "Enter your name: ");
      String email = Utils.inputHelper(scanner, "Enter your email: ", "@", ".com");
      String phone = Utils.inputHelper(scanner, "Enter your phone: ", 10);
      String city = Utils.inputHelper(scanner, "Enter your city: ");
      String state = Utils.inputHelper(scanner, "Enter your state: ");
      String address = Utils.inputHelper(scanner, "Enter your address: ");

      try {
        Customer customer = new Customer(name, email, phone, new Address(city, state, address));

        customers.add(customer);
        JSONFileOperations.write(customers);
        openAccountPrompt(scanner, customer);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Ok, have a good day");
    }
  }

  public static void deposit(Scanner scanner, Account account, int customerId) {
    String amount = Utils.inputHelper(scanner, "How much would you like to deposit?: ");

    account.deposit(Float.parseFloat(amount), customerId);
    System.out.println("\n" + account);
  }

  public static void withdraw(Scanner scanner, Account account, int customerId) {
    String amount = Utils.inputHelper(scanner, "How much would you like to withdraw?: ");

    account.withdraw(Float.parseFloat(amount), customerId);
    System.out.println("\n" + account);
  }

  public static void accountOptions(Scanner scanner, Account account, Customer customer) throws IOException {
    String response = Utils.inputHelper(scanner, "Do you want to withdraw or deposit? (w/d/n/q): ");

    switch (response) {
      case "w":
        withdraw(scanner, account, customer.getId());
        JSONFileOperations.update(customer.getId(), customer);
        TextFileOperations.write(customer, account, "withdraw");
        accountOptions(scanner, account, customer);
        break;
      case "d":
        deposit(scanner, account, customer.getId());
        JSONFileOperations.update(customer.getId(), customer);
        TextFileOperations.write(customer, account, "deposit");
        accountOptions(scanner, account, customer);
        break;
      case "n":
        chooseAccountAction(scanner, customer);
        break;
      case "q":
        System.out.println("Good bye, " + customer.getName() + ". Logging you out now");
        System.exit(1);
        break;
      default:
        System.out.println(
            "That is not a valid option. Please enter again (w - withdraw/d - deposit/ q - quit");

        accountOptions(scanner, account, customer);
        break;
    }

  }

  public static void openAccountPrompt(Scanner scanner, Customer customer) throws IOException {
    String response = Utils.inputHelper(scanner,
        "You don't have any accounts open with us, want to open one? (y/n): ");

    if (response.equals("y")) {
      System.out.print("What type of account do you want to open? (1 - checking/2 - savings): ");
      openAccountAction(scanner, customer);
    } else {
      System.out.print("Okay, have a good day.");
      System.exit(1);
    }
  }

  public static void openAccountAction(Scanner scanner, Customer customer) throws IOException {
    int response = scanner.nextInt();

    if (response == 1) {
      chooseAccountPrompt(scanner, customer, response);
    } else if (response == 2) {
      chooseAccountPrompt(scanner, customer, response);
    } else {
      System.out.print("That is not a valid selection, please try again. (1 - checking/2 - savings): ");
      openAccountAction(scanner, customer);
    }
  }

  public static void chooseAccountAction(Scanner scanner, Customer customer) throws IOException {
    List<Account> accounts = customer.getAccounts();

    System.out.println("Accounts:");

    for (int i = 0; i < accounts.size(); i++) {
      System.out.println(i + ": " + accounts.get(i));
    }

    String accountChooser = Utils.inputHelper(scanner,
        "Choose an account or open a new one (q - logout/o - open): ");

    while (!accountChooser.equals("q")) {
      if (Utils.isNumeric(accountChooser)) {
        if (Integer.parseInt(accountChooser) > accounts.size()) {
          System.out.println("No account found");
          chooseAccountAction(scanner, customer);
        } else {
          Account account = accounts.get(Integer.parseInt(accountChooser));

          accountOptions(scanner, account, customer);
        }
      } else if (accountChooser.equals("q")) {
        System.exit(1);
      } else {
        if (accountChooser.equals("o")) {
          System.out.print("What type of account do you want to open? (1 - checking/2 - savings): ");
          openAccountAction(scanner, customer);
        } else {
          chooseAccountAction(scanner, customer);
        }
      }
    }
  }

  public static void chooseAccountPrompt(Scanner scanner, Customer customer, int response)
      throws IOException {
    String accountType = response == 1 ? "checking" : "savings";
    System.out.println("OPening " + accountType + " account...");
    customer.openAccount(response);
    chooseAccountAction(scanner, customer);
  }

  public static void bankOperations(Scanner scanner, String message, List<Customer> customers) throws IOException {
    String firstResponse = Utils.inputHelper(scanner, message);

    if (customers == null)
      firstVisit(scanner, customers);
    else {
      if (firstResponse.equals("y")) {
        String pin = Utils.inputHelper(scanner, "Please login with your account information.\nPin: ");
        String email = Utils.inputHelper(scanner, "Email: ");
        Customer currentCustomer = customers.stream().filter(p -> p.isValid(email.toLowerCase(), Integer.parseInt(pin)))
            .collect(Collectors.toList()).get(0);

        JSONFileOperations.read(currentCustomer);

        if (currentCustomer == null) {
          System.out.println("No account found with the given information. Please try again");
          firstVisit(scanner, customers);
        } else {
          List<Account> accounts = currentCustomer.getAccounts();

          System.out.println("Logged in successfully. Welcome " + currentCustomer.getName() + ".");

          if (accounts == null) {
            openAccountPrompt(scanner, currentCustomer);
          } else {
            chooseAccountAction(scanner, currentCustomer);
          }
        }
      } else {
        firstVisit(scanner, customers);
      }
    }
  }

  public static void main(String[] args) throws IOException {
    List<Customer> customers = JSONFileOperations.read();

    try (Scanner scanner = new Scanner(System.in)) {
      if (customers == null) {
        customers = new ArrayList<>();

        firstVisit(scanner, customers);
      } else {
        customers.forEach(System.out::println);
        bankOperations(scanner, "Welcome to the bank! Do you have an account? (y/n): ", customers);
      }
    }
  }
}
