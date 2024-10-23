package application;

import model.entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Program {

    public static void main(String[] args) {
        String sourceFilePath = "C:\\Windows\\Temp\\sell.csv";
        String targetFolderPath = createOutputFolder(sourceFilePath);
        String targetFilePath = targetFolderPath + "\\summary.csv";

        List<Product> productList = readProductsFromFile(sourceFilePath);
        if (productList.isEmpty()) {
            System.out.println("No products found to process.");
            return;
        }

        writeSummaryToFile(targetFilePath, productList);
    }

    private static String createOutputFolder(String sourceFilePath) {
        File sourceFile = new File(sourceFilePath);
        String folderPath = sourceFile.getParent() + "\\out";

        File outputFolder = new File(folderPath);
        if (outputFolder.mkdir()) {
            System.out.println("Output folder created at: " + folderPath);
        } else {
            System.out.println("Output folder already exists or could not be created.");
        }
        return folderPath;
    }

    private static List<Product> readProductsFromFile(String filePath) {
        List<Product> productList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineProduct = line.split(", ");
                if (lineProduct.length >= 3) {
                    String name = lineProduct[0];
                    double price = Double.parseDouble(lineProduct[1]);
                    int quantity = Integer.parseInt(lineProduct[2]);
                    productList.add(new Product(name, price, quantity));
                }
            }
            System.out.println("Products read successfully.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        }

        return productList;
    }

    private static void writeSummaryToFile(String filePath, List<Product> productList) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            System.out.println("\nWriting to summary.csv:");

            for (Product product : productList) {
                String lineWriteProduct = String.format("%s, %.2f", product.getName(), product.total());
                bufferedWriter.write(lineWriteProduct);
                System.out.println(lineWriteProduct);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
