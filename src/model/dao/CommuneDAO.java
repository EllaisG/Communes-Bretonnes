package model.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util .*;
import model.data.Commune;
import model.data.Departement;
import model.data.Gare;

/**
 * Classe CommuneDAO pour la gestion des communes dans la base de données.
 * Hérite de la classe abstraite DAO avec le type générique Commune.
 */
public class CommuneDAO extends DAO <Commune> {

    /**
     * Crée une nouvelle commune dans la base de données.
     * @param commune L'objet Commune à créer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int create(Commune commune) {
        String query = "INSERT INTO COMMUNE(IDCOMMUNE, NOMCOMMUNE, LEDEPARTEMENT) VALUES ('" + commune.getIdCommune() + "','" + commune.getNomCommune() + "','" + commune.getDepartement().getIdDep() + "')";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            return -1;
        }
    }

    /**
     * Met à jour une commune existante dans la base de données.
     * @param commune L'objet Commune à mettre à jour
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int update(Commune commune) {
        String query = "UPDATE COMMUNE SET IDCOMMUNE = '" + commune.getIdCommune() + "', NOMCOMMUNE = '" + commune.getNomCommune()+"', LEDEPARTEMENT = '" + commune.getDepartement().getIdDep() + "' WHERE IDCOMMUNE = '" + commune.getIdCommune() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Supprime une commune de la base de données.
     * @param commune L'objet Commune à supprimer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int delete(Commune commune) {
        String query = "DELETE FROM COMMUNE WHERE IDCOMMUNE = '" + commune.getIdCommune() + "'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }

    /**
     * Trouve une commune par son identifiant.
     * @param id L'identifiant de la commune à trouver
     * @return L'objet Commune correspondant, ou null si non trouvé
     */
    public Commune findById(long id){
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM COMMUNE WHERE IDCOMMUNE = " + id);
            while (rs.next()) {
                int idCommune = rs.getInt("IDCOMMUNE");
                String nomCommune = rs.getString("NOMCOMMUNE");
                
                int idDep = rs.getInt("LEDEPARTEMENT");
                DepartementDAO departementDAO = new DepartementDAO();
                Departement departement = departementDAO.findById(idDep);
                
                return new Commune(idCommune, nomCommune, departement, null, null);
            }
        } catch (SQLException ex) {
             ex.printStackTrace (); 
        }
        return null;
    }

    /**
     * Trouve une commune par son identifiant (String).
     * (Cette méthode n'est pas implémentée car l'identifiant de Commune est un int)
     * @param id L'identifiant de la commune à trouver
     * @return Toujours null
     */
    public Commune findById(String id) {
        return null;
    }

    /**
     * Trouve toutes les communes dans la base de données.
     * @return Une liste de toutes les communes
     */
    public List <Commune> findAll () {
        List <Commune> communes = new LinkedList <>();
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM COMMUNE, DEPARTEMENT WHERE IDDEP = LEDEPARTEMENT ;");//, COMMUNE WHERE LACOMMUNE = IDCOMMUNE AND IDCOMMUNE = LACOMMUNE"); // Commune C1 C2, à voir...
            while (rs.next()) {
                int idCommune = rs.getInt("IDCOMMUNE");
                String nomCommune = rs.getString("NOMCOMMUNE");

                int idDep = rs.getInt("IDDEP");
                String nomDep = rs.getString("NOMDEP");
                int invCult2019 = rs.getInt("INVESTISSEMENTCULTUREL2019");
                Departement departement = new Departement(idDep, nomDep, invCult2019, null);
                communes.add(new Commune(idCommune, nomCommune, departement, null, null));

            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return communes;
    }

    /**
     * Trouve toutes les communes voisines d'une commune donnée.
     * @param commune La commune pour laquelle on veut trouver les communes voisines
     * @return Une liste des communes voisines
     */
    public ArrayList <Commune> findAllVoisines (Commune commune) {
        ArrayList <Commune> communesVoisines = new ArrayList<Commune>();
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM VOISINAGE, COMMUNE WHERE COMMUNEVOISINE = IDCOMMUNE  AND COMMUNE = "+ commune.getIdCommune() +";");//Commune C1 C2, à voir...
            while (rs.next()) {
                int communeVoisineId = rs.getInt("COMMUNEVOISINE");
                communesVoisines.add(findById(communeVoisineId));

            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return communesVoisines;
    }

    /**
     * Trouve toutes les gares associées à une commune donnée.
     * @param commune La commune pour laquelle on veut trouver les gares
     * @return Une liste des gares associées à la commune
     */
    public ArrayList <Gare> findAllGares (Commune commune) {
        ArrayList <Gare> garesVoisines = new ArrayList<Gare>();
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            ResultSet rs = st.executeQuery("SELECT * FROM GARE, COMMUNE WHERE LACOMMUNE = IDCOMMUNE  AND IDCOMMUNE = "+ commune.getIdCommune() +";");
            while (rs.next()) {
                int codeGare = rs.getInt("CODEGARE");
                GareDAO gareDAO = new GareDAO();
                garesVoisines.add(gareDAO.findById(codeGare));

            }
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
        return garesVoisines;
    }

    /**
     * Crée un nouveau voisiange dans la base de données.
     * @param commune Le premier paramètre de voisinage
     * @param communeVoisine la commune voisine de la première commune en paramètre
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int createVoisinage(Commune commune, Commune communeVoisine) {
        String query = "INSERT INTO VOISINAGE(COMMUNE, COMMUNEVOISINE) VALUES ('" + commune.getIdCommune() + "', '" + communeVoisine.getIdCommune() + "')";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
            return -1;
        }
    }

    /**
     * Supprime un voisiange de la base de données.
     * @param commune Le premier paramètre de voisinage
     * @param communeVoisine la commune voisine à supprimer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public int deleteVoisinage(Commune commune, Commune communeVoisine) {
        String query = "DELETE FROM VOISINAGE WHERE COMMUNEVOISINE = " + communeVoisine.getIdCommune() + " AND COMMUNE = '"+commune.getIdCommune() +"'";
        try (Connection con = getConnection (); Statement st = con.createStatement ()) {
            return st.executeUpdate(query);
        } catch (SQLException ex) {
        ex.printStackTrace ();
            return -1;
        }
    }
}