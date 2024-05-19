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
import org.crhistophergomez.bean.Compras;
import org.crhistophergomez.db.Conexion;
import org.crhistophergomez.system.Main;


public class MenuComprasController implements Initializable{
    
    private Main escenarioPrincipal;
   
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO; 
    
    private ObservableList<Compras> listaCompra;
    
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtNumeroDocumentoCA;
    @FXML private TextField txtFechaDocumentoCA;
    @FXML private TextField txtDescripcionCA;
    @FXML private TextField txtTotalDocumentoCA;
    
    @FXML private TableView tblCompras;
    
    @FXML private TableColumn colNumeroDocumentoCA;
    @FXML private TableColumn colFechaDocumuentoCA;
    @FXML private TableColumn colDescripcionCA;
    @FXML private TableColumn colTotalDocumentoCA;
    
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
        tblCompras.setItems(getCompras());
        
        colNumeroDocumentoCA.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaDocumuentoCA.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("fechaDocumento"));
        colDescripcionCA.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("descripcion"));
        colTotalDocumentoCA.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("totalDocumento"));
    }
    
    public void seleccionarDatos(){
        txtNumeroDocumentoCA.setText(String.valueOf(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtFechaDocumentoCA.setText(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento());
        txtDescripcionCA.setText(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
        txtTotalDocumentoCA.setText(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento());
    }
    
    private ObservableList<Compras> getCompras(){
        ArrayList<Compras> lista = new ArrayList();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarCompras()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                resultado.getString("fechaDocumento"),
                resultado.getString("descripcion"),
                resultado.getString("totalDocumento")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCompra = FXCollections.observableArrayList(lista);
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
                imgAgregar.setImage(new Image("org/crhistophergomez/images/AgregarCompra.png"));
                imgEliminar.setImage(new Image("org/crhistophergomez/images/EliminarCompra.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
                
        }
    }
    
    public void guardar(){
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumentoCA.getText()));
        registro.setFechaDocumento(txtFechaDocumentoCA.getText());
        registro.setDescripcion(txtDescripcionCA.getText());
        registro.setTotalDocumento(txtTotalDocumentoCA.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call Sp_agregarCompras(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            
            procedimiento.execute();
            listaCompra.add(registro);
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
                imgAgregar.setImage(new Image("org/crhistophergomez/images/AgregarCompra.png"));
                imgEliminar.setImage(new Image("org/crhistophergomez/images/EliminarCompra.png"));
                
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
               
            default:
                if(tblCompras.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar al eliminar registro", "Eliminar Compras", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            
                            procedimiento.execute();
                            listaCompra.remove(tblCompras.getSelectionModel().getSelectedItem());
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
                if(tblCompras.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/crhistophergomez/images/Guardar.png"));
                    imgReportes.setImage(new Image("/org/crhistophergomez/images/Cancelar.png"));
                    activarControles();
                    txtNumeroDocumentoCA.setEditable(false);
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
                imgEditar.setImage(new Image("/org/crhistophergomez/images/AgregarCompra.png"));
                imgReportes.setImage(new Image("/org/crhistophergomez/images/Registros.png"));
                desactivarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCompras(?, ?, ?, ?)}");
            Compras registro = (Compras)tblCompras.getSelectionModel().getSelectedItem();
            
            registro.setFechaDocumento(txtFechaDocumentoCA.getText());
            registro.setDescripcion(txtDescripcionCA.getText());
            registro.setTotalDocumento(txtTotalDocumentoCA.getText());
            
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            
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
                imgEditar.setImage(new Image("/org/crhistophergomez/images/EditarCompra.png"));
                imgReportes.setImage(new Image("/org/crhistophergomez/images/Registros.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                
        }
    }
    
    public void desactivarControles(){
        txtNumeroDocumentoCA.setEditable(false);
        txtFechaDocumentoCA.setEditable(false);
        txtDescripcionCA.setEditable(false);
        txtTotalDocumentoCA.setEditable(false);
    }
    
    public void activarControles(){
        txtNumeroDocumentoCA.setEditable(true);
        txtFechaDocumentoCA.setEditable(true);
        txtDescripcionCA.setEditable(true);
        txtTotalDocumentoCA.setEditable(true);
    }
    
    public void limpiarControles(){
        txtNumeroDocumentoCA.clear();
        txtFechaDocumentoCA.clear();
        txtDescripcionCA.clear();
        txtTotalDocumentoCA.clear();
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
