package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import model.dao.UtilisateurDAO;
import model.data.Utilisateur;
import view.AppCommunes;

/**
 * Contrôleur pour la vue de changement de mot de passe.
 */
public class ControllerMDP {

    /**Champ de texte pour saisir le premier mot de passe */
    @FXML
    private PasswordField password;

    /**Hyperlien pour permettre à l'utilisateur de passer à l'écran de connexion */
    @FXML
    private Button retour;

    /**Pane racine de l'interface d'inscription */
    @FXML
    private AnchorPane inscriptionPane;

    /**Bouton pour soumettre le formulaire d'inscription */
    @FXML
    private Button inscription;

    /**Zone de texte pour afficher des messages d'action lors de la connexion */
    @FXML
    private Text actionTarget;

    /**Champ de texte pour saisir le second mot de passe lors de la confirmation du changement */
    @FXML
    private PasswordField password1;

    /**Champ de texte pour saisir le nom d'utilisateur lors de la connexion */
    @FXML
    private TextField userName;

    /**Instance de UtilisateurDAO utilisée pour interagir avec la base de données des utilisateurs */
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    /**
     * Méthode appelée lors du changement de mot de passe.
     * Elle vérifie les champs saisis, vérifie l'ancien mot de passe,
     * puis met à jour le mot de passe de l'utilisateur.
     * @param event L'événement associé au changement de mot de passe
     */
    @FXML
    void chgtMDP(ActionEvent event) {
        String nom = userName.getText();
        String mdp = password.getText();
        String newMdp = password1.getText();
        try {
            Utilisateur utilisateur = utilisateurDAO.findById(nom);
        if (utilisateur.getMdp().equals(mdp)){
            try {
                utilisateur.nouveauMdp(mdp, newMdp);
                utilisateur = new Utilisateur(nom, newMdp);
                utilisateurDAO.update(utilisateur);
                actionTarget.setText("");
        
            } catch (IllegalArgumentException e) {
                actionTarget.setText("Le mot de passe est incorrect");
            }
        } else {
            actionTarget.setText("Ancien mot de passe incorrect");
        } 
        } catch (RuntimeException e){
            actionTarget.setText("Remplissez les champs demand\u00e9s");
        }
        
    }

    /**
     * Méthode appelée pour naviguer vers l'écran de connexion.
     * Elle charge la vue connexion.fxml et change la scène de l'application.
     * @param event L'événement associé au clic sur le lien de connexion
     */
    @FXML
    void goToConnexion(ActionEvent event) {
        Scene scene=null;
        try {
            Parent root = FXMLLoader.load(getClass ().getResource("/connexion.fxml"));
            AppCommunes.setRoot(root);
            scene = new Scene(AppCommunes.getRoot() , 900, 600);
        } catch (IOException e) {
            e.printStackTrace ();
        }
        Stage stage= (Stage) ((Node)event.getSource()).getScene ().getWindow ();
        stage.setScene(scene);
    }

    /**
     * Méthode appelée pour naviguer vers l'écran principale.
     * Elle charge la vue main.fxml et change la scène de l'application.
     * @param event L'événement associé au clic sur le lien de connexion
     */
    @FXML
    void retour(ActionEvent event) {
        Scene scene=null;
        try {
            Parent root = FXMLLoader.load(getClass ().getResource("/main.fxml"));
            AppCommunes.setRoot(root);
            scene = new Scene(AppCommunes.getRoot() , 900, 600);
        } catch (IOException e) {
            e.printStackTrace ();
        }
        Stage stage= (Stage) ((Node)event.getSource()).getScene ().getWindow ();
        stage.setScene(scene);
    }


}
