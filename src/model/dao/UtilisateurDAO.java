package model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import model.data.Departement;
import model.data.Utilisateur;

/**
 * Classe UtilisateurDAO pour la gestion des utilisateurs dans la base de données.
 * Hérite de la classe abstraite DAO avec le type générique Utilisateur.
 */
public class UtilisateurDAO extends DAO<Utilisateur> {

    /**
     * Crée un nouvel utilisateur dans la base de données.
     * @param utilisateur L'objet Utilisateur à créer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int create(Utilisateur utilisateur) {
        String query = "INSERT INTO UTILISATEUR(IDENTIFIANT , MDP) VALUES ('" + utilisateur.getIdentifiant() + "','" + utilisateur.getMdp() + "')";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Met à jour un utilisateur existant dans la base de données.
     * @param utilisateur L'objet Utilisateur à mettre à jour
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int update(Utilisateur utilisateur) {
        String query = "UPDATE UTILISATEUR SET IDENTIFIANT = '" + utilisateur.getIdentifiant() + "', MDP ='" + utilisateur.getMdp() + "' WHERE IDENTIFIANT ='" + utilisateur.getIdentifiant() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Supprime un utilisateur de la base de données.
     * @param utilisateur L'objet Utilisateur à supprimer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int delete(Utilisateur utilisateur) {
        String query = "DELETE FROM UTILISATEUR WHERE IDENTIFIANT = '" + utilisateur.getIdentifiant() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Récupère tous les utilisateurs de la base de données.
     * @return Une liste de tous les utilisateurs
     */
    public List <Utilisateur> findAll () {
        List <Utilisateur> users = new LinkedList <>();
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM UTILISATEUR");
            while (rs.next()) {
                String identifiant = rs.getString("IDENTIFIANT");
                String mdp = rs.getString("MDP");
                users.add(new Utilisateur(identifiant , mdp));
            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return users;
    }

    /**
     * Trouve un utilisateur par son identifiant (long).
     * (Cette méthode n'est pas implémentée car l'identifiant d'Utilisateur est un String)
     * @param id L'identifiant de l'utilisateur à trouver
     * @return Toujours null
     */
    public Utilisateur findById(long id){
        return null;
    }

    /**
     * Recherche un utilisateur par son identifiant (int).
     * @param id L'identifiant de l'utilisateur à rechercher
     * @return L'objet Utilisateur correspondant, ou null si non trouvé
     */
    public Utilisateur findById(String id) {
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM UTILISATEUR WHERE IDENTIFIANT = '" + id +"';");
            while (rs.next()) {
                String identifiant = rs.getString("IDENTIFIANT");
                String nomDep = rs.getString("MDP");

                return new Utilisateur(identifiant, nomDep);
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    /**
     * Recherche un utilisateur par son identifiant et son mot de passe.
     * @param identifiant L'identifiant de l'utilisateur
     * @param mdp Le mot de passe de l'utilisateur
     * @return L'objet Utilisateur correspondant, ou null si non trouvé
     */
    public Utilisateur findByLoginPwd2(String identifiant , String mdp) {
        try (Connection con = getConnection ();
        PreparedStatement st = con.prepareStatement("SELECT * FROM UTILISATEUR WHERE IDENTIFIANT = ? AND MDP = ?")) {
        st.setString (1, identifiant); st.setString (2, mdp);
        ResultSet rs = st.executeQuery ();
        while (rs.next()) {
            String id = rs.getString("IDENTIFIANT");
            String m = rs.getString("MDP");
            return new Utilisateur(id, m);
        }
        } catch (SQLException ex) { 
            ex.printStackTrace (); 
        }
        return null;
    }      
}
