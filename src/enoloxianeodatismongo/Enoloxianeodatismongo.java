/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enoloxianeodatismongo;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

/**
 *
 * @author oracle
 */
public class Enoloxianeodatismongo {

    public static final String ODB_NAME = "/home/oracle/NetBeansProjects/enoloxianeodatismongo/vinho";
    public static ODB odb = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        odb = ODBFactory.open(ODB_NAME);
        
        lectura_obxectos_vinho(odb);
        acidez_minmax_uva(odb);
        
        odb.close();

    }

    //mostrar base de datos vinho
    public static void lectura_obxectos_vinho(ODB odb) {

        Objects<Analisis> analise = odb.getObjects(Analisis.class);
        Objects<Uva> uvas = odb.getObjects(Uva.class);
        Objects<Cliente> clientes = odb.getObjects(Cliente.class);

        Analisis analisis = null;
        Uva uvitas = null;
        Cliente clientete = null;
        
        while (analise.hasNext()) {
            analisis = analise.next();
            System.out.println("Codigo:" + analisis.getCodigoa()
                    + " Acidez:" + analisis.getAcidez()
                    + " Tipo:" + analisis.getTipouva()
                    + " Cantidade:" + analisis.getCantidade());
        }
        System.out.println("----");
        while (uvas.hasNext()) {
            uvitas = uvas.next();
            System.out.println("Tipo:" + uvitas.getTipo()
                    + " Nome:" + uvitas.getNomeu()
                    + " Acidezmin:" + uvitas.getAcidezmin()
                    + " Acidemax:" + uvitas.getAcidezmax());
        }
        System.out.println("----");
        while (clientes.hasNext()) {
            clientete = clientes.next();
            System.out.println("DNI:" + clientete.getDni()
                    + " Nome:" + clientete.getNome()
                    + " Telf:" + clientete.getTelf()
                    + " Analisis:" + clientete.getNumerodeanalisis());
        }
        System.out.println("----");
    }
    
    //acidez min y max de cada uva
    public static void acidez_minmax_uva(ODB odb){
        
        Objects<Uva> uvas = odb.getObjects(Uva.class);
        Uva uvitas = null;
        while (uvas.hasNext()) {
            uvitas = uvas.next();
            System.out.println("Nome:" + uvitas.getNomeu()
                    + " Acidezmin:" + uvitas.getAcidezmin()
                    + " Acidemax:" + uvitas.getAcidezmax());
        }
        System.out.println("----");
    }
    
    public static void actualizar_analisis_clientes(ODB odb){
        
    }
}
