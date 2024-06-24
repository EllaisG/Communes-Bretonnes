package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

import model.data.Utilisateur;
import view.AppCommunes;
import model.dao.UtilisateurDAO;
import java.util.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur pour l'écran de connexion des utilisateurs.
 */
public class ControllerLogin {

    /**Hyperlien pour permettre à l'utilisateur de passer à l'écran d'inscription */
    @FXML
    private Hyperlink inscriptionLien;

    /**Champ de texte pour saisir le premier mot de passe lors de la connexion */
    @FXML
    private PasswordField password;

    /**Bouton pour se connecter */
    @FXML
    private Button seConnecter;

    /**Zone de texte pour afficher des messages d'action lors de la connexion */
    @FXML
    private Text actionTarget;

    /**Champ de texte pour saisir le nom d'utilisateur lors de la connexion */
    @FXML
    private TextField userName;

    /**Bouton home */
    @FXML
    private Button home;

    
    /**
     * Méthode appelée lorsqu'un utilisateur tente de se connecter.
     * Vérifie les informations de connexion (nom d'utilisateur et mot de passe)
     * et redirige vers l'écran principal en cas de succès.
     * Affiche des messages d'erreur en cas d'échec.
     * @param event ActionEvent déclenché lors de la tentative de connexion
     */
    @FXML
    void connexion(ActionEvent event) {
        String login = userName.getText();
        String loginBon = "Mauvais login";
        String mdp = "";

        UtilisateurDAO dao = new UtilisateurDAO();
        List<Utilisateur> users = dao.findAll();
        for (Utilisateur user : users){
            //On regarde si le login rentre est present dans les utilisateurs enregistres
            //Si c'est le cas on comparera ensuite le mdp rentree avec celui enregistre pour cet utilisateur
            if(login.equals(user.getIdentifiant())){
                loginBon = login;
                mdp = user.getMdp();
            }
        }
        if (!loginBon.equals("Mauvais login")){
            if(!password.getText().equals(mdp)){
                actionTarget.setText("Wrong password");
            }
            else{
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
        } else {
            actionTarget.setText("Wrong username");
        }
    }

    /**
     * Méthode pour naviguer vers l'écran d'inscription lorsque l'utilisateur 
     * clique sur le lien correspondant.
     * @param event ActionEvent déclenché lors du clic sur le lien d'inscription
     */
    public void goToInscription (ActionEvent event){
        Scene scene=null;
        try {
            Parent root = FXMLLoader.load(getClass ().getResource("/inscription.fxml"));
            AppCommunes.setRoot(root);
            scene = new Scene(AppCommunes.getRoot() , 900, 600);
        } catch (IOException e) {
            e.printStackTrace ();
        }
        Stage stage= (Stage) ((Node)event.getSource()).getScene ().getWindow ();
        stage.setScene(scene);
    }

}