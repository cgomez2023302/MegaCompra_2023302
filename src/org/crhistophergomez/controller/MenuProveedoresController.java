package org.crhistophergomez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.crhistophergomez.bean.Proveedores;
import org.crhistophergomez.db.Conexion;
import org.crhistophergomez.reportes.GenerarReportes;
import org.crhistophergomez.system.Main;


public class MenuProveedoresController implements Initializable{
    private Main escenarioPrincipal;
    
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO; 
    
    private ObservableList<Proveedores> listaProveedores;
    
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoP;
    @FXML private TextField txtNIT;
    @FXML private TextField txtNombreP;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtDireccionP;
    @FXML private TextField txtRazonP;
    @FXML private TextField txtContactoPrincipalP;
    @FXML private TextField txtPaginaWebP;
    
    @FXML private TableView tblProveedores;
    
    @FXML private TableColumn colCodigoP;
    @FXML private TableColumn colNIT;
    @FXML private TableColumn colNombreP;
    @FXML private TableColumn colApellidoP;
    @FXML private TableColumn colDireccionP;
    @FXML private TableColumn colRazonP;
    @FXML private TableColumn colContactoPrincipalP;
    @FXML private TableColumn colPaginaWebP;
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    
    @FXML private MenuItem btnEmailProveedor;
    @FXML private MenuItem btnTelefonoProveedor;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblProveedores.setItems(getProveedores());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNIT.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITproveedor"));
        colNombreP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
        colDireccionP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoPrincipalP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaWebP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
        
    }
    
    public void seleccionarDatos(){
        txtCodigoP.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNIT.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNITproveedor());
        txtNombreP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor());
        txtApellidoP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor());
        txtDireccionP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazonP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
        txtContactoPrincipalP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        txtPaginaWebP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
    } 

    private ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarProveedores()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedores (resultado.getInt("codigoProveedor"),
                resultado.getString("NITproveedor"),
                resultado.getString("nombreProveedor"), 
                resultado.getString("apellidoProveedor"),
                resultado.getString("direccionProveedor"),
                resultado.getString("razonSocial"),
                resultado.getString("contactoPrincipal"),
                resultado.getString("paginaWeb")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaProveedores = FXCollections.observableArrayList(lista);
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
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
        registro.setNITproveedor(txtNIT.getText());
        registro.setNombreProveedor(txtNombreP.getText());
        registro.setApellidoProveedor(txtApellidoP.getText());
        registro.setDireccionProveedor(txtDireccionP.getText());
        registro.setRazonSocial(txtRazonP.getText());
        registro.setContactoPrincipal(txtContactoPrincipalP.getText());
        registro.setPaginaWeb(txtPaginaWebP.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            
            procedimiento.execute();
            listaProveedores.add(registro);
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
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar al eliminar registro", "Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
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
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/crhistophergomez/images/Guardar.png"));
                    imgReportes.setImage(new Image("/org/crhistophergomez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoP.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores)tblProveedores.getSelectionModel().getSelectedItem();
            
            registro.setNITproveedor(txtNIT.getText());
            registro.setNombreProveedor(txtNombreP.getText());
            registro.setApellidoProveedor(txtApellidoP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonP.getText());
            registro.setContactoPrincipal(txtContactoPrincipalP.getText());
            registro.setPaginaWeb(txtPaginaWebP.getText());
            
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            
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
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoProveedor", null);
        GenerarReportes.mostrarReportes("ReportesProveedores.jasper", "Reporte de los Proveedores", parametros);
    }
    
    public void desactivarControles(){
        txtCodigoP.setEditable(false);
        txtNIT.setEditable(false);
        txtNombreP.setEditable(false);
        txtApellidoP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtRazonP.setEditable(false);
        txtContactoPrincipalP.setEditable(false);
        txtPaginaWebP.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoP.setEditable(true);
        txtNIT.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtRazonP.setEditable(true);
        txtContactoPrincipalP.setEditable(true);
        txtPaginaWebP.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoP.clear();
        txtNIT.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtDireccionP.clear();
        txtRazonP.clear();
        txtContactoPrincipalP.clear();
        txtPaginaWebP.clear();
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
    
    @FXML 
    public void clicEmailProveedor (ActionEvent event){
        if(event.getSource() == btnEmailProveedor){
            escenarioPrincipal.menuEmailProveedores();
        }
    }
    
    @FXML 
    public void clicTelefonoProveedor (ActionEvent event){
        if(event.getSource() == btnTelefonoProveedor){
            escenarioPrincipal.menuTelefonoProveedor();
        }
    }
}
