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
        
        amosar_analisis(odb);
        System.out.println("-----");
        amosar_uva(odb);
        System.out.println("-----");
        amosar_cliente(odb);
        
        odb.close();

    }

    //mostrar datos de analisis con la base de datos vinho
    public static void amosar_analisis(ODB odb) {

        Objects<Analisis> analise = odb.getObjects(Analisis.class);
        Analisis analisis = null;
        while (analise.hasNext()) {
            analisis = analise.next();
            System.out.println("Codigo:" + analisis.getCodigoa()
                    + " Acidez:" + analisis.getAcidez()
                    + " Tipo:" + analisis.getTipouva()
                    + " Cantidade:" + analisis.getCantidade());
        }
    }
    
    //mostrar datos de uva con la base de datos vinho
    public static void amosar_uva(ODB odb) {

        Objects<Uva> uvas = odb.getObjects(Uva.class);
        Uva uvitas = null;
        while (uvas.hasNext()) {
            uvitas = uvas.next();
            System.out.println("Tipo:" + uvitas.getTipo()
                    + " Nome:" + uvitas.getNomeu()
                    + " Acidezmin:" + uvitas.getAcidezmin()
                    + " Acidemax:" + uvitas.getAcidezmax());
        }
    }

    //mostrar datos de cliente con la base de datos vinho
    public static void amosar_cliente(ODB odb) {

        Objects<Cliente> clientes = odb.getObjects(Cliente.class);
        Cliente clientete = null;
        while (clientes.hasNext()) {
            clientete = clientes.next();
            System.out.println("DNI:" + clientete.getDni()
                    + " Nome:" + clientete.getNome()
                    + " Telf:" + clientete.getTelf()
                    + " Analisis:" + clientete.getNumerodeanalisis());
        }
    }
}
