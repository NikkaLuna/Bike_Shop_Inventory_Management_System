package com.example.demo3;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Andrea Hayes
 * This class serves as a repository for all the parts and products.
 * It provides the functionality to add, search, update, and delete parts and products.
 */


public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int partId = 0;
    private static int productId = 0;

    /**
     * Adds a new part to the allParts observable list.
     //* @param newPart
     */

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the allParts observable list.
     //* @param newProduct
     */

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up a part ID to determine if the part is in inventory.  If not, it will return null.
     //* @param part
     */

    public static Part lookupPart(int partID) {
        for(Part part: Inventory.getAllParts()) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /**
     * Looks up a product ID to determine if the part is in inventory.
     //* @param product
     */

    public static Product lookupProduct(int productID) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Looks up a part name to determine if the part is in inventory.  If so, it will return the part.
     //* @param partName
     */

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        // Iterates through allParts list to determine if partName is in list
        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                PartName.add(part);
            }
        }

        return PartName;
    }

    /**
     * Looks up a product name to determine if the product is in inventory.  If so, it will return the product.
     //* @param productName
     */

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        // Iterates through allProducts list to determine if productName is in list
        for (Product product : allProducts)
            if (product.getName().equals(productName)) {
                ProductName.add(product);
            }

        return ProductName;
    }

    /**
     * Updates a selected part.
     //* @param selectedPart
     */

    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * Updates a selected product.
     * @param selectedProduct
     */

    public static void updateProduct (int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);
    }

    /**
     * Deletes a selected part if found in the allParts list.
     * @param selectedPart
     */

    public static boolean deletePart(String selectedPart) {
        for (Part part: allParts) {
            if (part.getName().equals(selectedPart)) {
                allParts.remove(part);
                return true; // Return true to indicate part was deleted
            }
        }
        return false; // Return false if part was not found
    }


    /**
     * Deletes a selected product if found in the allParts list.
     * @param selectedProduct
     */

    public static boolean deleteProduct(String selectedProduct) {
        for (Product product : allProducts) {
            if (product.getName().equals(selectedProduct)) {
                allProducts.remove(product);
                return true; // Return true to indicate product was deleted
            }
        }
        return false; // Return false if product was not found
    }

    /**
     * Returns all parts from the allParts list.
     */

    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * Returns all products from the allProducts list.
     */

    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

    /**
     * Gets a new PartID when a new part is created and increments it so to not overlap with other PartIDs.
     */

    public static int getNewPartId() {
        return ++partId;
    }

    /**
     * Gets a new ProductID when a new product is created and increments it so to not overlap with other productIDs.
     */

    public static int getNewProductId() {
        return ++productId;
    }

}
