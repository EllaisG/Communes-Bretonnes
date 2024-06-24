package controller;

import model.data.*;
import model.fileAccess.FileAccess;

import view.AppCommunes;
import model.dao.AnneeDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Contrôleur pour la vue TableViewAnnee.fxml, gère l'affichage et la manipulation des données des années.
 */
public class ControllerTableAnnee {

    /**TableColumn qui affiche l'année dans la TableView */
    @FXML
    private TableColumn<Annee, Integer> anneeTableColumn;

    /**TableColumn qui affiche le taux d'inflation de l'année dans la TableView */
    @FXML
    private TableColumn<Annee, Double> tauxTableColumn;

    /**TableView qui affiche la liste des années */
    @FXML
    private TableView<Annee> tableAnnee;

    /**GridPane qui contient les boutons d'action pour la gestion des années */
    @FXML
    private GridPane gridAction;

    /**Label pour afficher le année sélectionnée pour modification */
    @FXML
    private Label anneeLabel;

    /**Label pour afficher le taux d'inflation de l'année sélectionné pour modification */
    @FXML
    private Label tauxLabel;

    /**Champ de texte pour entrer l'année du nouvel objet Annee à ajouter */
    @FXML
    private TextField anneeAjout;

    /**Champ de texte pour entrer le taux d'inflation du nouvel objet Annee à ajouter */
    @FXML
    private TextField tauxAjout;

    /**Bouton pour ajouter une nouvelle année */
    @FXML
    private Button ajouter;

    /**Bouton pour supprimer l'année sélectionné dans la TableView */
    @FXML
    private Button supprimer;

    /**Bouton pour mettre à jour les informations de l'année sélectionné dans la TableView */
    @FXML
    private Button update;

    /**Bouton pour retourner à l'écran précédent */
    @FXML
    private Button retour;

    /**Bouton pour agrandir la vue principale */
    @FXML
    private Button agrandir;

    /**Texte pour afficher des messages d'action ou d'erreur */
    @FXML
    private Text actionTarget;

    /**Bouton pour annuler les modifications et retourner à l'affichage précédent */
    @FXML
    private Button retourModif;

    /**Bouton pour ajouter une nouvelle année après avoir modifié l'affichage */
    @FXML
    private Button ajouter1;

    /**Bouton pour mettre à jour les informations de l'année après avoir modifié l'affichage */
    @FXML
    private Button update1;

    /**Bouton pour exporter les données des années vers un fichier CSV */
    @FXML
    private Button export;

    /**Champ de texte pour saisir le texte de recherche pour filtrer les années affichées */
    @FXML
    private TextField searchBar;

    /**DAO pour interagir avec la base de données et gérer les objets Annee */
    private AnneeDAO anneeDAO;

    /**
     * Initialise le contrôleur.
     * Charge les données dans la TableView et initialise les écouteurs d'événements.
     */
    @FXML
    public void initialize(){
        anneeDAO = new AnneeDAO();
        loadTableData();

        // Add a listener to the table selection model
        tableAnnee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Enable the "Supprimer" button if a row is selected
            supprimer.setDisable(newValue == null);
            update.setDisable(newValue == null);
        });

        // Add a listener to the searchBar text property
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTableData(newValue);
        });
    }

    /**
     * Charge les données des années depuis la base de données et les affiche dans la TableView.
     */
    private void loadTableData() {
        List<Annee> anneeList = anneeDAO.findAll();

        ObservableList<Annee> anneeObservableList = FXCollections.observableArrayList(anneeList);
        
        anneeTableColumn.setCellValueFactory(new PropertyValueFactory<Annee, Integer>("annee"));
        tauxTableColumn.setCellValueFactory(new PropertyValueFactory<>("tauxInflation"));
        tableAnnee.setItems(anneeObservableList);
    }

    /**
     * Gère l'action de modification d'une année sélectionnée.
     * Affiche les champs de modification et désactive les boutons d'action associés.
     * @param event L'événement d'action déclenché par l'utilisateur
     */
    @FXML
    void handleModifyAction(ActionEvent event) {
        gridAction.setId("modifEnCours");
        supprimer.setVisible(false);
        ajouter.setVisible(false);
        update.setVisible(false);
        retourModif.setVisible(true);
        anneeLabel.setVisible(true);
        anneeAjout.setVisible(true);
        tauxLabel.setVisible(true);
        tauxAjout.setVisible(true);
        if ((Button) event.getSource() == ajouter){
            ajouter1.setVisible(true);
        }
        else if ((Button) event.getSource() == update){
            Annee selectedAnnee = tableAnnee.getSelectionModel().getSelectedItem();
                if (selectedAnnee != null) {
                    anneeAjout.setText(selectedAnnee.getAnnee()+"");
                }
            update1.setVisible(true);
        }
    }

    /**
     * Gère l'action de suppression d'une année sélectionnée.
     * Supprime l'année sélectionnée de la base de données et actualise la TableView.
     * @param event L'événement d'action déclenché par l'utilisateur
     */
    @FXML
    void handleDeleteAction(ActionEvent event) {
        Annee selectedAnnee = tableAnnee.getSelectionModel().getSelectedItem();
        if (selectedAnnee != null) {
            anneeDAO.delete(selectedAnnee);
            loadTableData();
        }
    }  

    /**
     * Gère l'action d'ajout d'une nouvelle année.
     * Crée une nouvelle année avec les valeurs saisies et l'ajoute à la base de données.
     * @param event L'événement d'action déclenché par l'utilisateur
     */
    @FXML
    void handleAddAction(ActionEvent event) {
        try {
            int lannee = Integer.parseInt(anneeAjout.getText());
            double taux = Double.parseDouble(tauxAjout.getText());
            Annee annee = new Annee(lannee, taux);
            int checkException = anneeDAO.create(annee);
            if (checkException == -1){
                actionTarget.setText("Ce code est d\u00e9j\u00e0 utilis\u00e9e par une autre gare");
            }
        }
        catch (RuntimeException e){
            actionTarget.setText("Param\u00e8tres invalides");
        }
        actionTarget.setText("");
        emptyTextField();
        loadTableData();
    }

    /**
     * Gère l'action de mise à jour des informations d'une année sélectionnée.
     * Met à jour les valeurs de l'année sélectionnée dans la base de données.
     * @param event L'événement d'action déclenché par l'utilisateur
     */
    @FXML
    void handleUpdateAction(ActionEvent event) {
        Annee annee = tableAnnee.getSelectionModel().getSelectedItem();
        try {
            annee.setAnnee(Integer.parseInt(anneeAjout.getText()));
            annee.setTauxInflation(Double.parseDouble(tauxAjout.getText()));
            anneeDAO.update(annee);
        }
        catch (RuntimeException e){
            actionTarget.setText("Param\u00e8tres invalides");
        }
        actionTarget.setText("");
        emptyTextField();
        loadTableData();
    }

    /**
     * Réinitialise les champs de texte utilisés pour l'ajout ou la modification des années.
     */
    public void emptyTextField(){
        anneeAjout.setText("");
        tauxAjout.setText("");
    }

    /**
     * Annule les modifications en cours et rétablit l'affichage précédent.
     * Cache les champs de modification et réactive les boutons d'action.
     * @param event L'événement d'action déclenché par l'utilisateur
     */
    @FXML
    public void retourModif (ActionEvent event){
        emptyTextField();

        gridAction.setId("actionGrid");

        supprimer.setVisible(true);
        ajouter.setVisible(true);
        update.setVisible(true);
        retourModif.setVisible(false);
        anneeLabel.setVisible(false);
        anneeAjout.setVisible(false);
        tauxLabel.setVisible(false);
        tauxAjout.setVisible(false);
        ajouter1.setVisible(false);
        update1.setVisible(false);
    
    }

    /**
     * Gère l'action d'agrandir ou de retourner à la vue principale en plein écran.
     * En fonction de l'état actuel de l'application (vue principale ou agrandie),
     * ajuste la taille de la fenêtre et modifie le texte du bouton "Retour" en conséquence.
     * @param event L'événement d'action déclenché par l'utilisateur
     */
    public void agrandirEtRetour (ActionEvent event ){

        if(AppCommunes.getRoot().getId().equals("main")){
            
            Scene scene=null;
            try {
                Parent root = FXMLLoader.load(getClass ().getResource("/tableViewAnnee.fxml"));
                AppCommunes.setRoot(root);
                scene = new Scene(AppCommunes.getRoot() , 900, 600);
            } catch (IOException e) {
                e.printStackTrace ();
            }
            Stage stage = (Stage) ((Node)event.getSource()).getScene ().getWindow ();
            stage.setScene(scene);
            stage.setFullScreen(true);
        }

        else if(AppCommunes.getRoot().getId().equals("anneeTable")){
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

    /**
     * Exporte les données des années affichées dans la TableView vers un fichier CSV.
     * Utilise la classe FileAccess pour écrire les données dans le fichier spécifié.
     * Affiche un message à l'utilisateur pour confirmer l'exportation.
     * @param event L'événement d'action déclenché par l'utilisateur
     */
    @FXML
    public void exportToCSV(ActionEvent event) {

        FileAccess anneeFileAccess = new FileAccess();
        
        // Retrieve the data from the TableView
        ObservableList<Annee> anneeList = tableAnnee.getItems();
        anneeFileAccess.annees.addAll(anneeList);
        
        // Specify the file name and call the writeToCSV method to export the data
        String fileName = "export_annees.csv";
        anneeFileAccess.writeToCSVAnnee(fileName);
        
        // Provide feedback to the user
        actionTarget.setText("Donn\u00e9es export\u00e9es dans " + fileName);

    }

    /**
     * Filtrage des données affichées dans la TableView en fonction du texte saisi dans le champ de recherche.
     * Actualise la TableView pour afficher uniquement les années qui correspondent au texte de recherche.
     * Si aucun texte n'est saisi, réaffiche toutes les années.
     * @param searchText Le texte saisi dans le champ de recherche
     */
    private void filterTableData(String searchText) {
        ObservableList<Annee> filteredList = FXCollections.observableArrayList();
        List<Annee> anneeList = anneeDAO.findAll();
        
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(anneeList);
        } else {
            for (Annee annee : anneeList) {
                if (String.valueOf(annee.getAnnee()).contains(searchText) || 
                    String.valueOf(annee.getTauxInflation()).contains(searchText)) {
                    filteredList.add(annee);
                }
            }
            tableAnnee.setItems(filteredList);
        }
    }

    
}