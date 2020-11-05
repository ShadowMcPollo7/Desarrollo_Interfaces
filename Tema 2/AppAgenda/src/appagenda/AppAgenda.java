
package appagenda;

import entidades.Provincia;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author raul-
 */
public class AppAgenda {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf=
            Persistence.createEntityManagerFactory("AppAgendaPU");
        EntityManager em=emf.createEntityManager();
        
        //Iniciar una transaccion para poder realizar operaciones
        //em.getTransaction().begin();
        //Para acabar las operaciones y confirmarlas
        //em.getTransaction().commit();
        
        //Creamos objeto Cadiz
        Provincia provinciaCadiz=new Provincia();
        provinciaCadiz.setNombre("Cadiz");

        //Creamos objeto Sevilla
        Provincia provinciaSevilla=new Provincia();
        provinciaSevilla.setNombre("Sevilla");
        
        //Realizar operaciones
        em.getTransaction().begin();
        em.persist(provinciaCadiz);
        em.persist(provinciaSevilla);
        em.getTransaction().commit();

        
        //Cerrar la conexion
        em.close();
        emf.close();
        try{
             DriverManager.getConnection("jdbc:derby:BDAgenda;shutdown=true");
           } catch (SQLException ex){}

    }
    
}
