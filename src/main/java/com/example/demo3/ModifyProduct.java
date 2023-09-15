package com.example.demo3;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Andrea Hayes
 * This class implements the ModifyProduct controller.
 */


public class ModifyProduct implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    private Product chosenProduct;

    Stage stage;
    Scene scene;

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
    private Button removeProductBtn;

    @FXML
    private Button saveProductBtn;

    @FXML
    private TextField searchProductTxt;

    @FXML
    private TableColumn<Part, Double> addPricePerUnitCol;

    @FXML
    private TableColumn<Part, Integer> addProductIDCol;

    @FXML
    private TableColumn<Part, Integer> addProductInvLevelCol;

    @FXML
    private TableColumn<Part, String> addProductNameCol;

    @FXML
    private TableView<Part> addProductTableView;

    @FXML
    private TableColumn<Part, Integer> removeInvLevelCol;

    @FXML
    private TableColumn<Part, Double> removePricePerUnitCol;

    @FXML
    private TableColumn<Part, Integer> removeProductIDCol;

    @FXML
    private TableColumn<Part, String> removeProductNameCol;

    @FXML
    private TableView<Part> removeProductTableView;

    /**
     * This method enables the user to select a part from the top table and move it to the bottom table.
     * As long as the product's part list doesn't already contain the part, the part will be added to the product's part list.
     */

    @FXML
    void onActionAddProductBtn(ActionEvent event) {
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
     * This method enables the user to cancel out of the 'Modify Product' screen and return to the 'Main Screen'.
     */

    @FXML
    void onActionCancelProductBtn(ActionEvent event) throws IOException {

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
    void onActionRemoveProductBtn(ActionEvent event) {

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
    void onActionSaveProductBtn(ActionEvent event) throws IOException {
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
     * This method retrieves a Product and its characteristics.
     * It then populates the corresponding user interface components on the 'Modify Product' screen with the Product.
     */

    @FXML
    public void sendProduct(Product product) {

        chosenProduct = product;
        removeProductTableView.setItems(chosenProduct.getAllAssociatedParts());

        addProductIDTxt.setText(String.valueOf(chosenProduct.getId()));
        addProductNameTxt.setText(chosenProduct.getName());
        addProductPriceTxt.setText(String.valueOf(chosenProduct.getPrice()));
        addProductInvTxt.setText(String.valueOf(chosenProduct.getStock()));
        addProductMaxTxt.setText(String.valueOf(chosenProduct.getMax()));
        addProductMinTxt.setText(String.valueOf(chosenProduct.getMin()));
    }

    /**
     * This method initializes the top and bottom tables, connects a list of associated parts to the bottom table, and ensures that the ID TextBox is disabled.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addProductTableView.setItems(Inventory.getAllParts());
        addProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        removeProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        removeProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        removePricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        removeProductTableView.setItems(associatedPartsList);

        addProductIDTxt.setDisable(true);
        }

}



