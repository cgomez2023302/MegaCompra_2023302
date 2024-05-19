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
import org.crhistophergomez.bean.Clientes;
import org.crhistophergomez.db.Conexion;
import org.crhistophergomez.system.Main;

public class MenuClientesController implements Initializable{
    private Main escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO; 
    
    private ObservableList<Clientes> listaClientes;
    
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtNombreC;
    @FXML private TextField txtApellidoC;
    @FXML private TextField txtDireccionC;
    @FXML private TextField txtCorreoC;
    @FXML private TextField txtCodigoC;
    @FXML private TextField txtNIT;
    @FXML private TextField txtTelefonoC;
    
    @FXML private TableView tblClientes;
    
    @FXML private TableColumn colCodigoC;
    @FXML private TableColumn colNombreC;
    @FXML private TableColumn colApellidoC;
    @FXML private TableColumn colNIT;
    @FXML private TableColumn colDireccionC;
    @FXML private TableColumn colTelefonoC;
    @FXML private TableColumn colCorreoC;
    
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
        
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNIT.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITcliente"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
    }
    
    public void seleccionarDatos(){
        txtCodigoC.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNIT.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNITcliente());
        txtNombreC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
        txtApellidoC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());   
        txtDireccionC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtCorreoC.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());        
    }
    
    public ObservableList<Clientes> getClientes(){
        ArrayList<Clientes> lista = new ArrayList<>(); 
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarClientes()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Clientes (resultado.getInt("codigoCliente"), 
                resultado.getString("NITCliente"),
                resultado.getString("nombreCliente"),
                resultado.getString("apellidoCliente"),
                resultado.getString("direccionCliente"),
                resultado.getString("telefonoCliente"),
                resultado.getString("correoCliente")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaClientes = FXCollections.observableArrayList(lista);
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
        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtCodigoC.getText()));
        registro.setNombreCliente(txtNombreC.getText());
        registro.setApellidoCliente(txtApellidoC.getText());
        registro.setNITcliente(txtNIT.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCliente(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITcliente());
            procedimiento.setString(3, registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            
            procedimiento.execute();
            listaClientes.add(registro);
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
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar al eliminar registro", "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
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
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/crhistophergomez/images/Guardar.png"));
                    imgReportes.setImage(new Image("/org/crhistophergomez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoC.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCliente(?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes)tblClientes.getSelectionModel().getSelectedItem();
            
            registro.setNITcliente(txtNIT.getText());
            registro.setNombreCliente(txtNombreC.getText());
            registro.setApellidoCliente(txtApellidoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            
            
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITcliente());
            procedimiento.setString(3, registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            
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
        txtCodigoC.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtCorreoC.setEditable(false);
        txtNIT.setEditable(false);
        txtTelefonoC.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoC.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtCorreoC.setEditable(true);
        txtNIT.setEditable(true);
        txtTelefonoC.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoC.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtDireccionC.clear();
        txtCorreoC.clear();
        txtNIT.clear();
        txtTelefonoC.clear();
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
