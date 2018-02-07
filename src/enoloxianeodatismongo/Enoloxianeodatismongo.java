/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enoloxianeodatismongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

/**
 *
 * @author oracle
 */
public class Enoloxianeodatismongo {

    public static final String ODB_NAME = "/home/oracle/NetBeansProjects/enoloxianeodatismongo/vinho";
    public static ODB odb = null;
    public static MongoClient client;
    public static MongoDatabase database;
    public static MongoCollection<Document> coleccion;

    public static String nomeuva, tipouva, codigo, trataAcidez, dni;
    public static int acidez, total;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        odb = ODBFactory.open(ODB_NAME);

//        lectura_obxectos_vinho(odb);
//        acidez_minmax_uva(odb);
//        actualizar_analisis_clientes(odb);
        añadir_datos_xerado_mongo(odb);
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

    //acidez min y max de cada uva, seleccionando de la tabla Analisis el tipouva
    //y comparandolo con el tipouva de la tabla Uva
    public static void acidez_minmax_uva(ODB odb) {

        Objects<Analisis> analisis = odb.getObjects(Analisis.class);
        IQuery query;
        Uva uvass = null;
        Analisis analise = null;

        while (analisis.hasNext()) {
            analise = analisis.next();
            query = odb.criteriaQuery(Uva.class, Where.equal("tipouva", analise.getTipouva()));
            uvass = (Uva) odb.getObjects(query).getFirst();
            System.out.println(
                    "Nome:" + uvass.getNomeu()
                    + " Tipo:" + uvass.getTipo()
                    + " Acidezmin:" + uvass.getAcidezmin()
                    + " Acidemax:" + uvass.getAcidezmax());
        }
        System.out.println("----");
    }

    //actualizacion de analisis, seleccionando de la tabla Analisis el dni
    //y comparandolo con el dni de la tabla Cliente
    public static void actualizar_analisis_clientes(ODB odb) {

        Objects<Analisis> analisis = odb.getObjects(Analisis.class);
        IQuery query;
        Analisis analise = null;
        Cliente clientes = null;

        while (analisis.hasNext()) {
            analise = analisis.next();
            query = odb.criteriaQuery(Cliente.class, Where.equal("dni", analise.getDni()));
            clientes = (Cliente) odb.getObjects(query).getFirst();
            clientes.setNumerodeanalisis(clientes.getNumerodeanalisis() + 1);
            odb.store(clientes);

        }
    }

    //recoger los datos de las tablas Analisis y Uva, calculamos los datos necesarios
    //y los insertamos en la base de datos resultado y coleccion xerado
    public static void añadir_datos_xerado_mongo(ODB odb) {

        Objects<Analisis> analisis = odb.getObjects(Analisis.class);
        Analisis analise = null;
        while (analisis.hasNext()) {
            analise = analisis.next();

            tipouva = analise.getTipouva();
            acidez = analise.getAcidez();
            dni = analise.getDni();
            total = analise.getCantidade() * 15;
            codigo = analise.getCodigoa();

            IQuery uvas = new CriteriaQuery(Uva.class, Where.equal("tipouva", tipouva));
            Objects<Uva> uvazas = odb.getObjects(uvas);
            Uva uvitas = null;
            while (uvazas.hasNext()) {
                uvitas = uvazas.next();
                
                int min = uvitas.getAcidezmin();
                int max = uvitas.getAcidezmax();
                nomeuva = uvitas.getNomeu();

                if (acidez < min) {
                    trataAcidez = "subir acidez";
                } else if (acidez > max) {
                    trataAcidez = "bajar acidez";
                } else {
                    trataAcidez = "correcta";
                }

            }

            client = new MongoClient("localhost", 27017);
            database = client.getDatabase("resultado");
            coleccion = database.getCollection("xerado");

            Document docu = new Document("_id", codigo)
                    .append("uva", nomeuva)
                    .append("tratacidez", trataAcidez)
                    .append("total", total);
            coleccion.insertOne(docu);

        }
    }
}
