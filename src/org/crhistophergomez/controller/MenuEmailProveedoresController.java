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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.crhistophergomez.bean.EmailProveedor;
import org.crhistophergomez.bean.Proveedores;
import org.crhistophergomez.db.Conexion;
import org.crhistophergomez.system.Main;


public class MenuEmailProveedoresController implements Initializable{
    private Main escenarioPrincipal;
    
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO; 
    
    private ObservableList <EmailProveedor> listaEmailProveedor;
    private ObservableList <Proveedores> listaProveedores;
    
    @FXML private TextField txtCodigoEP;
    @FXML private TextField txtEmailProveedor;
    @FXML private TextField txtDescripcion;
    
    @FXML private ComboBox cmbCodigoProveedor;
    
    @FXML private TableView tblEmailProveedor;
    
    @FXML private TableColumn colCodigoEP;
    @FXML private TableColumn colEmailProveedor;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colCodigoProveedor;
    
    @FXML private Button btnRegresar;
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
        cargaDatos();
        cmbCodigoProveedor.setItems(getProveedores());
    }
    
    public void cargaDatos(){
        tblEmailProveedor.setItems(getEmailProveedor());
        colCodigoEP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colEmailProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));
    }
    
    public void seleccionarElementos(){
        txtCodigoEP.setText(String.valueOf(((EmailProveedor)tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
        txtEmailProveedor.setText(((EmailProveedor)tblEmailProveedor.getSelectionModel().getSelectedItem()).getEmailProveedor());
        txtDescripcion.setText(((EmailProveedor)tblEmailProveedor.getSelectionModel().getSelectedItem()).getDescripcion());
        cmbCodigoProveedor.getSelectionModel().select(buscarProveedores(((EmailProveedor)tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }
    
    public Proveedores buscarProveedores (int codigoProveedor ){
        Proveedores result = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                result = new Proveedores(registro.getInt("codigoProveedor"),
                                            registro.getString("NITproveedor"),
                                            registro.getString("nombreProveedor"),
                                            registro.getString("apellidoProveedor"),
                                            registro.getString("direccionProveedor"),
                                            registro.getString("razonSocial"),
                                            registro.getString("contactoPrincipal"),
                                            registro.getString("paginaWeb")
             
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return result;
    }
    
    public ObservableList<EmailProveedor> getEmailProveedor(){
        ArrayList<EmailProveedor> listaEP = new ArrayList<EmailProveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmailProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                listaEP.add(new EmailProveedor (resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmailProveedor = FXCollections.observableArrayList(listaEP);
    }
    
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaProvee = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaProvee.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITproveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(listaProvee);
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
                cargaDatos();
                break;
                
        }
    }
    
    public void guardar (){
         EmailProveedor registro = new EmailProveedor();
         registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEP.getText()));
         registro.setEmailProveedor(txtEmailProveedor.getText());
         registro.setDescripcion(txtDescripcion.getText());
         registro.setCodigoProveedor(((Proveedores)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         
         try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmailProveedor(?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoEmailProveedor());
                procedimiento.setString(2, registro.getEmailProveedor());
                procedimiento.setString(3, registro.getDescripcion());
                procedimiento.setInt(4, registro.getCodigoProveedor());
                procedimiento.execute();
            listaEmailProveedor.add(registro);
         }catch (Exception e){
             e.printStackTrace();
         }
     }
    
    public void eliminar(){
        
    }
    
    public void editar(){
        
    }
    
    public void reportes(){
        
    }
    
    public void desactivarControles(){
        txtCodigoEP.setEditable(false);
        txtEmailProveedor.setEditable(false);
        txtDescripcion.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoEP.setEditable(true);
        txtEmailProveedor.setEditable(true);
        txtDescripcion.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoEP.clear();
        txtEmailProveedor.clear();
        txtDescripcion.clear();
        cmbCodigoProveedor.getSelectionModel().getSelectedItem();
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void regresar(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.menuProveedorView();
        }
    }
}
