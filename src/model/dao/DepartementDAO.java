package model.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import model.data.Departement;

/**
 * Classe DepartementDAO pour la gestion des département dans la base de données.
 * Hérite de la classe abstraite DAO avec le type générique Departement.
 */
public class DepartementDAO extends DAO <Departement> {

    /**
     * Crée un nouveau département dans la base de données.
     * @param departement L'objet Departement à créer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int create(Departement departement) {
        String query = "INSERT INTO DEPARTEMENT(IDDEP, NOMDEP,  INVESTISSEMENTCULTUREL2019) VALUES ('" + departement.getIdDep() + "','" + departement.getNomDep() + "','" + departement.getInvesCulturel2019() + "')";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Met à jour un département existant dans la base de données.
     * @param departement L'objet Departement à mettre à jour
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int update(Departement departement) {
        String query = "UPDATE DEPARTEMENT SET IDDEP = '" + departement.getIdDep() + "', NOMDEP='" + departement.getNomDep() + "', INVESTISSEMENTCULTUREL2019='" + departement.getInvesCulturel2019() + "' WHERE IDDEP='" + departement.getIdDep()+ "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Supprime un département de la base de données.
     * @param departement L'objet Departement à supprimer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int delete(Departement departement) {
        String query = "DELETE FROM DEPARTEMENT WHERE IDDEP = '" + departement.getIdDep() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Trouve un département par son identifiant.
     * @param id L'identifiant du département à trouver.
     * @return L'objet Departement correspondant, ou null si non trouvé.
     */
    public Departement findById(long id){
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM DEPARTEMENT WHERE IDDEP = " + id);
            while (rs.next()) {
                int idDep = rs.getInt("IDDEP");
                String nomDep = rs.getString("NOMDEP");
                double invesCulturel2019 = rs.getDouble("INVESTISSEMENTCULTUREL2019");

                return new Departement(idDep, nomDep, invesCulturel2019, null);
            }
        } catch (SQLException ex) {
             ex.printStackTrace (); 
        }
        return null;
    }

    /**
     * Trouve un département par son identifiant (String).
     * (Cette méthode n'est pas implémentée car l'identifiant de Departement est un int)
     * @param id L'identifiant du departement à trouver
     * @return Toujours null
     */
    public Departement findById(String id) {
        return null;
    }

    /**
     * Trouve tous les départements dans la base de données.
     * @return Une liste de tous les départements
     */
    public List <Departement> findAll () {
        List <Departement> departements = new LinkedList <>();
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM DEPARTEMENT");
            while (rs.next()) {
                int idDep = rs.getInt("IDDEP");
                String nomDep = rs.getString("NOMDEP");
                double invesCulturel2019 = rs.getDouble("INVESTISSEMENTCULTUREL2019");
                departements.add(new Departement(idDep, nomDep, invesCulturel2019, null));
            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return departements;
    }
}