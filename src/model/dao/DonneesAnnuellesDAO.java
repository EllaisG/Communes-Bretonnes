package model.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import model.data.*;

/**
 * Classe DonneesAnnuellesDAO pour la gestion des données annuelles dans la base de données.
 * Hérite de la classe abstraite DAO avec le type générique DonneesAnnuelles.
 */
public class DonneesAnnuellesDAO extends DAO <DonneesAnnuelles> {

    /**
     * Crée de nouvelles données annuelles dans la base de données.
     * @param donneesAnnuelles L'objet DonneesAnnuelles à créer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int create(DonneesAnnuelles donneesAnnuelles) {
        String query = "INSERT INTO DONNEESANNUELLES(LANNEE, LACOMMUNE, NBMAISON, NBAPPART, PRIXMOYEN, PRIXM2MOYEN, SURFACEMOY, DEPENSESCULTURELLESTOTALES, BUDGETTOTAL, POPULATION) VALUES ('" + donneesAnnuelles.getAnnee().getAnnee() + "','" + donneesAnnuelles.getCommune().getIdCommune() + "','" + donneesAnnuelles.getNbMaison() + "','" + donneesAnnuelles.getNbAppart()+ "','" + donneesAnnuelles.getPrixMoyen()+ "','" + donneesAnnuelles.getPrixM2Moyen()+ "','" + donneesAnnuelles.getSurfaceMoy()+ "','" + donneesAnnuelles.getDepCulturellesTotales() + "','" + donneesAnnuelles.getBudgetTotal()+ "','" + donneesAnnuelles.getPopulation() + "')";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            return -1;
        }
    }

    /**
     * Met à jour des données annuelles existantes dans la base de données.
     * @param donneesAnnuelles L'objet DonneesAnnuelles à mettre à jour
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int update(DonneesAnnuelles donneesAnnuelles) {
        String query = "UPDATE DONNEESANNUELLES SET LANNEE ='" + donneesAnnuelles.getAnnee().getAnnee() + "', LACOMMUNE ='" + donneesAnnuelles.getCommune().getIdCommune() + "', NBMAISON ='" + donneesAnnuelles.getNbMaison() + "', NBAPPART ='" + donneesAnnuelles.getNbAppart() + "', PRIXMOYEN ='" + donneesAnnuelles.getPrixMoyen() + "', PRIXM2MOYEN ='" + donneesAnnuelles.getPrixM2Moyen() + "', SURFACEMOY ='" + donneesAnnuelles.getSurfaceMoy() + "', DEPENSESCULTURELLESTOTALES ='" + donneesAnnuelles.getDepCulturellesTotales() + "', BUDGETTOTAL = '" + donneesAnnuelles.getBudgetTotal() + "', POPULATION = '" + donneesAnnuelles.getPopulation() + "' WHERE LANNEE = '" + donneesAnnuelles.getAnnee().getAnnee() + "' AND LACOMMUNE = '" + donneesAnnuelles.getCommune().getIdCommune() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Supprime des données annuelles de la base de données.
     * @param donneesAnnuelles L'objet DonneesAnnuelles à supprimer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int delete(DonneesAnnuelles donneesAnnuelles) {
        String query = "DELETE FROM DONNEESANNUELLES WHERE LANNEE = '" + donneesAnnuelles.getAnnee().getAnnee() + "' AND LACOMMUNE = '" + donneesAnnuelles.getCommune().getIdCommune() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Trouve une donnée annuelle par son identifiant (long).
     * (Cette méthode n'est pas implémentée car il n'y a pas d'identifiant pour cette objet)
     * @param id L'identifiant de la donnée annuelle à trouver
     * @return Toujours null
     */
    public DonneesAnnuelles findById(long id){
        return null;
    }

    /**
     * Trouve une donnée annuelle par son identifiant (String).
     * (Cette méthode n'est pas implémentée car il n'y a pas d'identifiant pour cette objet)
     * @param id L'identifiant de la donnée annuelle à trouver
     * @return Toujours null
     */
    public DonneesAnnuelles findById(String id) {
        return null;
    }

    /**
     * Récupère toutes les données annuelles de la base de données.
     * @return Une liste de toutes les données annuelles
     */
    public List <DonneesAnnuelles> findAll () {
        List <DonneesAnnuelles> donneesAnnuelles = new LinkedList <>();
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM DONNEESANNUELLES, ANNEE, COMMUNE, DEPARTEMENT WHERE LANNEE = ANNEE AND ANNEE = LANNEE AND LACOMMUNE = IDCOMMUNE AND IDCOMMUNE = LACOMMUNE AND LEDEPARTEMENT = IDDEP AND IDDEP = LEDEPARTEMENT");
            while (rs.next()) {
                int annee = rs.getInt("ANNEE");
                double tauxInflation = rs.getDouble("TAUXINFLATION");

                int idCommune = rs.getInt("IDCOMMUNE");
                String nomCommune = rs.getString("NOMCOMMUNE");

                int idDep = rs.getInt("IDDEP");
                String nomDep = rs.getString("NOMDEP");
                double invesCulturel2019 = rs.getDouble("INVESTISSEMENTCULTUREL2019");

                int nbMaison = rs.getInt("NBMAISON");
                int nbAppart = rs.getInt("NBAPPART");
                double prixMoyen = rs.getDouble("PRIXMOYEN");
                double prixM2Moyen = rs.getDouble("PRIXM2MOYEN");
                double surfaceMoy = rs.getDouble("SURFACEMOY");
                double depensesCultTot = rs.getDouble("DEPENSESCULTURELLESTOTALES");
                double budgetTotal = rs.getDouble("BUDGETTOTAL");
                int population = rs.getInt("POPULATION");

                Annee lannee = new Annee(annee, tauxInflation);
                Departement departement = new Departement(idDep, nomDep, invesCulturel2019,null);
                Commune commune = new Commune(idCommune, nomCommune, departement, null, null);
                donneesAnnuelles.add(new DonneesAnnuelles(lannee, commune, nbMaison, nbAppart, prixMoyen, prixM2Moyen, surfaceMoy, depensesCultTot, budgetTotal, population));
            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return donneesAnnuelles;
    }
}