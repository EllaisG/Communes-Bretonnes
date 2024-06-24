package model.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import model.data.Annee;

/**
 * Classe AnneeDAO pour la gestion des années dans la base de données.
 * Hérite de la classe abstraite DAO avec le type générique Annee.
 */
public class AnneeDAO extends DAO <Annee> {

    /**
     * Crée une nouvelle année dans la base de données.
     * @param annee L'objet Annee à créer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int create(Annee annee) {
        String query = "INSERT INTO ANNEE(ANNEE, TAUXINFLATION) VALUES ('" + annee.getAnnee() + "','"  + annee.getTauxInflation() + "')";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Met à jour une année existante dans la base de données.
     * @param annee L'objet Annee à mettre à jour
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int update(Annee annee) {
        String query = "UPDATE ANNEE SET ANNEE = '" + annee.getAnnee() + "', TAUXINFLATION ='" + annee.getTauxInflation() + "' WHERE ANNEE ='" + annee.getAnnee()+ "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Supprime une année de la base de données.
     * @param annee L'objet Annee à supprimer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int delete(Annee annee) {
        String query = "DELETE FROM ANNEE WHERE ANNEE = '" + annee.getAnnee() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Trouve une année par son identifiant (String).
     * (Cette méthode n'est pas implémentée car l'identifiant d'Annee est un int)
     * @param id L'identifiant de l'année à trouver
     * @return Toujours null
     */
    public Annee findById(String id){
        return null;
    }

    /**
     * Trouve une année par son identifiant (long).
     * @param id L'identifiant de l'année à trouver
     * @return L'objet Annee correspondant, ou null si non trouvé
     */
    public Annee findById(long id) {
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM ANNEE WHERE ANNEE  = " + id);
            while (rs.next()) {
                int annee = rs.getInt("ANNEE");
                double tauxInflation = rs.getDouble("TAUXINFLATION");
                return new Annee(annee, tauxInflation);
            }
        } catch (SQLException ex) {
             ex.printStackTrace (); 
        }
        return null;
    }

    /**
     * Récupère la liste de toutes les années.
     * @return La liste des objets Annee
     */
    public List <Annee > findAll () {
        List <Annee > annees = new LinkedList <>();
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM ANNEE");
            while (rs.next()) {
                int annee = rs.getInt("ANNEE");
                double tauxInflation = rs.getDouble("TAUXINFLATION");

                annees.add(new Annee(annee, tauxInflation));
            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return annees;
    }
}