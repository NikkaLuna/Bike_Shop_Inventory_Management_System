package com.example.demo3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 *
 * @author Andrea Hayes
 * The location of my Javadoc folder is: file:///Users/andreahayes/Desktop/Javadoc/com.example.demo3/module-summary.html
 */

/**
 *
 * @author Andrea Hayes
 * This class containes the main method, which is the entry point into the program.
 */


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method.  This is the first method that gets called when the program is run.
     */
    public static void main(String[] args) {

        Part Wheel = new InHouse(1, "Wheel", 100.00, 100, 25, 100, 1);
        Inventory.addPart(Wheel);

        Part Handlebar = new InHouse(2, "Handlebar", 120.00, 25, 15, 50, 2);
        Inventory.addPart(Handlebar);

        Part Saddle = new Outsourced(3, "Saddle", 50.00, 10, 0, 40, "Ichi Bike Shop");
        Inventory.addPart(Saddle);

        Part Frame = new Outsourced(4, "Frame", 1000.00, 5, 0, 15, "Des Moines Bikes");
        Inventory.addPart(Frame);

        Product MountainBike = new Product(10, "Mountain Bike", 2999.99, 3, 2, 25);
        Inventory.addProduct(MountainBike);

        Product BMXBike = new Product(20, "BMX Bike", 1999.99, 5, 2, 25);
        Inventory.addProduct(BMXBike);

        Product RacingBike = new Product(30, "Racing Bike", 3299.99, 3, 2, 25);
        Inventory.addProduct(RacingBike);

        launch();
    }
}