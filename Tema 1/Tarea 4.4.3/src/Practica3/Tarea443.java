
package Practica3;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class Tarea443 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dialog");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 300, Color.WHITE);

        //Creacion del Stage
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //MyDialog
        Stage myDialog = new MyDialog(primaryStage);
        myDialog.sizeToScene();
        myDialog.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

class MyDialog extends Stage {

    public MyDialog(Stage owner) {
        super();
        initOwner(owner);
        setTitle("Titulo");
        initModality(Modality.APPLICATION_MODAL); //Un Stage que bloquea los eventos de entrada del resto de ventanas de la misma aplicacion a excepción de la que hereda
        
        //Creacion del Group
        Group root = new Group();
        
        //Creacion de la escena
        Scene scene = new Scene(root, 250, 150, Color.WHITE);
        setScene(scene);

        //Creacion del Pane y padding
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);

        //Creacion del Label del usuario
        Label userNameLbl = new Label("Usuario: ");
        gridpane.add(userNameLbl, 0, 1);

        //Creacion del Label de la contraseña
        Label passwordLbl = new Label("Contraseña: ");
        gridpane.add(passwordLbl, 0, 2);
        
        //Creacion del TextField del usuario
        final TextField userNameFld = new TextField("Admin");
        gridpane.add(userNameFld, 1, 1);

        //Creacion del TextField de la contraseña
        final PasswordField passwordFld = new PasswordField();
        passwordFld.setText("password");
        gridpane.add(passwordFld, 1, 2);

        //Creacion del botón
        Button login = new Button("Cambiar");
        
        //Evento
        login.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                close(); //Se cierra la ventana al accionar el botón
            }
        });
        
        //Añade el boton al pane
        gridpane.add(login, 1, 3);
        GridPane.setHalignment(login, HPos.RIGHT);
        //Añade el pane al group
        root.getChildren().add(gridpane);
    }
}
