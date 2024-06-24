package view;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * Classe principale de l'application pour gérer l'interface graphique de Communes bretonnes.
 */
public class AppCommunes extends Application {

    /**Représente la racine de l'interface utilisateur chargée à partir d'un fichier FXML */
    private static Parent root;
    
    /**
     * Démarre l'application JavaFX.
     * @param stage Le stage principal de l'application
     * @throws Exception En cas d'erreur lors du démarrage de l'application
     */
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass ().getResource("/connexion.fxml"));
        stage.getIcons().add(new Image("/logo.png"));
        stage.setTitle("Communes bretonnes");
        stage.setScene(new Scene(root ,900, 600));
        //stage.setFullScreen(true);
        stage.show();

    }

    /**
     * Point d'entrée principal de l'application JavaFX.
     * @param args Arguments de la ligne de commande (non utilisés dans cette application)
     * @throws SQLException En cas d'erreur d'accès à la base de données
     */
    public static void main(String[] args) throws SQLException {       
        Application.launch(AppCommunes.class , args);
    }

    /**
     * Obtient la racine de l'interface JavaFX parent.
     * @return La racine parent de la scène JavaFX
     */
    public static Parent getRoot() {
        return root;
    }

    /**
     * Définit la racine de l'interface JavaFX parent.
     * @param root La nouvelle racine parent à définir
     */
    public static void setRoot(Parent root) {
        AppCommunes.root = root;
    }   
}
