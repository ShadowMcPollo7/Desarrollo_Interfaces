
package Practica1;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class Tarea441 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Controls Layout ");
        Group root = new Group();
        Scene scene = new Scene(root, 380, 118, Color.WHITE);

        //Creación del pane y le aplicamos la separacion o padding
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);

        //Creacion de Labels, TextFields y Botón
        Label fNameLbl = new Label("Nombre");
        TextField fNameFld = new TextField();
        Label lNameLbl = new Label("Apellido");
        TextField lNameFld = new TextField();
        Button saveButt = new Button("Guardar");
        
        // Label del nombre
        GridPane.setHalignment(fNameLbl, HPos.RIGHT);
        gridpane.add(fNameLbl, 0, 0);
        
        
        // Label del apellido
        GridPane.setHalignment(lNameLbl, HPos.RIGHT);
        gridpane.add(lNameLbl, 0, 1);
        
        // Field del Nombre
        GridPane.setHalignment(fNameFld, HPos.LEFT);       
        gridpane.add(fNameFld, 1, 0);
        
        // Field del Apellido
        GridPane.setHalignment(lNameFld, HPos.LEFT);
        gridpane.add(lNameFld, 1, 1);

        // Botón de guardar
        GridPane.setHalignment(saveButt, HPos.RIGHT);
        gridpane.add(saveButt, 1, 2);
        
        root.getChildren().add(gridpane);        
        
        //Creacion del Stage
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
