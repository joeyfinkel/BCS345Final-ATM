package com.mycompany.atmproject1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {
  private int id;
  private int pin;
  private String name;
  private String email;
  private String phone;
  private Address address;
  private HashMap<Object, Object> customerInfo;
  private List<Account> accounts;

  /**
   * Creates a new customer without any accounts.
   *
   * @param name    The customer's name.
   * @param email   The customer's email.
   * @param phone   The customer's phone number.
   * @param address The customer's address.
   * @throws IOException
   */
  public Customer(String name, String email, String phone, Address address) throws IOException {
    String parent = "receipts/";

    this.name = name;
    this.email = email.toLowerCase();
    this.phone = phone;
    this.address = address;
    this.accounts = null;
    this.id = Utils.generateRandomNumber(5);
    this.pin = Utils.generateRandomNumber(4);

    String child = parent + this.id;

    TextFileOperations.mkdir(child);
    TextFileOperations.write(child + "/account_info.txt", createTextObj(true));
  }

  /**
   * Creates a new customer with all fields.
   *
   * @param id       The customer's id.
   * @param pin      The customer's pin.
   * @param name     The customer's name.
   * @param email    The customer's email.
   * @param phone    The customer's phone number.
   * @param address  The customer's address.
   * @param accounts The customer's accounts.
   */
  public Customer(int id, int pin, String name, String email, String phone, Address address, List<Account> accounts) {
    this.id = id;
    this.pin = pin;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.address = address;
    this.accounts = accounts;
  }

  // #region Getters and Setters
  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the pin
   */
  public int getPin() {
    return pin;
  }

  /**
   * @param pin the pin to set
   */
  public void setPin(int pin) {
    this.pin = pin;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  public List<Account> getAccounts() {
    return accounts;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * @return the customerInfo
   */
  public HashMap<Object, Object> getCustomerInfo() {
    return customerInfo;
  }
  // #endregion

  /**
   * Checks if a customer is valid so they can login.
   *
   * @param email The customer's email.
   * @param pin   the customer's pin.
   * @return if the customer is has an account.
   */
  public boolean isValid(String email, int pin) {
    return this.email.equals(email) && this.pin == pin;
  }

  /**
   * Opens a new account for the {@link Customer}.
   *
   * @param type The type of account to open.
   *             <ul>
   *             <li>1 - checking</li>
   *             <li>2 - savings</li>
   *             </ul>
   *
   * @throws IOException
   */
  public void openAccount(int type) throws IOException {
    String accountType = type == 1 ? "checking" : "savings";

    if (accounts == null) {
      accounts = new ArrayList<>();
    }

    accounts.add(new Account(type, 0, id));

    JSONFileOperations.update(id, new Customer(id, pin, name, email, phone, address, accounts));

    System.out.println("A new " + accountType + " account has been opened up for you. Your account number for your new "
        + accountType + " account is " + this.accounts.get(this.accounts.size() - 1).getNumber());
  }

  /**
   * Creates a {@link HashMap} for the text file.
   *
   * @return A {@link HashMap} with necessary customer information for the receipt
   *         files.
   */
  public HashMap<String, Object> createTextObj(boolean all) {
    HashMap<String, Object> data = new HashMap<String, Object>() {
      {
        put("Customer Id", id);
        put("Name", name);
        put("Email", email);
        put("Phone Number", phone);
      }
    };

    if (all)
      data.putAll(new HashMap<>() {
        {
          put("Pin", pin);
          put("Address", address);
          put("Total accounts", accounts);

        }
      });

    return data;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "Customer [id=" + id + ", pin=" + pin + ", name=" + name + ", email=" + email + ", phone=" + phone
        + ", address=" + address + ", customerInfo=" + customerInfo + ", accounts="
        + accounts + "]";
  }

}
