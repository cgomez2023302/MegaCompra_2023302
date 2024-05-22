package org.crhistophergomez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.crhistophergomez.system.Main;


public class MenuEmpleadoController implements Initializable{
    private Main escenarioPrincipal;
    
    @FXML private Button btnRegresar;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    
    @FXML private MenuItem btnCargoEmpleado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void agregar(){
        
    }
    
    public void eliminar(){
        
    }
    
    public void editar(){
        
    }
    
    public void reportes(){
        
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
    public void clicCargoEmpleado (ActionEvent event){
        if(event.getSource() == btnCargoEmpleado){
            escenarioPrincipal.menuCargoEmpleadoView();
        }
    }
}
