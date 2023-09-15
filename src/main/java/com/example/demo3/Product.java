package com.example.demo3;


/**
 *
 * @author Andrea Hayes
 * This class implements the Product objects.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Product {

    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

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
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() { return stock; }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a part to the associatedParts list.
     * @param part
     */

    public void addAssociatedParts(Part part) {

        associatedParts.add(part);
    }

    /**
     * Sets associated parts to a part.
     */

    public void setAssociatedParts(ObservableList<Part> associatedParts) {

        this.associatedParts = associatedParts;
    }

    /**
     * Remove a part from the associatedParts list.
     * @param selectedAssociatedPart
     * @return true
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true; // Return true to indicate part was deleted
    }

    /**
     * Returns all associated parts for selected product.
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}


