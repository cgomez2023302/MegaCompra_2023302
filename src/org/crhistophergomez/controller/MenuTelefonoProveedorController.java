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
import org.crhistophergomez.bean.Proveedores;
import org.crhistophergomez.bean.TelefonoProveedor;
import org.crhistophergomez.db.Conexion;
import org.crhistophergomez.system.Main;


public class MenuTelefonoProveedorController implements Initializable{
    private Main escenarioPrincipal;
    
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO; 
    
    private ObservableList<TelefonoProveedor> listaTelefonoProveedor;
    private ObservableList <Proveedores> listaProveedores;
    
    @FXML private TextField txtCodigoTP;
    @FXML private TextField txtNumeroP;
    @FXML private TextField txtNumeroS;
    @FXML private TextField txtObservaciones;
    
    @FXML private ComboBox cmbCodigoProveedor;
    
    @FXML private TableView tblTelefonoProveedor;
    
    @FXML private TableColumn colCodigoTP;
    @FXML private TableColumn colNumeroP;
    @FXML private TableColumn colNumeroS;
    @FXML private TableColumn colObservaciones;
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
        tblTelefonoProveedor.setItems(getTelefonoProveedor());
        colCodigoTP.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoTelefonoProveedor"));
        colNumeroP.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("numeroPrincipal"));
        colNumeroS.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("numeroSecundario"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("observaciones"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoProveedor"));
    }
    
    public void seleccionarElementos(){
        txtCodigoTP.setText(String.valueOf(((TelefonoProveedor)tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
        txtNumeroP.setText(((TelefonoProveedor)tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
        txtNumeroS.setText(((TelefonoProveedor)tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroSecundario());
        txtObservaciones.setText(((TelefonoProveedor)tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getObservaciones());
        cmbCodigoProveedor.getSelectionModel().select(buscarProveedores(((TelefonoProveedor)tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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
    
    public TelefonoProveedor buscarTelefonoProveedor (int codigoTelefonoProveedor){
        TelefonoProveedor result = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTelefonoProvedor(?)}");
            procedimiento.setInt(1, codigoTelefonoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                result = new TelefonoProveedor (registro.getInt("codigoTelefonoProveedor"),
                        registro.getString("numeroPrincipal"),
                        registro.getString("numeroSecundario"),
                        registro.getString("observaciones"),
                        registro.getInt("codigoProveedor")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    public ObservableList<TelefonoProveedor> getTelefonoProveedor(){
        ArrayList<TelefonoProveedor> listaTP = new ArrayList<TelefonoProveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTelefonoProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                listaTP.add(new TelefonoProveedor (resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTelefonoProveedor = FXCollections.observableArrayList(listaTP);
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
         TelefonoProveedor registro = new TelefonoProveedor();
         registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTP.getText()));
         registro.setNumeroPrincipal(txtNumeroP.getText());
         registro.setNumeroSecundario(txtNumeroS.getText());
         registro.setObservaciones(txtObservaciones.getText());
         registro.setCodigoProveedor(((Proveedores)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         
         try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTelefonoProveedor(?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
                procedimiento.setString(2, registro.getNumeroPrincipal());
                procedimiento.setString(3, registro.getNumeroSecundario());
                procedimiento.setString(4, registro.getObservaciones());
                procedimiento.setInt(5, registro.getCodigoProveedor());
                procedimiento.execute();
            listaTelefonoProveedor.add(registro);
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
        txtCodigoTP.setEditable(false);
        txtNumeroP.setEditable(false);
        txtNumeroS.setEditable(false);
        txtObservaciones.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoTP.setEditable(true);
        txtNumeroP.setEditable(true);
        txtNumeroS.setEditable(true);
        txtObservaciones.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoTP.clear();
        txtNumeroP.clear();
        txtNumeroS.clear();
        txtObservaciones.clear();
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
