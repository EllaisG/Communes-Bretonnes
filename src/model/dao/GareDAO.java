package model.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import model.data.Commune;
import model.data.Departement;
import model.data.Gare;

/**
 * Classe GareDAO pour la gestion des gares dans la base de données.
 * Hérite de la classe abstraite DAO avec le type générique Gare.
 */
public class GareDAO extends DAO <Gare> {

    /**
     * Crée une nouvelle gare dans la base de données.
     * @param gare L'objet Gare à créer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int create(Gare gare){
        String query = "INSERT INTO GARE(CODEGARE, NOMGARE, ESTFRET, ESTVOYAGEUR, LACOMMUNE) VALUES ('" + gare.getCodeGare() + "','" + gare.getNomGare() + "','" + gare.getEstFret() + "','" + gare.getEstVoyageur()+ "','" + gare.getCommune().getIdCommune()  + "')";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            return -1;
        }
    }

    /**
     * Met à jour une gare existante dans la base de données.
     * @param gare L'objet Gare à mettre à jour
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int update(Gare gare) {
        String query = "UPDATE GARE SET CODEGARE = '" + gare.getCodeGare() + "', NOMGARE='" + gare.getNomGare() + "', ESTFRET='" + gare.getEstFret() + "', ESTVOYAGEUR='" + gare.getEstVoyageur() + "', LACOMMUNE='" + gare.getCommune().getIdCommune() + "' WHERE CODEGARE='" + gare.getCodeGare()+ "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Supprime une gare de la base de données.
     * @param gare L'objet Gare à supprimer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int delete(Gare gare) {
        String query = "DELETE FROM GARE WHERE CODEGARE = '" + gare.getCodeGare() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Recherche une gare par son identifiant (code de la gare).
     * @param id L'identifiant de la gare à rechercher
     * @return L'objet Gare correspondant, ou null si non trouvé
     */
    public Gare findById(long id){
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM GARE WHERE CODEGARE = " + id);
            while (rs.next()) {
                int codeGare = rs.getInt("CODEGARE");
                String nomGare = rs.getString("NOMGARE");
                boolean estFret = rs.getBoolean("ESTFRET");
                boolean estVoyageur = rs.getBoolean("ESTVOYAGEUR");

                int idCommune = rs.getInt("LACOMMUNE");
                CommuneDAO communeDAO = new CommuneDAO();
                Commune commune = communeDAO.findById(idCommune);
                return new Gare(codeGare, nomGare, estFret, estVoyageur, commune);
            }
        } catch (SQLException ex) {
             ex.printStackTrace (); 
        }
        return null;
    }

    /**
     * Trouve une gare par son identifiant (String).
     * (Cette méthode n'est pas implémentée car l'identifiant de Gare est un int)
     * @param id L'identifiant de la gare à trouver
     * @return Toujours null
     */
    public Gare findById(String id) {
        return null;
    }

    /**
     * Récupère toutes les gares de la base de données.
     * @return Une liste de toutes les gares
     */
    public List <Gare> findAll () {
        List <Gare> gares = new LinkedList <>();
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM GARE, COMMUNE, DEPARTEMENT WHERE LACOMMUNE = IDCOMMUNE AND IDCOMMUNE = LACOMMUNE AND LEDEPARTEMENT = IDDEP AND IDDEP = LEDEPARTEMENT");
            while (rs.next()) {
                int codeGare = rs.getInt("CODEGARE");
                String nomGare = rs.getString("NOMGARE");
                boolean estFret = rs.getBoolean("ESTFRET");
                boolean estVoyageur = rs.getBoolean("ESTVOYAGEUR");

                int idCommune = rs.getInt("IDCOMMUNE");
                String nomCommune = rs.getString("NOMCOMMUNE");
                

                int idDep = rs.getInt("IDDEP");
                String nomDep = rs.getString("NOMDEP");
                double invesCulturel2019 = rs.getDouble("INVESTISSEMENTCULTUREL2019");

                Departement departement = new Departement(idDep, nomDep, invesCulturel2019,null);
                Commune commune = new Commune(idCommune, nomCommune, departement, null, null);
                gares.add(new Gare(codeGare, nomGare, estFret, estVoyageur, commune));
            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return gares;
    }
}