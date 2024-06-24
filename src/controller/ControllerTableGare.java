package controller;

import model.data.*;
import model.fileAccess.FileAccess;
import view.AppCommunes;
import model.dao.CommuneDAO;
import model.dao.GareDAO;
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
import javafx.scene.layout.HBox;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Contrôleur pour la vue TableViewGare.fxml, gère l'affichage et la manipulation des données des gares.
 */
public class ControllerTableGare {

    /**TableColumn qui affiche si la gare transporte des frets dans la TableView */
    @FXML
    private TableColumn<Gare, Boolean> fretTableColumn;

    /**TableColumn qui affiche si la gare transporte des voyageurs dans la TableView */
    @FXML
    private TableColumn<Gare, Boolean> voyageurTableColumn;

    /**TableColumn qui affiche le nom de la gare dans la TableView */
    @FXML
    private TableColumn<Gare, String> nomGareTableColumn;

    /**TableColumn qui affiche le nom de la commune de la gare dans la TableView */
    @FXML
    private TableColumn<Gare, String> communeTableColumn;

    /**TableView qui affiche la liste des gares */
    @FXML
    private TableView<Gare> tableGare;

    /**TableColumn qui affiche le code de la gare dans la TableView */
    @FXML
    private TableColumn<Gare, Integer> idGareTableColumn;

    /**GridPane qui contient les boutons d'action pour la gestion des gares */
    @FXML
    private GridPane gridAction;

    /**Label pour afficher l'identifiant de la gare sélectionnée pour modification */
    @FXML
    private Label codeGareLabel;
    
    /**Label pour afficher le nom de la gare sélectionnée pour modification */
    @FXML
    private Label nomLabel;

    /**Label pour afficher l'identifiant de la commune de la gare sélectionnée pour modification */
    @FXML
    private Label idCommuneLabel;

    /**Champ de texte pour entrer le code de la gare à ajouter */
    @FXML
    private TextField codeAjout;

    /**Champ de texte pour entrer le nom de la gare à ajouter */
    @FXML
    private TextField nomAjout;

    /**Button à sélectionner si la gare transporte des frets */
    @FXML
    private RadioButton voyageurAjout;

    /**Button à sélectionner si la gare transporte des voyageurs */
    @FXML
    private RadioButton fretAjout;

    /**Champ de texte pour entrer l'identifiant de la commune de la gare à ajouter */
    @FXML
    private TextField communeAjout;

    /**Bouton pour ajouter une nouvelle gare */
    @FXML
    private Button ajouter;

    /**Bouton pour supprimer la gare sélectionnée dans la TableView */
    @FXML
    private Button supprimer;

    /**Bouton pour mettre à jour les informations de la gare sélectionnée dans la TableView */
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

    /**Bouton pour ajouter une nouvelle gare après avoir modifié l'affichage */
    @FXML
    private Button ajouter1;

    /**Bouton pour mettre à jour les informations de la gare après avoir modifié l'affichage */
    @FXML
    private Button update1;

    /**Bouton pour exporter les données des gares vers un fichier CSV */
    @FXML
    private Button export;

    /**Champ de texte pour saisir le texte de recherche pour filtrer les gares affichées */
    @FXML
    private TextField searchBar;

    /**DAO pour interagir avec la base de données et gérer les objets Gare */
    private GareDAO gareDAO;


    /**
     * Initialise le contrôleur après le chargement du fichier FXML.
     * Initialise le DAO des gares, charge les données dans la TableView et configure les écouteurs d'événements.
     */
    @FXML
    public void initialize(){
        gareDAO = new GareDAO();
        loadTableData();

        // Add a listener to the table selection model
        tableGare.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
     * Charge les données des gares depuis la base de données et les affiche dans la TableView.
     */
    private void loadTableData() {
        List<Gare> gareList = gareDAO.findAll();

        ObservableList<Gare> gareObservableList = FXCollections.observableArrayList(gareList);
     
        
        idGareTableColumn.setCellValueFactory(new PropertyValueFactory<Gare, Integer>("codeGare"));
        nomGareTableColumn.setCellValueFactory(new PropertyValueFactory<>("nomGare"));
        fretTableColumn.setCellValueFactory(new PropertyValueFactory<>("estFret"));
        voyageurTableColumn.setCellValueFactory(new PropertyValueFactory<>("estVoyageur"));
        communeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Gare, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Gare, String> param) {
                Commune commune = param.getValue().getCommune();
                return new SimpleStringProperty(commune != null ? commune.getNomCommune() : "N/A");
            }
            
        });

        tableGare.setItems(gareObservableList);
    }

    /**
     * Gère l'action de suppression d'une gare sélectionnée dans la TableView.
     * Supprime la gare sélectionnée de la base de données et recharge les données dans la TableView.
     * @param event L'événement d'action déclenché
     */
    public void handleDeleteAction(ActionEvent event) {
        Gare selectedGare = tableGare.getSelectionModel().getSelectedItem();
        if (selectedGare != null) {
            gareDAO.delete(selectedGare);
            loadTableData();
        }
    }

    /**
     * Gère l'action de mise à jour des informations d'une gare sélectionnée dans la TableView.
     * Met à jour les informations de la gare avec les valeurs saisies dans les champs de texte.
     * Affiche un message d'erreur si les paramètres saisis ne sont pas valides.
     * Recharge les données dans la TableView après la mise à jour.
     * @param event L'événement d'action déclenché
     */

    public void handleUpdateAction(ActionEvent event) {
        Gare gare = tableGare.getSelectionModel().getSelectedItem();
        try {
            gare.setCodeGare(Integer.parseInt(codeAjout.getText()));
            gare.setNomGare(nomAjout.getText());
            gare.setEstFret(fretAjout.isSelected());
            gare.setEstVoyageurs(voyageurAjout.isSelected());
            int idCommune = Integer.parseInt(communeAjout.getText());
            CommuneDAO communeDAO = new CommuneDAO();
            gare.setCommune(communeDAO.findById(idCommune));
            gareDAO.update(gare);
        }
        catch (RuntimeException e){
            actionTarget.setText("Param\u00e8tres invalides");
        }
        actionTarget.setText("");
        emptyTextField();
        loadTableData();
    }

    /**
     * Gère l'action de modification de l'affichage pour l'ajout ou la mise à jour d'une gare.
     * Cache ou affiche les éléments nécessaires pour l'ajout ou la mise à jour de la gare.
     * @param event L'événement d'action déclenché
     */
    public void handleModifyAction (ActionEvent event){
        gridAction.setId("modifEnCours");
        supprimer.setVisible(false);
        ajouter.setVisible(false);
        update.setVisible(false);
        retourModif.setVisible(true);
        codeGareLabel.setVisible(true);
        codeAjout.setVisible(true);
        nomLabel.setVisible(true);
        nomAjout.setVisible(true);
        fretAjout.setVisible(true);
        voyageurAjout.setVisible(true);
        idCommuneLabel.setVisible(true);
        communeAjout.setVisible(true);
        if ((Button) event.getSource() == ajouter){
            ajouter1.setVisible(true);
        }
        else if ((Button) event.getSource() == update){
            Gare selectedGare = tableGare.getSelectionModel().getSelectedItem();
                if (selectedGare != null) {
                    codeAjout.setText(selectedGare.getCodeGare()+"");
                }
            update1.setVisible(true);
        }
    }

    /**
     * Gère l'action d'ajout d'une nouvelle gare.
     * Récupère les valeurs saisies dans les champs de texte et ajoute une nouvelle gare dans la base de données.
     * Affiche un message d'erreur si les paramètres saisis ne sont pas valides ou si le code de la gare est déjà utilisé.
     * Recharge les données dans la TableView après l'ajout.
     * @param event L'événement d'action déclenché
     */
    public void handleAddAction(ActionEvent event) {
        try {
            int codeGare = Integer.parseInt(codeAjout.getText());
            String nomGare = nomAjout.getText();
            boolean estFret = fretAjout.isSelected();
            boolean estVoyageur = voyageurAjout.isSelected();
            int idCommune = Integer.parseInt(communeAjout.getText());
            CommuneDAO communeDAO = new CommuneDAO();
            Commune commune = communeDAO.findById(idCommune);
            Gare gare = new Gare(codeGare, nomGare, estFret, estVoyageur, commune);
            int checkException = gareDAO.create(gare);
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
     * Vide les champs de texte utilisés pour l'ajout ou la mise à jour d'une gare.
     */
    public void emptyTextField(){
        codeAjout.setText("");
        nomAjout.setText("");
        fretAjout.setSelected(false);
        voyageurAjout.setSelected(false);
        communeAjout.setText("");
    }

    /**
     * Rétablit l'affichage par défaut après avoir modifié l'affichage pour l'ajout ou la mise à jour d'une gare.
     * Cache les éléments utilisés pour l'ajout ou la mise à jour et réaffiche les éléments principaux.
     * @param event L'événement d'action déclenché
     */
    public void retourModif (ActionEvent event){
        emptyTextField();
        gridAction.setId("actionGrid");

        supprimer.setVisible(true);
        ajouter.setVisible(true);
        update.setVisible(true);
        retourModif.setVisible(false);
        codeGareLabel.setVisible(false);
        codeAjout.setVisible(false);
        nomLabel.setVisible(false);
        nomAjout.setVisible(false);
        fretAjout.setVisible(false);
        voyageurAjout.setVisible(false);
        idCommuneLabel.setVisible(false);
        communeAjout.setVisible(false);
        ajouter1.setVisible(false);
        update1.setVisible(false);
    
    }

    /**
     * Gère l'action d'agrandir ou de réduire la vue principale de l'application.
     * Change la vue entre agrandie et normale en fonction de l'état actuel.
     * @param event L'événement d'action déclenché
     */
    public void agrandirEtRetour (ActionEvent event ){

        if(AppCommunes.getRoot().getId().equals("main")){
            
            Scene scene=null;
            try {
                Parent root = FXMLLoader.load(getClass ().getResource("/tableViewGare.fxml"));
                AppCommunes.setRoot(root);
                scene = new Scene(AppCommunes.getRoot() , 900, 600);
            } catch (IOException e) {
                e.printStackTrace ();
            }
            Stage stage= (Stage) ((Node)event.getSource()).getScene ().getWindow ();
            stage.setScene(scene);
            stage.setFullScreen(true);
        }

        else if(AppCommunes.getRoot().getId().equals("gareTable")){
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
     * Exporte les données des gares affichées dans la TableView vers un fichier CSV.
     * Utilise la classe FileAccess pour gérer l'exportation des données.
     * Affiche un message de confirmation une fois l'exportation terminée.
     * @param event L'événement d'action déclenché
     */
    @FXML
    void exportToCSV(ActionEvent event) {
        // Create an instance of FileAccess
        FileAccess gareFileAccess = new FileAccess();
        
        // Retrieve the data from the TableView
        ObservableList<Gare> gareList = tableGare.getItems();
        
        // Add the retrieved data to the FileAccess instance
        gareFileAccess.gares.addAll(gareList);
        
        // Specify the file name and call the writeToCSV method to export the data
        String fileName = "export_gares.csv";
        gareFileAccess.writeToCSVGare(fileName);
        
        // Provide feedback to the user
        actionTarget.setText("Donn\u00e9es export\u00e9es dans " + fileName);

    }

    /**
     * Filtre les données affichées dans la TableView en fonction du texte saisi dans la barre de recherche.
     * Affiche uniquement les gares dont le code, le nom ou le nom de la commune correspondent au texte de recherche.
     * @param searchText Le texte saisi dans la barre de recherche
     */
    private void filterTableData(String searchText) {
        ObservableList<Gare> filteredList = FXCollections.observableArrayList();
        List<Gare> gareList = gareDAO.findAll();
    
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(gareList);
        } else {
            String lowerCaseSearchText = searchText.toLowerCase();
            for (Gare gare : gareList) {
                if (String.valueOf(gare.getCodeGare()).contains(searchText) || 
                    gare.getNomGare().toLowerCase().contains(lowerCaseSearchText) ||
                    (gare.getCommune() != null && gare.getCommune().getNomCommune().toLowerCase().contains(lowerCaseSearchText))) {
                    filteredList.add(gare);
                }
            }
        }
        tableGare.setItems(filteredList);
    }
}
