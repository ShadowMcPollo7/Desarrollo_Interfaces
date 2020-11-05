
package fxmlexample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class FXMLExample extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Cargamos el archivo fxml
        Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
        
        //Creamos la escena
        Scene scene = new Scene(root, 325, 300);
        
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
