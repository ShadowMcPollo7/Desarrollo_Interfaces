package fxmltableview;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class FXMLTableView extends Application {
    
     @Override
    public void start(Stage primaryStage) throws Exception {
        //Ponemos titulo al STage
       primaryStage.setTitle("FXML TableView Example");
       
       //Creamos el pane y lo linkeamos con el archivo fxml
       Pane myPane = (Pane)FXMLLoader.load(getClass().getResource
    ("fxml_tableview.fxml"));
       
       //Creamos la Escena
       Scene myScene = new Scene(myPane);
       
       //Añadimos la escena al stage
       primaryStage.setScene(myScene);
       primaryStage.show();
       
    }
 
    public static void main(String[] args) {
        launch(args);
    }
    
}
