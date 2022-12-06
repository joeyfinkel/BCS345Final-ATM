package com.mycompany.atmproject1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONFileOperations {
  public static String path = "./accounts.json";

  static List<Customer> read() throws IOException {
    Reader reader = Files.newBufferedReader(Paths.get(path));
    List<Customer> customers = new ArrayList<Customer>();
    List<Customer> customersFromFile = new GsonBuilder().setPrettyPrinting()
        .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create().fromJson(
            reader,
            new TypeToken<List<Customer>>() {
            }.getType());

    reader.close();

    if (customersFromFile == null)
      return null;

    customersFromFile.forEach(customer -> {
      customers.add(customer);
    });

    return customers;
  }

  static void read(Customer customer) throws IOException {
    List<Customer> customers = read();

    customers.forEach(System.out::println);
  }

  static void write(List<Customer> customers) throws IOException {
    FileWriter file = new FileWriter(path);
    Gson gson = new Gson();

    gson.toJson(customers, file);
    file.close();
  }

  /**
   * Updates the JSON file with the {@code data}.
   *
   * @param id   Id of the current customer.
   * @param data The new data to write.
   * @throws IOException
   */
  static void update(int id, Customer data) throws IOException {
    List<Customer> customersFromFile = read();
    Customer customer = customersFromFile.stream().filter(p -> p.getId() == id)
        .collect(Collectors.toList()).get(0);
    int idx = customersFromFile.indexOf(customer);

    customersFromFile.set(idx, data);

    write(customersFromFile);
  }

}
