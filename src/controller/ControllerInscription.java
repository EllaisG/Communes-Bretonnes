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
 * Contrôleur pour l'interface d'inscription des utilisateurs.
 */
public class ControllerInscription {

    /**Champ de texte pour saisir le premier mot de passe lors de l'inscription */
    @FXML
    private PasswordField password;

    /**Hyperlien pour permettre à l'utilisateur de passer à l'écran de connexion */
    @FXML
    private Hyperlink connexionLien;

    /**Bouton pour soumettre le formulaire d'inscription */
    @FXML
    private Button inscription;

    /**Pane racine de l'interface d'inscription */
    @FXML
    private AnchorPane inscriptionPane;

    /**Zone de texte pour afficher des messages d'action lors de l'inscription */
    @FXML
    private Text actionTarget;

    /**Champ de texte pour saisir le second mot de passe lors de la confirmation d'inscription */
    @FXML
    private PasswordField password1;

    /**Champ de texte pour saisir le nom d'utilisateur lors de l'inscription */
    @FXML
    private TextField userName;

    /**Instance de UtilisateurDAO utilisée pour interagir avec la base de données des utilisateurs */
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    /**
     * Méthode appelée lorsqu'un utilisateur souhaite s'inscrire.
     * Vérifie la correspondance des mots de passe et enregistre
     * l'utilisateur dans la base de données.
     * @param event ActionEvent déclenché lors de l'inscription
     */
    @FXML
    void inscription(ActionEvent event) {
        String nom = userName.getText();
        String mdp = password.getText();
        String confirmationMdp = password1.getText();

        if (mdp.equals(confirmationMdp)){
            Utilisateur utilisateur = new Utilisateur(nom, mdp);
            utilisateurDAO.create(utilisateur);
        } else {
            actionTarget.setText("Le mot de passe est diff\u00e9rent du premier");
        }
        goToConnexion(event);
            
    }

    /**
     * Méthode appelée lorsqu'un utilisateur souhaite passer à l'écran de connexion.
     * Charge la vue de connexion et configure la scène principale avec cette vue.
     * 
     * @param event ActionEvent déclenché pour passer à la connexion
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
}
