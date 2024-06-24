package model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe abstraite DAO pour les opérations de base de données génériques.
 * @param <T> Le type d'objet manipulé par le DAO.
 */
public abstract class DAO <T> {

    /**L'url de la base de données */
    private static String url = "jdbc:mysql://localhost:3306/bd_iut";
    /**Le nom de la classe du driver JDBC */
    private static String driverClassName = "com.mysql.cj.jdbc.Driver"; //"mysql-connector-j-8.4.0";//
    /**Le nom d'utilisateur pour la connexion à la base de données */
    private static String login = "root";//change to non static and nothing initialized (for pwd too) 
    /** Le mot de passe pour la connexion à la base de données */
    private static String password =""; // méthodes pour lire les attributs plutôt que d'écrire en dur

    /**
     * Obtient une connexion à la base de données.
     * @return Une connexion à la base de données, ou null en cas d'erreur
     * @throws SQLException Si une erreur SQL survient lors de la connexion
     */
    public static Connection getConnection() throws SQLException {//change public static for protected
        // Charger la classe du pilote
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace ();
            return null;
        }
        // Obtenir la connection
        return DriverManager.getConnection(url , login , password);
    }
    
    /**
     * Trouve tous les objets de type T.
     * @return Une liste de tous les objets de type T
     */
    public abstract List<T> findAll();

    /**
     * Trouve un objet de type T par son identifiant (long).
     * @param id L'identifiant de l'objet à trouver
     * @return L'objet de type T correspondant, ou null si non trouvé
     */
    public abstract T findById(long id);

    /**
     * Trouve un objet de type T par son identifiant (String).
     * @param id L'identifiant de l'objet à trouver
     * @return L'objet de type T correspondant, ou null si non trouvé
     */
    public abstract T findById(String id);

    /**
     * Met à jour un objet de type T.
     * @param element L'objet de type T à mettre à jour
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public abstract int update(T element);

    /**
     * Crée un nouvel objet de type T.
     * @param element L'objet de type T à créer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public abstract int create(T element);

    /**
     * Supprime un objet de type T.
     * @param element L'objet de type T à supprimer
     * @return Le nombre de lignes affectées, ou -1 en cas d'erreur
     */
    public abstract int delete(T element);

    // Ajout des méthodes qui ne sont pas spécialisées à toutes les classes
    // (par exemple une méthode qui get des listes dans données annuelles)
}