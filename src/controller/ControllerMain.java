package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.AppCommunes;
import javafx.event.ActionEvent;

/**
 * Contrôleur pour la vue principale de l'application.
 * Gère l'affichage des différentes vues en fonction des actions de l'utilisateur.
 */
public class ControllerMain {

    /**Bouton permettant de filtrer et d'afficher les vues liées aux aéroports */
    @FXML
    private ToggleButton aeroport;

    /**Bouton permettant de filtrer et d'afficher les vues liées aux départements */
    @FXML
    private ToggleButton departement;

    /**Bouton permettant de filtrer et d'afficher les vues liées aux communes */
    @FXML
    private ToggleButton commune;

    /**Panneau principal de l'interface utilisateur où sont affichées les données principales */
    @FXML
    private AnchorPane main;

    /**Bouton permettant de filtrer et d'afficher les vues liées aux années */
    @FXML
    private ToggleButton annee;

    /**Panneau où sont affichées les données spécifiques sélectionnées par l'utilisateur */
    @FXML
    private AnchorPane paneDonnee;

    /**Bouton permettant de filtrer et d'afficher les vues liées aux gares */
    @FXML
    private ToggleButton gare;

    /**Bouton permettant de filtrer et d'afficher les vues liées aux données */
    @FXML
    private ToggleButton donnee;

    /**Bouton permettant d'accéder aux fonctions déconnexion et changer de mot de passe */
    @FXML
    private MenuButton compte;

    /**MenuItem dépendant de compte permettant de se déconnecter */
    @FXML
    private MenuItem deconnexion;

    /**Bouton dépendant de compte permettant d'accéder à la focntion changement mot de passe */
    @FXML
    private MenuItem chgtMDP;

    /**Bouton permettant d'accéder à la fonction changement mot de passe */
    @FXML
    private Button donneesBrutes;

    /**Bouton permettant d'accéder à la page visualisation des données */
    @FXML
    private Button visualisation;


    /**
     * Initialise la classe controleur.
     * Initialise l'état d'origine des boutons.
     */
    @FXML
    public void initialize (){
        donneesBrutes.setId("pageActive");
        visualisation.setId(null);
    }

    /**
     * Charge et affiche la vue correspondante au bouton sélectionné par l'utilisateur.
     * Utilise un ScrollPane pour afficher le contenu de la vue chargée.
     * @param event L'événement de clic sur un bouton de type ToggleButton.
     */
    @FXML
    void loadData(ActionEvent event) {

        ToggleButton clickedButton = (ToggleButton) event.getSource();

        try {
            Parent content = null;
            if (clickedButton == departement) {
                content = FXMLLoader.load(getClass().getResource("/tableViewDepartement.fxml"));
            } 
            else if (clickedButton == commune) {
                content = FXMLLoader.load(getClass().getResource("/tableViewCommune.fxml"));
            } 
            else if (clickedButton == gare) {
                content = FXMLLoader.load(getClass().getResource("/tableViewGare.fxml"));
            } 
            else if (clickedButton == annee) {
                content = FXMLLoader.load(getClass().getResource("/tableViewAnnee.fxml"));
            } 
            else if (clickedButton == aeroport) {
                content = FXMLLoader.load(getClass().getResource("/tableViewAeroport.fxml"));
            } 
            else if (clickedButton == donnee) {
                content = FXMLLoader.load(getClass().getResource("/tableViewDonneeAnnuelle.fxml"));
            }

            if (content != null) {
                paneDonnee.getChildren().clear(); // Effacer les enfants de l'AnchorPane

                // Créer un ScrollPane pour contenir le contenu
                ScrollPane scrollPane = new ScrollPane(content);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                paneDonnee.getChildren().add(scrollPane); // Ajouter le ScrollPane à l'AnchorPane
                // Ajuster la taille du ScrollPane pour remplir l'AnchorPane
                AnchorPane.setTopAnchor(scrollPane, 0.0);
                AnchorPane.setRightAnchor(scrollPane, 0.0);
                AnchorPane.setBottomAnchor(scrollPane, 0.0);
                AnchorPane.setLeftAnchor(scrollPane, 0.0);
            }

        } catch (IOException e) {
            e.printStackTrace();
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
     * Change l'écran pour l'écran de visualisation des données.
     * @param event L'événement déclenché par le clic sur le bouton de visualisation des données.
     */
    @FXML
    public void goToVisualisation(ActionEvent event) {
        loadScene(event, "/visualisationDonnees.fxml");
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

