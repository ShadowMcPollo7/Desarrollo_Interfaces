/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlCircle;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class ControlCircle extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Circle circulo = new Circle();
        Button btn1 = new Button();
        Button btn2 = new Button();
        btn1.setText("Aumentar");
        btn2.setText("Reducir");
        circulo.setRadius(100);
        
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                circulo.setRadius(circulo.getRadius()+5);
            }
        });
        
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                circulo.setRadius(circulo.getRadius()-5);
            }
        });
        
        
        BorderPane root = new BorderPane();
        root.setCenter(circulo);
        root.setRight(btn1);
        root.setLeft(btn2);

        
        Scene scene = new Scene(root, 500, 300);
        
        primaryStage.setTitle("Circulo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
