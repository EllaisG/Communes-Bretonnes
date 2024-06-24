package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.AppCommunes;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Contrôleur pour la vue des graphes de l'application.
 * Gère l'affichage des différentes vues en fonction des actions de l'utilisateur.
 */
public class ControllerVisualisation {

    
    /**Bouton permettant d'accéder à la page données brutes*/
    @FXML
    private Button donneesBrutes;

    /**Bouton permettant d'accéder à la page visualisation des données */
    @FXML
    private Button visualisation;

    /**Panneau principal de l'interface utilisateur */
    @FXML
    private AnchorPane main;

    /**Bouton permettant d'accéder au graphe représentant les degrés */
    @FXML
    private Button excentircite;

    /**Bouton permettant d'accéder au graphe représentant les degrés */
    @FXML
    private Button degres;

    /**Bouton permettant d'accéder au graphe mettant en valeur les communes principales */
    @FXML
    private Button principales;

    /**Bouton permettant d'accéder au graphe représentant les gares */
    @FXML
    private Button gare;

    /**ImageView permettant d'afficher les différents graphes */
    @FXML
    private ImageView view;

    /**Bouton permettant d'accéder aux fonctions déconnexion et changer de mot de passe */
    @FXML
    private MenuButton compte;

    /**MenuItem dépendant de compte permettant de se déconnecter */
    @FXML
    private MenuItem deconnexion;

    /**Bouton dépendant de compte permettant d'accéder à la focntion changement mot de passe */
    @FXML
    private MenuItem chgtMDP;

    /**
     * Initialise la classe du contrôleur.
     * Définit l'état initial des boutons et affiche l'image par défaut.
     */
    @FXML
    public void initialize (){
        donneesBrutes.setId(null);
        visualisation.setId("pageActive");
        // URL de l'image
        String imageUrl = "./onLoad.png";
        
        // Associez l'image à l'ImageView
        view.setImage(new Image(imageUrl));
    }

    /**
     * Change l'écran pour l'écran de changement de mot de passe.
     * @param event L'événement de clic sur le bouton de changement de mot de passe.
     */
    @FXML
    void loadView(ActionEvent event) {
        if (event.getSource() == degres){
            view.setImage(new Image("./degresGraphe.png"));
        } else if (event.getSource() == principales) {
            view.setImage(new Image("./communesPrincipales.png"));
        } else if (event.getSource() == excentircite){
            view.setImage(new Image("./excentriciteGraphe.png"));
        } else if (event.getSource() == gare){
            view.setImage(new Image("./gareGraphe.png"));
        }
        
    }

    /**
     * Déconnecte l'utilisateur en revenant à l'écran de connexion.
     * @param event L'événement de clic sur le bouton de déconnexion
     */
    @FXML
    public void deconnexion(ActionEvent event) {
        loadScene(event,"/connexion.fxml");
    }

    /**
     * Change l'écran pour l'écran de changement de mot de passe.
     * @param event L'événement de clic sur le bouton de changement de mot de passe
     */
    @FXML
    public void chgtMDP(ActionEvent event) {
        loadScene(event, "/changementMDP.fxml");
    }


    /**
     * Change l'écran pour l'écran principal.
     * @param event L'événement de clic sur le bouton pour retourner à l'écran principal.
     */
    @FXML
    public void goToMain(ActionEvent event) {
        loadScene(event, "/main.fxml");
    }


    /**
     * Charge et affiche une nouvelle scène en fonction du fichier FXML fourni.
     * @param event L'événement qui a déclenché le changement de scène.
     * @param fxmlFile Le fichier FXML à charger pour la nouvelle scène.
     */
    private void loadScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            AppCommunes.setRoot(root);
            Scene scene = new Scene(AppCommunes.getRoot(), 900, 600);
    
            // Récupérer la Stage de manière générique
            Object source = event.getSource();
            Stage stage = null;
    
            if (source instanceof Node) {
                stage = (Stage) ((Node) source).getScene().getWindow();
            } else {
                // Dans le cas où l'événement provient d'un MenuItem ou autre non-Node
                stage = (Stage) main.getScene().getWindow();
            }
    
            if (stage != null) {
                stage.setScene(scene);
            } else {
                System.err.println("Stage not found for source: " + source);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
