/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appagenda;

import entidades.Persona;
import entidades.Provincia;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 * FXML Controller class
 *
 * @author raul-
 */
public class DetalleViewController implements Initializable {
    
    
    private Pane rootAgendaView;
    private TableView tableViewPrevio;
    private Persona persona;
    private EntityManager entityManager;
    //Para saber si al abrir la detalle view, es una persona nueva (se ha pulsado boton nuevo) o estamos editando uno ya existente
    private boolean nuevaPersona;
    //Para mostrar datos de Estado Civil
    public static final char CASADO='C';
    public static final char SOLTERO='S';
    public static final char VIUDO='V';
    //Constante para la direccion de la carpeta fotos
    public static final String CARPETA_FOTOS="src/appagenda/Fotos";

    
    //Nos permite indicar qué información de la base de datos se debe mostrar en cada columna.
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellidos;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private TextField textFieldNumHijos;
    @FXML
    private TextField textFieldSalario;
    @FXML
    private CheckBox checkBoxJubilado;
    @FXML
    private ComboBox<Provincia> comboBoxProvincia;
    @FXML
    private RadioButton radioButtonSoltero;
    @FXML
    private RadioButton radioButtonCasado;
    @FXML
    private RadioButton radioButtonViudo;
    @FXML
    private DatePicker datePickerFechaNacimiento;
    @FXML
    private ImageView ImageViewFoto;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private AnchorPane rootDetalleView;
    
    //Metodo set para "coger" la vista de AgendaView
    public void setRootAgendaView(Pane rootAgendaView){
        this.rootAgendaView = rootAgendaView;
    }
    
    //Metodo set para "coger" la lista de Personas de AgendaView
    public void setTableViewPrevio(TableView tableViewPrevio){
        this.tableViewPrevio=tableViewPrevio;
    }
    
    //Método para conectarse con la base de datos y aignar los valores dados.
    //en caso de que el objeto Persona sea uno de los registros ya existentes, dicho objeto se tomará desde la base de datos
    public void setPersona(EntityManager entityManager, Persona persona,
            Boolean nuevaPersona){
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
        if (!nuevaPersona){
            this.persona=entityManager.find(Persona.class,persona.getId());
        } else {
            this.persona=persona;
        }
        this.nuevaPersona=nuevaPersona;
    }
    
    //Método para mostrar los datos de las personas
    public void mostrarDatos(){
        //Controles tipo String
        textFieldNombre.setText(persona.getNombre());
        textFieldApellidos.setText(persona.getApellidos());
        textFieldTelefono.setText(persona.getTelefono());
        textFieldEmail.setText(persona.getEmail());
        
        //Controles de tipo no String(es mas complicado)
        
        //Hay que convertir los datos de tipo Short y BigDecimal a String
        if (persona.getNumHijos() != null){
            textFieldNumHijos.setText(persona.getNumHijos().toString());
        }
        if (persona.getSalario() != null){
            textFieldSalario.setText(persona.getSalario().toString());
        }
        //Ver si está seleccionado o no el checkBox de Jubilado
        if (persona.getJubilado() != null){
            checkBoxJubilado.setSelected(persona.getJubilado());
        }
        //Se han creado constantes para saber que tipo de Estado Civil se ha seleccionado
        if (persona.getEstadoCivil() != null){
            switch(persona.getEstadoCivil()){
                case CASADO:
                    radioButtonCasado.setSelected(true);
                    break;
                case SOLTERO:
                    radioButtonSoltero.setSelected(true);
                    break;
                case VIUDO:
                    radioButtonViudo.setSelected(true);
                    break;
            }
        }
        
        //Se coge el valor de tipo Date del Date Picker
        if (persona.getFechaNacimiento() != null){
            Date date=persona.getFechaNacimiento();
            Instant instant=date.toInstant();
            ZonedDateTime zdt=instant.atZone(ZoneId.systemDefault());
            LocalDate localDate=zdt.toLocalDate();
            datePickerFechaNacimiento.setValue(localDate);
        }
        
        //En el caso de Provincia, es especial, ya que el valor se almacena en la tabla Provincia
        //Por lo que haremos una consulta para que se muestren todas las provincias en la lista desplegable
        Query queryProvinciaFindAll=
            entityManager.createNamedQuery("Provincia.findAll");
        List listProvincia = queryProvinciaFindAll.getResultList();
        comboBoxProvincia.setItems(FXCollections.observableList(listProvincia));
        //En el caso de que la persona tenga ya asignada una provincia, se mostrará directamente
        if (persona.getProvincia() != null){
            comboBoxProvincia.setValue(persona.getProvincia());
        }
        //Vamos a hacer que cada provincia aparezca en forma de una combinacion de su nombre y su codigo
        comboBoxProvincia.setCellFactory(
        (ListView<Provincia> provincial)-> new ListCell<Provincia>(){
            protected void updateItem(Provincia provincia, Boolean empty){
                super.updateItem(provincia, empty);
                if (provincial == null || empty){
                    setText("");
                } else {
                    setText(provincia.getCodigo()+"-"+provincia.getNombre());
                }
            }
        });
        //Y ahora que al seleccionar una provincia, salga la seleccionada en el ComboBox cerrado
        comboBoxProvincia.setConverter(new StringConverter<Provincia>(){
            @Override
            public String toString(Provincia provincial){
                if (provincial == null){
                    return null;
                } else {
                    return provincial.getCodigo()+"-"+provincial.getNombre();
                    }
                }
            @Override
            public Provincia fromString(String userId){
                return null;
            }
        });
        
        //Para que se muestre la imágen que tenga esa Persona. Todas las fotos asociadas a la direccion la cual
        //hemos puesto en la constante CARPETA_FOTOS. SI existe el archivo se cargará y se proyectará en el ImageView
        //si no, saldrá un aviso
        if (persona.getFoto() != null){
            String imageFileName=persona.getFoto();
            File file = new File(CARPETA_FOTOS+"/"+imageFileName);
            if (file.exists()){
                Image image = new Image(file.toURI().toString());
                ImageViewFoto.setImage(image);
            } else {
                Alert alert=new Alert(AlertType.INFORMATION,"No se encuentra la imagen en "+file.toURI().toString());
                alert.showAndWait();
            }
        }
        
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        //Variable booleana para saber si todos los datos introducidos son correctos 
        boolean errorFormato = false;
        
        //Para que se actualicen y se guarden los datos del objeto Persona en la base de datos.
                
        //Datos tipo String
        persona.setNombre(textFieldNombre.getText());
        persona.setApellidos(textFieldApellidos.getText());
        persona.setTelefono(textFieldTelefono.getText());
        persona.setEmail(textFieldEmail.getText());
                
        //Datos numericos para el numero de hijos 
        if (!textFieldNumHijos.getText().isEmpty()){
                    
            try {
                persona.setNumHijos(Short.valueOf(textFieldNumHijos.getText()));
            } catch(NumberFormatException ex){
                errorFormato = true;
                Alert alert = new Alert(AlertType.INFORMATION, "Número de hijos no válido");
                alert.showAndWait();
                textFieldNumHijos.requestFocus();
            }
        }
        
        //Datos numericos para el salario
        if (!textFieldSalario.getText().isEmpty()){

            try {
                persona.setSalario(BigDecimal.valueOf(Double.valueOf(textFieldSalario.getText()).doubleValue()));
            } catch(NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(AlertType.INFORMATION, "Salario no válido");
                alert.showAndWait();
                textFieldSalario.requestFocus();
            }
        }
        
        //Dato booleano para el checkbox de jubilado para saber si está seleccionado
        persona.setJubilado(checkBoxJubilado.isSelected());
        
        //Datos de opcion múltiple para estado civil, variará dependiendo de cual esté elegido
        if (radioButtonCasado.isSelected()){
            persona.setEstadoCivil(CASADO);
        } else if (radioButtonSoltero.isSelected()){
            persona.setEstadoCivil(SOLTERO);
        } else if (radioButtonViudo.isSelected()){
            persona.setEstadoCivil(VIUDO);
        }
        
        //Datos de tipo fecha para la fecha de nacimiento
        if (datePickerFechaNacimiento.getValue() != null){
            LocalDate localDate = datePickerFechaNacimiento.getValue();
            ZonedDateTime zonedDateTime =
                localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            persona.setFechaNacimiento(date);
        } else {
            persona.setFechaNacimiento(null);
        }
        
        //Datos de tabla relacionada -> Variable Provincia se relaciona con tabla Provincia
        if (comboBoxProvincia.getValue() != null){
            persona.setProvincia(comboBoxProvincia.getValue());
        } else {
            Alert alert = new Alert(AlertType.INFORMATION,"Debe indicar una provincia");
            alert.showAndWait();
            errorFormato = true;
        }
        
        //Datos de tipo imagen
        

        
        
        
            
        if (!errorFormato) { // Los datos introducidos son correctos
            try {
            
                //Para que la vista vuelva a AgendaView y se elimine de la escena a DetalleView
                StackPane rootMain =
                    (StackPane) rootDetalleView.getScene().getRoot();
                rootMain.getChildren().remove(rootDetalleView);
                
                rootAgendaView.setVisible(true);
                
                
                //Si la persona ya existía hay que actualizar(merge), y si es nueva hay que insertar(persist) los datos.
                //Sabemos si es nueva o no gracias a la variable creada nuevaPersona de tipo Booleano
                if (nuevaPersona){
                    entityManager.persist(persona);
                } else {
                    entityManager.merge(persona);
                }
                entityManager.getTransaction().commit();
        
            } catch (RollbackException ex) { // Los datos introducidos no cumplen requisitos de BD
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios. "
                    + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            }
        }
        
        
        //Ahora hay que actualizar los datos en el TableView de la vista de AgendaView
        //Si es una nueva persona se añadirá debajo del todo y se le asignará el foco, si es editando una existente, el foco volverá a dicha fila.
        int numFilaSeleccionada;
        if (nuevaPersona){
            tableViewPrevio.getItems().add(persona);
            numFilaSeleccionada = tableViewPrevio.getItems().size()- 1;
            tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
            tableViewPrevio.scrollTo(numFilaSeleccionada);
        } else {
            numFilaSeleccionada=
            tableViewPrevio.getSelectionModel().getSelectedIndex();
            tableViewPrevio.getItems().set(numFilaSeleccionada,persona);
        }
        TablePosition pos = new TablePosition(tableViewPrevio,
            numFilaSeleccionada,null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
        
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        //Para que la vista vuelva a AgendaView y se elimine de la escena a DetalleView
        StackPane rootMain =
            (StackPane) rootDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootDetalleView);
        
        rootAgendaView.setVisible(true);
        
        //Como se cancela, hay que anular la transaccion para que no varien los datos ni en el TableView ni en la base de datos
        //Además se devuelve el foco a la fila que estuviera seleccionada previamente
        entityManager.getTransaction().rollback();
        
        int numFilaSeleccionada =
            tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio,
            numFilaSeleccionada,null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
        File carpetaFotos = new File(CARPETA_FOTOS);
        if (!carpetaFotos.exists()){
            carpetaFotos.mkdir(); //Si no existe la carpeta fotos, la crea
        }
        //Se crea un objeto FileChooser que nos muestra una ventana para elegir el archivo que deseemos
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.jpg","*.png"), //Se configura para que solo salgan archivos jpg y png
        new FileChooser.ExtensionFilter("Todos los archivos","*.*"));            //Aunque está la posibilidad de mostrar todos los datos
        File file = fileChooser.showOpenDialog(
            rootDetalleView.getScene().getWindow());
        if (file != null){
            try {
                //Si seleccionamos un archivo, se hará una copia en la carpeta de fotos
                Files.copy(file.toPath(),new File(CARPETA_FOTOS+
                    "/"+file.getName()).toPath());
                //Asignamos la foto al objeto persona
                persona.setFoto(file.getName());
                //Cargamos la imagen en un objeto Image
                Image image = new Image(file.toURI().toString());
                //Cargamos la imagen en el ImageView
                ImageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex){
                Alert alert = new Alert(AlertType.WARNING,"Nombre de archivo duplicado"); //Si ya existe el archivo al intentar copiarlo, salta el aviso
            alert.showAndWait();
            } catch (IOException ex){
                Alert alert = new Alert(AlertType.WARNING,"No se ha podido guardar la imagen"); //Si no le deja hacer la copia por cualquier motivo, salta el aviso
            alert.showAndWait();
            }
        }
                
            
    }

    @FXML
    private void onActionSuprimirFoto(ActionEvent event) {
        //Solicita confirmación al usuario para borrar la foto
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresión de imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen,\n"+ "quitar la foto pero MANTENER el archivo, \no CANCELAR la operación?");
        alert.setContentText("Elija la opción deseada:");
        
        //En la alerta nos salen tres opciones,, dejar a la persona sin foto, o si ademas eliminar la foto de la carpeta o cancelar
        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar",
            ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener,
        buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeEliminar){ //Si se pulsa el boton eliminar, se elimina la foto y deja a la persona sin foto
            String imageFileName = persona.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()) {
                file.delete();
            }
            persona.setFoto(null);
            ImageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener) { //Si se pulsa mantener, deja a la persona sin foto
            persona.setFoto(null);
            ImageViewFoto.setImage(null);
        } 
    }

    
    
}
