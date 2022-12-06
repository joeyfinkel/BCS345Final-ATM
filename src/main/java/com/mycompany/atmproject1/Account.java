package com.mycompany.atmproject1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Account {
  private int number;
  private int type;
  private String openDate;
  private float balance;
  private List<Transaction> transactions;

  /**
   * @param type
   * @param balance
   */
  public Account(int type, float balance, int customerId) {
    String accountNumber = Utils.generateRandomNumber(5) + "0" + type;
    Transaction transaction = new Transaction(accountNumber, 'O', balance, customerId);

    System.out.println(transaction);

    this.type = type;
    this.balance = balance;
    this.openDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yy"));
    this.number = Integer.parseInt(accountNumber);
    System.out.println("Number: " + transaction.getTransactionNumber());
    this.transactions = new ArrayList<>() {
      {
        add(new Transaction(accountNumber, 'O', balance, customerId));
      }
    };
  }

  // #region Getters and Setters
  /**
   * @return the number
   */
  public int getNumber() {
    return number;
  }

  /**
   * @param number the number to set
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   * @return the type
   */
  public int getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * @return the openDate
   */
  public String getOpenDate() {
    return openDate;
  }

  /**
   * @param openDate the openDate to set
   */
  public void setOpenDate(String openDate) {
    this.openDate = openDate;
  }

  /**
   * @return the balance
   */
  public float getBalance() {
    return balance;
  }

  /**
   * @param balance the balance to set
   */
  public void setBalance(float balance) {
    this.balance = balance;
  }

  /**
   * @return the transactions
   */
  public List<Transaction> getTransactions() {
    return transactions;
  }

  /**
   * @param transactions the transactions to set
   */
  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }
  // #endregion

  public void deposit(float amount, int customerId) {
    this.balance += amount;

    if (transactions == null) {
      transactions = new ArrayList<>();
    }

    transactions.add(new Transaction(String.valueOf(number), 'D', balance, customerId));

    System.out.printf("Successfully deposited %.2f into account %d", amount, number);
  }

  public String getAccountType() {
    return this.type == 1 ? "checking" : "savings";
  }

  public void withdraw(float amount, int customerId) {
    if (this.balance <= 0) {
      System.out
          .println("Your account balance is " + this.balance + ", you do not have enough money to withdraw " + amount);
    } else {
      this.balance -= amount;
      System.out.println("You successfully withdrew " + amount + " from your " + this.getAccountType()
          + " account. Your new account balance is: " + this.balance);
    }

    if (transactions == null) {
      transactions = new ArrayList<>();
    }

    transactions.add(new Transaction(String.valueOf(number), 'W', balance, customerId));
  }

  public Transaction getTransaction(int transactionNumber) {
    return transactions.stream().filter(p -> p.getTransactionNumber() == transactionNumber).collect(Collectors.toList())
        .get(0);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "Account [number=" + number + ", type=" + type + ", openDate=" + openDate + ", balance=" + balance + "]";
  }

}
