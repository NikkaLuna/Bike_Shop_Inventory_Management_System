package com.example.demo3;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * RUNTIME ERROR:
 * When working with the onActionSaveAdd() method in the ‘AddPart’ class, I received this runtime error message: NumberFormatException.
 * Upon investigation, I discovered that, due to an invalid number format input into the text field, the method was unable to parse the values from the various text
 * fields into their correct data type (double or integer).
 *
 * To fix the issue, I added a try/catch block into the method to handle this potential exception.
 * The try/catch block specifies the NumberFormatException e as the exception to catch and creates an alert to notify the user that they are using an invalid number format.
 * The user will then be returned to the ‘AddPart’ or ‘ModifyPart’ screen where they will be given another opportunity to enter the correct number format for the text fields.
 */


/**
 *
 * FUTURE ENHANCEMENT TO EXTEND FUNCTIONALITY:
 * To extend the functionality of this program, I would implement a feature that monitored and optimized inventory levels.
 * This feature would constantly assess inventory levels and, when a part or product approached the minimum threshold, it would automatically generate purchase orders.
 * By doing so, this feature would ensure the company never runs out of stock, which would prevent a potential loss in sales.
 */

/**
 *
 * @author Andrea Hayes
 * The location of my Javadoc folder is: file:///Users/andreahayes/Desktop/Javadoc/com.example.demo3/module-summary.html
 */


/**
 *
 * @author Andrea Hayes
 * This class implements the MainScreen controller.
 */

public class MainScreen implements Initializable {

    Stage stage;
    Scene scene;

    @FXML
    private Button addPartBtn;

    @FXML
    private Button addProductBtn;

    @FXML
    private Button deletePartBtn;

    @FXML
    private Button deleteProductBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button modifyPartBtn;

    @FXML
    private Button modifyProductBtn;

    @FXML
    private TextField searchPartTxt;

    @FXML
    private TextField searchProductTxt;

    @FXML
    private TableColumn<Part, Integer> invProductLevelCol;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;

    @FXML
    private TableColumn<Part, Integer> partNameCol;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Double> pricePerUnitPartCol;

    @FXML
    private TableColumn<Part, Double> pricePerUnitProductCol;

    @FXML
    private TableColumn<Part, Integer> productIDCol;

    @FXML
    private TableColumn<Part, Integer> productNameCol;

    @FXML
    private TableView<Product> productTableView;


    /**
     * This method takes the user to the Add Part screen to add a part.
     */

    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method takes the user to the Add Product screen to add a product.
     */

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method enables the user to search for a part.
     * If found, the part will populate in the part table.
     */

    @FXML
    void onActionSearchParts(ActionEvent event) throws IOException {
        String searchText = searchPartTxt.getText();
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
            partTableView.setItems(results);
            searchPartTxt.clear(); // Clears the text from the searchPartTxt text box
        } catch (NumberFormatException e) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Part cannot be found.");
            noParts.showAndWait();
        }
    }

    /**
     * This method enables the user to search for a product.
     * If found, the product will populate in the product table.
     */

    @FXML
    void onActionSearchProducts(ActionEvent event) throws IOException {
        String searchText = searchProductTxt.getText();
        ObservableList<Product> results = Inventory.lookupProduct(searchText);
        try {
            if (results.size() == 0 && !searchText.isEmpty()) {
                int productID = Integer.parseInt(searchText);
                Product product = Inventory.lookupProduct(productID);
                if (product != null) {
                    results.add(product);
                }
            } else if (searchText.isEmpty()) {
                results.addAll(Inventory.getAllProducts());
            }
            productTableView.setItems(results);
            searchProductTxt.clear(); // Clears the text from the searchProductTxt text box
        } catch (NumberFormatException e) {
            Alert noProducts = new Alert(Alert.AlertType.ERROR);
            noProducts.setTitle("Error Message");
            noProducts.setContentText("Product cannot be found.");
            noProducts.showAndWait();
        }
    }

    /**
     * This method enables the user to delete a part.
     */

    @FXML
    void onActionDeletePart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the selected part?");
        alert.setTitle("Alert");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            ObservableList<Part> allParts, singlePart;
            allParts = partTableView.getItems();
            singlePart = partTableView.getSelectionModel().getSelectedItems();
            singlePart.forEach(allParts::remove);

        }
    }

    /**
     * This method enables the user to delete a product.
     */

    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        Product product = productTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the selected product?");
        alert.setTitle("Alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (!product.getAllAssociatedParts().isEmpty()) {
                Alert assocAlert = new Alert(Alert.AlertType.WARNING);
                assocAlert.setTitle("Warning Dialog");
                assocAlert.setContentText("Product has at least 1 associated part");
                assocAlert.showAndWait();
            } else {
                ObservableList<Product> allProducts, singlePart;
                allProducts = productTableView.getItems();
                singlePart = productTableView.getSelectionModel().getSelectedItems();
                singlePart.forEach(allProducts::remove);

            }
        }
    }

    /**
     * This method enables the user to modify a part.
     */

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        if (partTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("ERROR: No part selected");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPart.fxml"));
            Parent modifyPartRoot = loader.load();

            ModifyPart MPController = loader.getController();
            MPController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(modifyPartRoot);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method enables the user to modify a product.
     */

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        if (productTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("ERROR: No product selected");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
            loader.load();

            ModifyProduct MPController = loader.getController();
            MPController.sendProduct(productTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method enables the user to exit from the program.
     */

    @FXML
    public void onActionExit(ActionEvent event) throws IOException {
        System system;
        system = null;
        system.exit(0);
    }

    /**
     * This method initializes and populates the tables.
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitPartCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invProductLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitProductCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}


