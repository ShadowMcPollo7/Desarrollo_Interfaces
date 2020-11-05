
package TableViewSample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author raul-
 */
public class TableViewSample extends Application {
    
    //Creamos el TableView
    private final TableView<Person> table = new TableView<>();
    
    //Añadimos los valores con una Observable List
    private final ObservableList<Person> data =
        FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"),
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("Ethan", "Williams", "ethan.williams@example.com"),
            new Person("Emma", "Jones", "emma.jones@example.com"),
            new Person("Michael", "Brown", "michael.brown@example.com")
        );
    final HBox hb = new HBox();
    
    
    @Override
    public void start(Stage stage)  {
        //Creamos la escena
        Scene scene = new Scene(new Group());
        
        //Personalizamos el Stage
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(550);
        
        //Creamos un Label y lo personalizamos
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
        
        table.setEditable(true);
        
        //Creamos las columnas de la tabla
        TableColumn<Person, String> firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        TableColumn<Person, String> lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        TableColumn<Person, String> emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);

        
        //Asociamos los datos con las columnas
        firstNameCol.setCellValueFactory(
        new PropertyValueFactory<>("firstName")
        );
        lastNameCol.setCellValueFactory(
        new PropertyValueFactory<>("lastName")
        );
        emailCol.setCellValueFactory(
        new PropertyValueFactory<>("email")
        );
        
        //Permitimos editar los valores de la tabla
        firstNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        firstNameCol.setOnEditCommit(
            (CellEditEvent<Person, String> t) -> {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
        });
        
        lastNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
       lastNameCol.setOnEditCommit(
            (CellEditEvent<Person, String> t) -> {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
        });
       
       emailCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());       
        emailCol.setOnEditCommit(
            (CellEditEvent<Person, String> t) -> {
                ((Person) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setEmail(t.getNewValue());
        });


        //Añadimos las columnas a la tabla
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        
        //TextFields y Button para añadir datos a la tabla
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("Email");
 
        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            data.add(new Person(
                    addFirstName.getText(),
                    addLastName.getText(),
                    addEmail.getText()));
            addFirstName.clear();
            addLastName.clear();
            addEmail.clear();
        });
        
        //Añadimos al HBox creado los TextFIelds y el boton
        hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton);
        hb.setSpacing(3);

        
        //Creamos un VBox, añadimos la tabla y lo personalizamos
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        
        
        //Añadimos el VBox a la escena
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        //Hacemos que los datos se ordenen de forma descendente en función del email
        emailCol.setSortType(TableColumn.SortType.DESCENDING);
 
        
        //Añadimos la escena al stage
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
