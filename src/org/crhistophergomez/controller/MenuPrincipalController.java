package org.crhistophergomez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.crhistophergomez.system.Main;

/**
 *
 * @author informatica
 */
public class MenuPrincipalController implements Initializable{
    private Main escenarioPrincipal;
    
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador;
    @FXML MenuItem btnProveedor;
    @FXML MenuItem btnCargoEmpleado;
    @FXML MenuItem btnTipoProducto;
    @FXML MenuItem btnCompras;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public Main getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clicClientes (ActionEvent event){
        if (event.getSource() == btnMenuClientes){
            escenarioPrincipal.menuClientesView();
        }
    }
    
    @FXML
    public void clicProgramador (ActionEvent event){
        if (event.getSource() == btnProgramador){
            escenarioPrincipal.programador();
        }
    }
    
    @FXML 
    public void clicProveedor (ActionEvent event){
        if(event.getSource() == btnProveedor){
            escenarioPrincipal.menuProveedorView();
        }
    }
    
    @FXML 
    public void clicCargoEmpleado (ActionEvent event){
        if(event.getSource() == btnCargoEmpleado){
            escenarioPrincipal.menuCargoEmpleadoView();
        }
    }
    
    @FXML 
    public void clicTipoProducto (ActionEvent event){
        if(event.getSource() == btnTipoProducto){
            escenarioPrincipal.menuTipoProductoView();
        }
    }
    
    @FXML 
    public void clicCompra (ActionEvent event){
        if(event.getSource() == btnCompras){
            escenarioPrincipal.menuCompraView();
        }
    }
}
