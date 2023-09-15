package com.example.demo3;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Andrea Hayes
 * This class implements the AddProduct controller.
 */

public class AddProduct implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    Stage stage;
    Scene scene;

    private static int id = 100;

    @FXML
    private Button RemoveProductBtn;

    @FXML
    private Button addProductBtn;

    @FXML
    private TextField addProductIDTxt;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private Button cancelProductBtn;

    @FXML
    private Button saveProductBtn;

    @FXML
    private TextField searchProductTxt;

    @FXML
    private TableColumn<Product, Integer> addInvLevelCol;

    @FXML
    private TableColumn<Product, Double> addPricePerUnitCol;

    @FXML
    private TableColumn<Product, Integer> addProductIDCol;

    @FXML
    private TableColumn<Product, String> addProductNameCol;

    @FXML
    private TableView<Part> addProductTableView;

    @FXML
    private TableColumn<Product, Integer> removeInventoryLevelCol;

    @FXML
    private TableColumn<Product, Double> removePricePerUnitCol;

    @FXML
    private TableColumn<Product, Integer> removeProductIDCol;

    @FXML
    private TableColumn<Product, String> removeProductNameCol;

    @FXML
    private TableView<Part> removeProductTableView;

    /**
     * This method enables the user to select a part from the top table and move it to the bottom table.
     * As long as the product's part list doesn't already contain the part, the part will be added to the product's part list.
     */

    @FXML
    void onActionAddProduct(ActionEvent event) {
        // Adding selectedPart to the bottom table
        Part selectedPart = addProductTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
            return;
        }

        // As long as the product's part list doesn't already contain this part:
        if (!associatedPartsList.contains(selectedPart)) {
            // Add the part to the product's part list:
            associatedPartsList.add(selectedPart);

            // Update the removeProductTableView with the associatedPartsList
            removeProductTableView.setItems(associatedPartsList);
        }
    }

    /**
     * This method enables the user to search for parts from the inventory.  If the part is found, it will populate in the top table.
     */

    @FXML
    void onActionSearchParts(ActionEvent event) throws IOException {
        String searchText = searchProductTxt.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            if (results.size() == 0 && !searchText.isEmpty()) {
                int partID = Integer.parseInt(searchText);
                Part part = Inventory.lookupPart(partID);
                if (part != null) {
                    results.add(part);
                }
            } else if (searchText.isEmpty()) {
                results.addAll(Inventory.getAllParts());
            }
            addProductTableView.setItems(results);
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Part cannot be found.");
            noParts.showAndWait();
        }
    }

    /**
     * This method enables the user to cancel out of the 'Add Product' screen and return to the 'Main Screen'.
     */

    @FXML
    void onActionCancelProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method enables the user to remove the product from the bottom table after it has been selected.
     */

    @FXML
    void onActionRemoveProduct(ActionEvent event) {

        Part deletedPart = removeProductTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.show();
            associatedPartsList.remove(deletedPart);
            removeProductTableView.setItems(associatedPartsList);
        }
    }

    /**
     * This method enables the user to save the new product. It will display alerts if the product is not entered correctly.
     * The user will then automatically be sent back to the 'Main Screen'.
     */

    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {

            int id = 0;
            String name = addProductNameTxt.getText();
            Double price = Double.parseDouble(addProductPriceTxt.getText());
            int stock = Integer.parseInt(addProductInvTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());

            if (addProductNameTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("ERROR: The name must not be empty");
                alert.showAndWait();

            } else if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: Minimum is higher than the maximum.");
                alert.showAndWait();
                return;

            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: Inventory is out of the minimum and maximum range.");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(id, name, price, stock, min, max);

            for (Part part : associatedPartsList) {
                newProduct.addAssociatedParts(part);
            }

            newProduct.setId(Inventory.getNewProductId());
            Inventory.addProduct(newProduct);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Invalid number format. Please enter a valid number.");
            alert.showAndWait();
        }
    }

    /**
     * This method auto creates a product ID for the new product and increments it starting from 100 so to not overlap with other product IDs.
     */

    public static int getNewProductID() {
        id++;
        return id;
    }

    /**
     * This method initializes the top and bottom tables, sets a new product ID, and ensures that the ID TextBox is disabled.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //moves the items from the associatedParts observable list INTO the top table in Add Products table
        addProductTableView.setItems(Inventory.getAllParts());

        addProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        removeProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        removeProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        removePricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        id = getNewProductID();
        addProductIDTxt.setText(String.valueOf(id));

        addProductIDTxt.setDisable(true);
    }
    }


