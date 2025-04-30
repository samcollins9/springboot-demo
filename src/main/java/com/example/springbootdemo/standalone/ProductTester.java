package com.example.springbootdemo.standalone;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductTester {

    public static void main(String[] args){
        List<Product> products = Arrays.asList(
                new Product("Laptop", "Electronics", 1200.0, 5),
                new Product("Smartphone", "Electronics", 800.0, 10),
                new Product("Desk Chair", "Furniture", 200.0, 15),
                new Product("Monitor", "Electronics", 300.0, 8),
                new Product("Bookshelf", "Furniture", 150.0, 3),
                new Product("Tablet", "Electronics", 600.0, 7)
        );
/**
        1 - Filter for Electronics category only.
        2 - Calculate a "value score" for each product as price * quantity.
        3 - Sort the products by their value score in descending order.
        4 - Create a list of strings in the format:
        "Smartphone ($800.0) x10 = $8000.0"
        5 - Collect and print the list.
 **/
        List<String> results = products.stream()
                .filter(p -> p.getCategory().equals("Electronics"))
                .sorted((p1, p2) -> Double.compare(
                        p2.getPrice() * p2.getQuantity(),
                        p1.getPrice() * p1.getQuantity()
                ))
                .map(p -> p.getName() + " ($" + p.getPrice() + ") x" + p.getQuantity() +
                        " = $" + (p.getPrice() * p.getQuantity()))
                .collect(Collectors.toList());

        /***
         Group all products by category.

         For each category, find the product with the highest total sales value (price * quantity).

         Return a Map<String, String> where the key is the category name ("Electronics", "Furniture", etc.)
         */

        Map<String, String> mapResult = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory, // group by category (key)
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(p -> p.getPrice() * p.getQuantity())),
                                optionalProduct -> optionalProduct.map(Product::getName).orElse("N/A")
                        )
                ));


        /**
         *
         *
         *
         */

        Map<String, String> mapResult2 = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory, // group by category (key)
                        Collectors.collectingAndThen(
                                Collectors.reducing(
                                        0.0,                                   // identity
                                        p -> p.getPrice() * p.getQuantity(),   // mapper: total value per product
                                        Double::sum                            // combiner
                                ),
                                total -> String.format("Total sales: $%.2f", total) // format result
                        )
                ));

    }
}
