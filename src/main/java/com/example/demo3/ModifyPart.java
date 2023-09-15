package com.example.demo3;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Andrea Hayes
 * This class implements the ModifyPart controller.
 */

public class ModifyPart implements Initializable {

    Stage stage;
    Scene scene;

    @FXML
    private Label MachineIDorCompany;

    @FXML
    private RadioButton modifyInHouseRBtn;

    @FXML
    private RadioButton modifyOutSourceRBtn;

    @FXML
    private TextField modifyPartIDTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartMachineIDTxt;

    @FXML
    private ToggleGroup locationModifyTG;

    /**
     * This method enables the user to choose InHouse for their part.
     */

    @FXML
    public void onActionChooseInHouseModify(ActionEvent event) {

        MachineIDorCompany.setText("Machine ID");
    }

    /**
     * This method enables the user to choose Outsource for their part.
     */

    @FXML
    public void onActionChooseOutsourceModify(ActionEvent event) {

        MachineIDorCompany.setText("Company Name");
    }

    /**
     * This method enables the user to save the modified part, displaying alerts if the part is not entered correctly.
     * The user is then sent back to the 'Main Screen'.
     */

    @FXML
    public void onActionSaveModify(ActionEvent event) throws IOException {
        int id = 0;
        String name = modifyPartNameTxt.getText();
        Double price = null;
        int stock = 0;
        int min = 0;
        int max = 0;
        int machineId;
        String companyName;
        boolean partAddSuccessful = false;

        try {
            price = Double.parseDouble(modifyPartPriceTxt.getText());
            stock = Integer.parseInt(modifyPartInvTxt.getText());
            min = Integer.parseInt(modifyPartMinTxt.getText());
            max = Integer.parseInt(modifyPartMaxTxt.getText());

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid number format. Please enter a valid number.");
            alert.showAndWait();
            return;
        }

        if (max <= min) {
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

        if (modifyInHouseRBtn.isSelected()) {
            try {
                machineId = Integer.parseInt(modifyPartMachineIDTxt.getText());
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

        if (modifyOutSourceRBtn.isSelected()) {
            companyName = modifyPartMachineIDTxt.getText();
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
     * This method retrieves a Part and its characteristics.
     * It then populates the corresponding user interface components on the 'Modify Part' screen with the Part.
     */

    public void sendPart(Part part) {
            modifyPartIDTxt.setText(String.valueOf(part.getId()));
            modifyPartNameTxt.setText(part.getName());
            modifyPartInvTxt.setText(String.valueOf(part.getStock()));
            modifyPartPriceTxt.setText(String.valueOf(part.getPrice()));
            modifyPartMaxTxt.setText(String.valueOf(part.getMax()));
            modifyPartMinTxt.setText(String.valueOf(part.getMin()));

        //Get In-House or Outsourced radio button

        if(part instanceof InHouse) {
            modifyInHouseRBtn.setSelected(true);
            MachineIDorCompany.setText("Machine ID");
            modifyPartMachineIDTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        else {
            modifyOutSourceRBtn.setSelected(true);
            MachineIDorCompany.setText("Company Name");
            modifyPartMachineIDTxt.setText(String.valueOf(((Outsourced) part).getCompanyName()));

            System.out.println("Part ID: " + modifyPartIDTxt.getText());
            System.out.println("Part Name: " + modifyPartNameTxt.getText());
            System.out.println("Part Inventory: " + modifyPartInvTxt.getText());
            System.out.println("Part Price: " + modifyPartPriceTxt.getText());
            System.out.println("Part Max: " + modifyPartMaxTxt.getText());
            System.out.println("Part Min: " + modifyPartMinTxt.getText());
            System.out.println("Part Machine ID or Company Name: " + modifyPartMachineIDTxt.getText());

        }
    }

    /**
     * This method enables the user cancel out of the 'Modify Part' screen and return to the Main Screen.
     * No part will be modified.
     */
        @FXML
        public void onActionCancelModify (ActionEvent event) throws IOException {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
        }

    /**
     * This method initializes the user interface and ensures that the ID TextBox is disabled.
     */

        @Override
        public void initialize (URL location, ResourceBundle resources){

            modifyPartIDTxt.setDisable(true);
        }

    }

