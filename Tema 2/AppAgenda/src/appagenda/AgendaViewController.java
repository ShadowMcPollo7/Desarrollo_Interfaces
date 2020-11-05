
package appagenda;

import entidades.Persona;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author raul-
 */
public class AgendaViewController implements Initializable {

    private EntityManager entityManager;
    
    //Creamos objeto que utilizaremos para que al seleccionar una Persona, se almacenen en ésta sus datos y  salgan sus datos en los TextFields y así modificar sus valores
    private Persona personaSeleccionada;
    
    //Nos permite indicar qué información de la base de datos se debe mostrar en cada columna.
    @FXML
    private TableView<Persona> tableViewAgenda;
    @FXML
    private TableColumn<Persona, String> columnNombre;
    @FXML
    private TableColumn<Persona, String> columnApellidos;
    @FXML
    private TableColumn<Persona, String> columnEmail;
    @FXML
    private TableColumn<Persona, String> columnProvincia;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellidos;
    @FXML
    private AnchorPane rootAgendaView;
    

    
    //Damos acceso al controlador al objeto entityManager para que
    //en la ventana se puedan mostrar datos en la base de datos
    public void setEntityManager(EntityManager entityManager){
     this.entityManager=entityManager;
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new
            PropertyValueFactory<>("apellidos"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnProvincia.setCellValueFactory(
        cellData->{
        SimpleStringProperty property=new SimpleStringProperty();
        if (cellData.getValue().getProvincia()!=null){
            property.setValue(cellData.getValue().getProvincia().getNombre());
        }
        return property;
        });
        
        //Para que cada vez que se seleccione un contacto, se almacene en el objeto personaSeleccionada de tipo Persona sus datos y que los valores
        // se establezcan en los TextFields cogiendolos del objeto personaSeleccionada
        tableViewAgenda.getSelectionModel().selectedItemProperty().addListener(
            (observable,oldValue,newValue)->{
                personaSeleccionada=newValue;
                if (personaSeleccionada != null){
                    textFieldNombre.setText(personaSeleccionada.getNombre());
                    textFieldApellidos.setText(personaSeleccionada.getApellidos());
                } else {
                    textFieldNombre.setText("");
                    textFieldApellidos.setText("");
                }

        });


    }    
    
    public void cargarTodasPersonas(){
        
        Query queryPersonaFindAll=
            entityManager.createNamedQuery("Persona.findAll");
        List<Persona> listPersona=queryPersonaFindAll.getResultList();
            tableViewAgenda.setItems(FXCollections.observableArrayList(listPersona)
        );
        
    }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        //If para comprobar que haya algo seleccionado en el TableView
        if (personaSeleccionada != null){
            //Se actualizan los valores del objeto
            personaSeleccionada.setNombre(textFieldNombre.getText());
            personaSeleccionada.setApellidos(textFieldApellidos.getText());
            
            //Se actualiza el objeto en la base de datos
            entityManager.getTransaction().begin();
            entityManager.merge(personaSeleccionada);
            entityManager.getTransaction().commit();
            
            //Se actualiza en el TableView los nuevos valores
            int numFilaSeleccionada =
            tableViewAgenda.getSelectionModel().getSelectedIndex();
            tableViewAgenda.getItems().set(numFilaSeleccionada,personaSeleccionada);
            
            //Para que el foco no siga en el botón y vuelva al TableView
            TablePosition pos = new
                TablePosition(tableViewAgenda,numFilaSeleccionada,null);
            tableViewAgenda.getFocusModel().focus(pos);
            tableViewAgenda.requestFocus();
        }
    }

    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        try{
            // Carga la vista de detalle (DetalleView) al pulsar el boton
            FXMLLoader fxmlLoader=new
                FXMLLoader(getClass().getResource("DetalleView.fxml"));
            Parent rootDetalleView=fxmlLoader.load();
            // Oculta la vista de la lista (AgendaView)
            rootAgendaView.setVisible(false);
            
            //Añadir la vista detalle (DetalleView) al StackPane principal para que se muestre
            StackPane rootMain =
                (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
            
            //Se usa el método creado en DetalleViewController setRootController para pasar la vista de lista al detalle.
            DetalleViewController DetalleViewController =
            (DetalleViewController) fxmlLoader.getController();
        DetalleViewController.setRootAgendaView(rootAgendaView);
        
        //Intercambio de datos funcionales con DetalleView
        DetalleViewController.setTableViewPrevio(tableViewAgenda);
        
        //Se le pasa un nuevo objeto Persona
        personaSeleccionada = new Persona();
        DetalleViewController.setPersona(entityManager,
        personaSeleccionada,true);
        
        //Que muestre los datos de la persona nueva en la vista detalle
        DetalleViewController.mostrarDatos();
        
        } catch (IOException ex){
            Logger.getLogger(AgendaViewController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void onActionButtonEditar(ActionEvent event) {
        try{
            // Carga la vista de detalle (DetalleView) al pulsar el boton
            FXMLLoader fxmlLoader=new
                FXMLLoader(getClass().getResource("DetalleView.fxml"));
            Parent rootDetalleView=fxmlLoader.load();
            // Oculta la vista de la lista (AgendaView)
            rootAgendaView.setVisible(false);
            
            //Añadir la vista detalle (DetalleView) al StackPane principal para que se muestre
            StackPane rootMain =
                (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
            
            //Se usa el método creado en DetalleViewController setRootController para pasar la vista de lista al detalle.
            DetalleViewController DetalleViewController =
            (DetalleViewController) fxmlLoader.getController();
        DetalleViewController.setRootAgendaView(rootAgendaView);
        
        //Intercambio de datos funcionales con DetalleView
        DetalleViewController.setTableViewPrevio(tableViewAgenda);
        
        //Le pasamos los datos de la persona elegida
        DetalleViewController.setPersona(entityManager,personaSeleccionada,false);
        
        //Que muestre los datos de la persona elegida en la vista detalle
        DetalleViewController.mostrarDatos();
        
        } catch (IOException ex){
            Logger.getLogger(AgendaViewController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        //Un aviso saltará para confirmar que se quiere eliminar el registro, con el nombre y los apellidos de la persona seleccionada
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("¿Desea suprimir el siguiente registro?");
        alert.setContentText(personaSeleccionada.getNombre() + " "
            + personaSeleccionada.getApellidos());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // Acciones a realizar si el usuario acepta (Se borra el registro)
            // se eliminará de la base de datos, de la TableView y se haá que el foco vuelva a uno de los registros
            entityManager.getTransaction().begin();
            entityManager.merge(personaSeleccionada);
            entityManager.remove(personaSeleccionada);
            entityManager.getTransaction().commit();
            
            tableViewAgenda.getItems().remove(personaSeleccionada);
            tableViewAgenda.getFocusModel().focus(null);
            tableViewAgenda.requestFocus();
        } else {
            // Acciones a realizar si el usuario cancela. Simplemente hay que volver a dejar seleccionado en el TableView la misma fila
            int numFilaSeleccionada=
                tableViewAgenda.getSelectionModel().getSelectedIndex();
            tableViewAgenda.getItems().set(numFilaSeleccionada,personaSeleccionada);
            TablePosition pos = new TablePosition(tableViewAgenda,
                numFilaSeleccionada,null);
            tableViewAgenda.getFocusModel().focus(pos);
            tableViewAgenda.requestFocus();
        }
    }


    
}
