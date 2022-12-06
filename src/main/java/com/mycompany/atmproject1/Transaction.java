package com.mycompany.atmproject1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
  private String date;
  private String accountNumber;
  private char type;
  private int transactionNumber;
  private int customerNumber;
  private float amount;

  /**
   * @param accountNumber
   * @param type
   * @param date
   * @param amount
   */
  public Transaction(String accountNumber, char type, float amount, int customerId) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    this.transactionNumber = Utils.generateRandomNumber(10);
    this.accountNumber = accountNumber;
    this.type = type;
    this.date = dtf.format(now);
    this.amount = amount;
    this.customerNumber = customerId;
  }

  /**
   * @return the date
   */
  public String getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * @return the accountNumber
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * @param accountNumber the accountNumber to set
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * @return the type
   */
  public char getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(char type) {
    this.type = type;
  }

  /**
   * @return the transactionNumber
   */
  public int getTransactionNumber() {
    return transactionNumber;
  }

  /**
   * @param transactionNumber the transactionNumber to set
   */
  public void setTransactionNumber(int transactionNumber) {
    this.transactionNumber = transactionNumber;
  }

  /**
   * @return the customerNumber
   */
  public int getCustomerNumber() {
    return customerNumber;
  }

  /**
   * @param customerNumber the customerNumber to set
   */
  public void setCustomerNumber(int customerNumber) {
    this.customerNumber = customerNumber;
  }

  /**
   * @return the amount
   */
  public float getAmount() {
    return amount;
  }

  /**
   * @param amount the amount to set
   */
  public void setAmount(float amount) {
    this.amount = amount;
  }

  /**
   * Since {@link Transaction#type type} is a {@link Character char}, this utility
   * function will return the full word of {@link Transaction#type type}.
   *
   * @return The type of {@link Transaction#type transaction}.
   */
  public String getFullType() {
    String type = "";

    switch (this.type) {
      case 'o':
      case 'O':
        type = "Open";
        break;
      case 'd':
      case 'D':
        type = "Deposit";
        break;
      case 'w':
      case 'W':
        type = "Withdrawal";
        break;
    }

    return type;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "Transaction [date=" + date + ", accountNumber=" + accountNumber + ", type=" + type + ", transactionNumber="
        + transactionNumber + ", customerNumber=" + customerNumber + ", amount=" + amount + "]";
  }
}
