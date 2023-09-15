package com.example.demo3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.IOException;

/**
 *
 * @author Andrea Hayes
 * This class implements the AddPart controller.
 */


public class AddPart implements Initializable {

    Stage stage;
    Scene scene;

    private static int id = 100;


    @FXML
    private Label MachineIDorCompany;

    @FXML
    private Label addPartLabel;

    @FXML
    private RadioButton addInHouseRBtn;

    @FXML
    private RadioButton addOutSourceRBtn;

    @FXML
    private TextField addPartIDTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartMachineIDTxt;

    @FXML
    private ToggleGroup locationAddTG;

    /**
     * This method enables the user to choose InHouse for their part.
     */

    @FXML
    public void onActionChooseInHouseAdd(ActionEvent event) {

        MachineIDorCompany.setText("Machine ID");
    }

    /**
     * This method enables the user to choose Outsource for their part.
     */

    @FXML
    public void onActionChooseOutsourceAdd(ActionEvent event) {

        MachineIDorCompany.setText("Company Name");
    }

    /**
     * This method enables the user to save the new part, displaying alerts if the part is not entered correctly.
     * The user is then sent back to the 'Main Screen'.
     */

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

    @FXML
    void onActionSaveAdd(ActionEvent event) throws IOException {
        int id = 0;
        String name = addPartNameTxt.getText();
        Double price = null;
        int stock = 0;
        int min = 0;
        int max = 0;
        int machineId;
        String companyName;
        boolean partAddSuccessful = false;

        try {
            price = Double.parseDouble(addPartPriceTxt.getText());
            stock = Integer.parseInt(addPartInvTxt.getText());
            min = Integer.parseInt(addPartMinTxt.getText());
            max = Integer.parseInt(addPartMaxTxt.getText());

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid number format. Please enter a valid number.");
            alert.showAndWait();
            return;
        }

        if (min >= max) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: maximum must be greater than minimum");
            alert.showAndWait();
            return;

        } else if (stock > max || stock < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: Inventory must be within min and max.");
            alert.showAndWait();
            return;

        } else if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("ERROR: The name must not be empty");
            alert.showAndWait();

        } else {
            if (addInHouseRBtn.isSelected()) {
                try {
                    machineId = Integer.parseInt(addPartMachineIDTxt.getText());
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid machine ID format. Please enter a valid number.");
                    alert.showAndWait();
                    return;
                }
                InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                newInHousePart.setId(Inventory.getNewPartId());
                Inventory.addPart(newInHousePart);
                partAddSuccessful = true;
            }

            if (addOutSourceRBtn.isSelected()) {
                companyName = addPartMachineIDTxt.getText();
                Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                newOutsourcedPart.setId(Inventory.getNewPartId());
                Inventory.addPart(newOutsourcedPart);
                partAddSuccessful = true;
            }

            if (partAddSuccessful) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(anchorPane);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    /**
     * This method enables the user cancel out of the 'Add Part' screen and return to the Main Screen.
     * No part will be added.
     */

    @FXML
        public void onActionCancelAdd (ActionEvent event) throws IOException {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
        }

    /**
     * This method auto creates a part ID for the new part and increments it starting from 100 so to not overlap with other part IDs.
     */

        public static int getNewPartID() {
            id++;
            return id;
    }

    /**
     * This method initializes the user interface, sets the new part ID, and ensures that the ID TextBox is disabled.
     */

        @Override
        public void initialize (URL location, ResourceBundle resources){
            addInHouseRBtn.setSelected(true);
            id = getNewPartID();
            addPartIDTxt.setText(String.valueOf(id));


            addPartIDTxt.setDisable(true);
        }

}

    





