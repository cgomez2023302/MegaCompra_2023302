package org.crhistophergomez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.crhistophergomez.bean.CargoEmpleado;
import org.crhistophergomez.db.Conexion;
import org.crhistophergomez.system.Main;


public class MenuCargoEmpleadoController implements Initializable{
    
    private Main escenarioPrincipal;
    
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO; 
    
    private ObservableList<CargoEmpleado> listaCargoEmpleado;
    
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoCE;
    @FXML private TextField txtNombreCE;
    @FXML private TextField txtDescripcionCE;
    
    @FXML private TableView tblCargoEmpleados;
    
    @FXML private TableColumn colCodigoCE;
    @FXML private TableColumn colNombreCE;
    @FXML private TableColumn colDescripcionCE;
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblCargoEmpleados.setItems(getCargoEmpleado());
        colCodigoCE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripcionCE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }
    
    public void seleccionarDatos(){
        txtCodigoCE.setText(String.valueOf(((CargoEmpleado)tblCargoEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreCE.setText(((CargoEmpleado)tblCargoEmpleados.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcionCE.setText(((CargoEmpleado)tblCargoEmpleados.getSelectionModel().getSelectedItem()).getDescripcionCargo());
    }
    
    private ObservableList<CargoEmpleado> getCargoEmpleado(){
        ArrayList<CargoEmpleado> lista = new ArrayList();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarCargoEmpleado()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                resultado.getString("nombreCargo"),
                resultado.getString("descripcionCargo")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCargoEmpleado = FXCollections.observableArrayList(lista);
    }
    
    public void agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("org/crhistophergomez/images/Guardar.png"));
                imgEliminar.setImage(new Image("org/crhistophergomez/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
                
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("org/crhistophergomez/images/AgregarPersona.png"));
                imgEliminar.setImage(new Image("org/crhistophergomez/images/EliminarPersona.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
                
        }
    }
    
    public void guardar(){
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCE.getText()));
        registro.setNombreCargo(txtNombreCE.getText());
        registro.setDescripcionCargo(txtDescripcionCE.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCargoEmpleado(?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            
            procedimiento.execute();
            listaCargoEmpleado.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
       switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("org/crhistophergomez/images/AgregarPersona.png"));
                imgEliminar.setImage(new Image("org/crhistophergomez/images/EliminarPersona.png"));
                
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
               
            default:
                if(tblCargoEmpleados.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar al eliminar registro", "Eliminar Cargo de Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado)tblCargoEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            
                            procedimiento.execute();
                            listaCargoEmpleado.remove(tblCargoEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    
                }else
                    JOptionPane.showMessageDialog(null, "Debes de seleccionar un Elemento");
       }
    }
    
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tblCargoEmpleados.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/crhistophergomez/images/Guardar.png"));
                    imgReportes.setImage(new Image("/org/crhistophergomez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoCE.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                    
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/crhistophergomez/images/AgregarPersona.png"));
                imgReportes.setImage(new Image("/org/crhistophergomez/images/Registros.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCargoEmpleado(?, ?, ?)}");
            CargoEmpleado registro = (CargoEmpleado)tblCargoEmpleados.getSelectionModel().getSelectedItem();
            
            registro.setNombreCargo(txtNombreCE.getText());
            registro.setDescripcionCargo(txtDescripcionCE.getText());
            
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/crhistophergomez/images/EditarPersona.png"));
                imgReportes.setImage(new Image("/org/crhistophergomez/images/Registros.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                
        }
    }
    
    public void desactivarControles(){
        txtCodigoCE.setEditable(false);
        txtNombreCE.setEditable(false);
        txtDescripcionCE.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoCE.setEditable(true);
        txtNombreCE.setEditable(true);
        txtDescripcionCE.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoCE.clear();
        txtNombreCE.clear();
        txtDescripcionCE.clear();
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void regresar(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
