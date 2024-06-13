package org.crhistophergomez.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.crhistophergomez.reportes.GenerarReportes;
import org.crhistophergomez.system.Main;


public class MenuFacturaController implements Initializable{
    private Main escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO; 
    
    @FXML private Button btnRegresar;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReportes;
    
    @FXML private MenuItem btnDetalleFactura;
    
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReportes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void agregar(){
        
    }
    
    public void eliminar(){
        
    }
    
    public void editar(){
        
    }
    
    public void reporte(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                imprimirReporte();
                break;
            case ACTUALIZAR:
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
        //int factID = ((Factura)tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura();
        //parametros.put("factID", factID);
        GenerarReportes.mostrarReportes("ReportesFactura.jasper", "Reportes de las Facturas", parametros);
                
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
    public void clicDetalleFactura (ActionEvent event){
        if(event.getSource() == btnDetalleFactura){
            escenarioPrincipal.menuDetallesFacturas();
        }
    }
}
