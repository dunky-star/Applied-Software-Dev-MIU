package edu.miu.cs.cs489appsd.lab1a.productmgmtapp;

import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.model.Product;
import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.service.CsvFormatter;
import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.service.JsonFormatter;
import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.service.ProductFormatter;
import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.service.XmlFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductMgmtApp {
    public static void main(String[] args) throws InterruptedException {
        // Sample product list
        List<Product> products = new ArrayList<>(List.of(
                new Product(3128874119L, "Banana", LocalDate.of(2023, 1, 24), 124, 0.55),
                new Product(2927458265L, "Apple", LocalDate.of(2022, 12, 9), 18, 1.09),
                new Product(9189927460L, "Carrot", LocalDate.of(2023, 3, 31), 89, 2.99)
        ));

        // Sort in descending order by unit price
        products.sort(Comparator.comparingDouble(Product::unitPrice).reversed());

        // Read CLI argument
        String format = "all";
        if (args.length >= 2 && args[0].equals("--format")) {
            format = args[1].toLowerCase();
        }

        // Print based on selected format
        switch (format) {
            case "json" -> printProducts("JSON", new JsonFormatter(), products);
            case "xml" -> printProducts("XML", new XmlFormatter(), products);
            case "csv" -> printProducts("Comma-Separated Values (CSV)", new CsvFormatter(), products);
            case "all" -> {
                printProducts("JSON", new JsonFormatter(), products);
                printProducts("XML", new XmlFormatter(), products);
                printProducts("Comma-Separated Values (CSV)", new CsvFormatter(), products);
            }
            default -> {
                System.out.println("Unsupported format: " + format);
                System.out.println("Supported formats: json, xml, csv, all");
            }
        }
    }

    private static void printProducts(String format, ProductFormatter formatter, List<Product> products) {
        System.out.printf("== Printed in %s Format ==%n", format);
        System.out.println(formatter.format(products));
        System.out.println();
    }
}
