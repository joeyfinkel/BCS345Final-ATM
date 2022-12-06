package com.mycompany.atmproject1;

public class Address {
  private String city;
  private String state;
  private String street;

  /**
   * @param city
   * @param state
   * @param street
   */
  public Address(String city, String state, String street) {
    this.city = city;
    this.state = state;
    this.street = street;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return the street
   */
  public String getStreet() {
    return street;
  }

  /**
   * @param street the street to set
   */
  public void setStreet(String street) {
    this.street = street;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */

  @Override
  public String toString() {
    return "Address [city=" + city + ", state=" + state + ", street=" + street + "]";
  }
}
