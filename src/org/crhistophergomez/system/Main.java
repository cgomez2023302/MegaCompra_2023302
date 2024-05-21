package org.crhistophergomez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.crhistophergomez.controller.MenuCargoEmpleadoController;
import org.crhistophergomez.controller.MenuClientesController;
import org.crhistophergomez.controller.MenuComprasController;
import org.crhistophergomez.controller.MenuDetalleComprasController;
import org.crhistophergomez.controller.MenuPrincipalController;
import org.crhistophergomez.controller.MenuProveedoresController;
import org.crhistophergomez.controller.MenuTipoProductoController;
import org.crhistophergomez.controller.ProgramadorController;

/**
 *
 * @author informatica
 */
public class Main extends Application {
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/crhistophergomez/view/";
    
    @Override 
    public void start(Stage escenarioPrincipal)throws Exception{
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("MegaCompra");
        escenarioPrincipal.getIcons().add(new Image(Main.class.getResourceAsStream("/org/crhistophergomez/images/Logo.png")));
        menuPrincipalView();
        escenarioPrincipal.show();
    }
    
    public Initializable cambiarEscena(String fxmlName, int width, int heigth)throws Exception{
        Initializable result;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        escena = new Scene ((AnchorPane)loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        
        result = (Initializable)loader.getController();
        
        return result;
    }
    
    public void menuPrincipalView (){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 803,484);
            menuPrincipalView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuClientesView(){
        try{
            MenuClientesController menuClienteView = (MenuClientesController)cambiarEscena("MenuClientesView.fxml", 756,422);
            menuClienteView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void programador(){
        try{
            ProgramadorController programadorView = (ProgramadorController)cambiarEscena("ProgramadorView.fxml", 400, 395);
            programadorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuProveedorView(){
        try{
            MenuProveedoresController menuProveedorView = (MenuProveedoresController)cambiarEscena("MenuProveedorView.fxml", 713, 400);
            menuProveedorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuCargoEmpleadoView(){
        try{
            MenuCargoEmpleadoController menuCargoEmpleadoView = (MenuCargoEmpleadoController)cambiarEscena("MenuCargoEmpleado.fxml", 745, 422);
            menuCargoEmpleadoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuTipoProductoView(){
        try{
            MenuTipoProductoController menuTipoProcuctoView = (MenuTipoProductoController)cambiarEscena("MenuTipoProductoView.fxml", 713, 400);
            menuTipoProcuctoView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuCompraView(){
        try{
            MenuComprasController menuCompraView = (MenuComprasController)cambiarEscena("MenuComprasView.fxml", 713, 400);
            menuCompraView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void menuDetalleCompras(){
        try{
            MenuDetalleComprasController menuDetalleComprasView = (MenuDetalleComprasController)cambiarEscena("MenuDetalleCompras", 713, 400);
            menuDetalleComprasView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuDetallesFacturas(){
        try{
            MenuDetallesFacturasController menuDetallesFacturas =
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuEmailProveedores(){
        try{
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuEmpleado(){
        try{
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuFactura(){
        try{
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuProductos(){
        try{
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuTelefonoProveedor(){
        try{
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
