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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.crhistophergomez.bean.Productos;
import org.crhistophergomez.bean.Proveedores;
import org.crhistophergomez.bean.TipoProducto;
import org.crhistophergomez.db.Conexion;
import org.crhistophergomez.system.Main;


public class MenuProductosController implements Initializable{
    private Main escenarioPrincipal;
    
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO; 
    
    private ObservableList <Productos> listaProductos;
    private ObservableList <Proveedores> listaProveedores;
    private ObservableList <TipoProducto> listaTipoProducto;
    
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoProducto;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecioU;
    @FXML private TextField txtPrecioD;
    @FXML private TextField txtPrecioM;
    @FXML private TextField txtImagenProducto;
    @FXML private TextField txtExistencia;
    
    @FXML private ComboBox cmbCodigoTipoProducto;
    @FXML private ComboBox cmbCodigoProveedor;
    
    @FXML private TableView tblProductos;
    
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colPrecioU;
    @FXML private TableColumn colPrecioD;
    @FXML private TableColumn colPrecioM;
    @FXML private TableColumn colImagenProducto;
    @FXML private TableColumn colExistencia;
    @FXML private TableColumn colCodigoTipoProducto;
    @FXML private TableColumn colCodigoProveedor;
    
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;
    
    @FXML private MenuItem btnTipoProducto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbCodigoTipoProducto.setItems(getTipoProducto());
        cmbCodigoProveedor.setItems(getProveedores());
    }
    
    public void cargaDatos(){
        tblProductos.setItems(getProducto());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImagenProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodigoTipoProducto.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    }
    
    public void selecionarElementos(){
       txtCodigoProducto.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
       txtDescripcion.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDescripcion());
       txtPrecioU.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
       txtPrecioD.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
       txtPrecioM.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
       txtImagenProducto.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getImagenProducto());
       txtExistencia.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
       cmbCodigoTipoProducto.getSelectionModel().select(buscarTipoProducto(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
       cmbCodigoProveedor.getSelectionModel().select(buscarProveedores(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }
    
    public TipoProducto buscarTipoProducto (int codigoTipoProducto){
        TipoProducto result = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                result = new TipoProducto(registro.getInt("codigoTipoProducto"),
                                            registro.getString("descripcion")
             
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }    
        return result;
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
    
    public ObservableList<Productos> getProducto(){
        ArrayList<Productos> listaProducto = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                listaProducto.add(new Productos (resultado.getInt("codigoProducto"),
                                        resultado.getString("descripcion"),
                                        resultado.getDouble("precioUnitario"),
                                        resultado.getDouble("precioDocena"),
                                        resultado.getDouble("precioMayor"),
                                        resultado.getString("imagenProducto"),
                                        resultado.getInt("existencia"),
                                        resultado.getInt("codigoTipoProducto"),
                                        resultado.getInt("codigoProveedor")            
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(listaProducto); 
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
    
     public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> listaProduc = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaProduc.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoProducto = FXCollections.observableList(listaProduc);
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
         Productos registro = new Productos();
         registro.setCodigoProducto(Integer.parseInt(txtCodigoProducto.getText()));
         registro.setDescripcion(txtDescripcion.getText());
         registro.setPrecioDocena(Double.parseDouble(txtPrecioD.getText()));
         registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
         registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
         registro.setImagenProducto(txtImagenProducto.getText());
         registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
         registro.setCodigoTipoProducto(((TipoProducto)cmbCodigoTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
         registro.setCodigoProveedor(((Proveedores)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         
         try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                procedimiento.setInt(1, registro.getCodigoProducto());
                procedimiento.setString(2, registro.getDescripcion());
                procedimiento.setDouble(3, registro.getPrecioUnitario());
                procedimiento.setDouble(4, registro.getPrecioDocena());
                procedimiento.setDouble(5, registro.getPrecioMayor());
                procedimiento.setString(6, registro.getImagenProducto());
                procedimiento.setInt(7, registro.getExistencia());
                procedimiento.setInt(8, registro.getCodigoProveedor());
                procedimiento.setInt(9, registro.getCodigoTipoProducto());
                procedimiento.execute();
            listaProductos.add(registro);
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
        txtCodigoProducto.setEditable(false);
        txtDescripcion.setEditable(false);
        txtPrecioU.setEditable(false);
        txtPrecioD.setEditable(false);
        txtPrecioM.setEditable(false);
        txtImagenProducto.setEditable(false);
        txtExistencia.setEditable(false);
        cmbCodigoTipoProducto.setEditable(false);
        cmbCodigoProveedor.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoProducto.setEditable(true);
        txtDescripcion.setEditable(true);
        txtPrecioU.setEditable(true);
        txtPrecioD.setEditable(true);
        txtPrecioM.setEditable(true);
        txtImagenProducto.setEditable(true);
        txtExistencia.setEditable(true);
        cmbCodigoTipoProducto.setEditable(true);
        cmbCodigoProveedor.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoProducto.clear();
        txtDescripcion.clear();
        txtPrecioU.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtImagenProducto.clear();
        txtExistencia.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbCodigoTipoProducto.getSelectionModel().getSelectedItem();
        cmbCodigoProveedor.getSelectionModel().getSelectedItem();
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
    public void clicTipoProducto (ActionEvent event){
        if(event.getSource() == btnTipoProducto){
            escenarioPrincipal.menuTipoProductoView();
        }
    }
}
