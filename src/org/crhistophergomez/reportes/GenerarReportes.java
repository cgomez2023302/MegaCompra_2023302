/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crhistophergomez.reportes;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.crhistophergomez.db.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametro){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{
            JasperReport reporteMaestro = (JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro, parametro, Conexion.getInstance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


/*
Interfaces Map

HasMap es uno de los objetos que implementa un conjunto de key-valu.
    Tiene un constructor sin parametros new HasMap() y su finalidad suele
    referirse para agrupar informacion en un unico objeto.

Tiene cierta similitud con la coleccion de objetos ArrayList pero que esta no tiene orden
El hasing (abierto-cerrado)
en la que se almacena el registro de una direccion que se genera por una funcion se aplica a la llave 
del registro dentro de memoria fisica

Has hace referencia a una tecnica de organizacion de archivos

en java el HasMap posee un espacio de memoria y cuando se guarda un objeto den dicho espacio
se determina su direccion, aplicando una funcion a la llave que le indiquemos.
Cada objeto se identifica mediante algun identificador apropiado.
*/