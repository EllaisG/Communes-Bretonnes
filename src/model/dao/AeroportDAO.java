package model.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import model.data.Aeroport;
import model.data.Departement;

/**
 * Classe AeroportDAO pour la gestion des aéroports dans la base de données.
 * Hérite de la classe abstraite DAO avec le type générique Aeroport.
 */
public class AeroportDAO extends DAO <Aeroport> {

    /**
     * Crée un nouvel aéroport dans la bas de données.
     * @param aeroport l'objet Aeroport à créer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int create(Aeroport aeroport) {
        String query = "INSERT INTO AEROPORT(NOM, ADRESSE, LEDEPARTEMENT) VALUES ('" + aeroport.getNomAeroport() + "','"  + aeroport.getAdresseAeroport() + "','" + aeroport.getDepartement().getIdDep() + "')";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Met à jour un aéroport existant dans la base de données.
     * @param aeroport L'objet Aeroport à mettre à jour
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int update(Aeroport aeroport) {
        String query = "UPDATE AEROPORT SET NOM = '" + aeroport.getNomAeroport() + "', ADRESSE='" + aeroport.getAdresseAeroport() + "', LEDEPARTEMENT='" + aeroport.getDepartement().getIdDep() + "' WHERE NOM ='" + aeroport.getNomAeroport()+ "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Supprime un aéroport de la base de données.
     * @param aeroport L'objet Aeroport à supprimer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int delete(Aeroport aeroport) {
        String query = "DELETE FROM AEROPORT WHERE NOM = '" + aeroport.getNomAeroport() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Trouve un aéroport par son identifiant (nom).
     * @param id Le nom de l'aéroport à trouver.
     * @return L'objet Aeroport correspondant, ou null si non trouvé.
     */
    public Aeroport findById(String id){
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM AEROPORT WHERE NOM  = " + id);
            while (rs.next()) {
                String nomAeroport = rs.getString("NOM");
                String adresse = rs.getString("ADRESSE");

                int idDep = rs.getInt("LEDEPARTEMENT");
                String nomDep = rs.getString("NOMDEP");
                double invesCulturel2019 = rs.getInt("INVESTISSEMENTCULTUREL2019");
                Departement departement = new Departement(idDep, nomDep, invesCulturel2019, null);
                return new Aeroport(nomAeroport, adresse, departement);
            }
        } catch (SQLException ex) {
             ex.printStackTrace (); 
        }
        return null;
    }

    /**
     * Trouve un aéroport par son identifiant (long).
     * (Cette méthode n'est pas implémentée car l'identifiant d'Aeroport est un String)
     * @param id L'identifiant de l'aéroport à trouver
     * @return Toujours null
     */
    public Aeroport findById(long id) {
        return null;
    }

    /**
     * Récupère la liste de tous les aéroports.
     * @return La liste des objets Aeroport.
     */
    public List <Aeroport > findAll () {
        List <Aeroport > aeroports = new LinkedList <>();
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM AEROPORT, DEPARTEMENT WHERE LEDEPARTEMENT = IDDEP");
            while (rs.next()) {
                String nomAeroport = rs.getString("NOM");
                String adresse = rs.getString("ADRESSE");

                int idDep = rs.getInt("IDDEP");
                String nomDep = rs.getString("NOMDEP");
                double invesCulturel2019 = rs.getDouble("INVESTISSEMENTCULTUREL2019");
                Departement departement = new Departement(idDep, nomDep, invesCulturel2019, null);
                aeroports.add(new Aeroport(nomAeroport, adresse, departement));

            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return aeroports;
    }
}