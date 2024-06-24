package controller;

import model.data.*;
import model.fileAccess.FileAccess;
import view.AppCommunes;
import model.dao.DepartementDAO;
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
 * Contrôleur pour la vue TableViewDepartement.fxml, gère l'affichage et la manipulation des données des départements.
 */
public class ControllerTableDepartement {

    /**TableColumn qui affiche l'identifiant du département dans la TableView */
    @FXML
    private TableColumn<Departement, Integer> idTableColumn;

    /**TableColumn qui affiche le nom du département dans la TableView */
    @FXML
    private TableColumn<Departement, String> nomTableColumn;

    /**TableColumn qui affiche l'investissement culturel de 2019 du département dans la TableView */
    @FXML
    private TableColumn<Departement, Double> investTableColumn;

    //@FXML
    //private TableColumn<Departement, ?> communeTableColumn;

    /**TableView qui affiche la liste des départements */
    @FXML
    private TableView<Departement> tableDepartement;

    /**GridPane qui contient les boutons d'action pour la gestion des départements */
    @FXML
    private GridPane gridAction;

    /**Label pour afficher l'identifiant du département sélectionné pour modification */
    @FXML
    private Label idLabel;

    /**Label pour afficher le nom du département sélectionné pour modification */
    @FXML
    private Label nomLabel;

    /**Label pour afficher l'investissement culturel du département sélectionné pour modification */
    @FXML
    private Label investLabel;

    /**Champ de texte pour entrer l'identifiant du département à ajouter */
    @FXML
    private TextField idAjout;

    /**Champ de texte pour entrer le nom du département à ajouter */
    @FXML
    private TextField nomAjout;

    /**Champ de texte pour entrer l'investissement culturel du département à ajouter */
    @FXML
    private TextField investAjout;

    /**Bouton pour ajouter un nouveau département */
    @FXML
    private Button ajouter;

    /**Bouton pour retourner à l'écran précédent */
    @FXML
    private Button retour;

    /**Bouton pour ajouter un nouveau département après avoir modifié l'affichage */
    @FXML
    private Button ajouter1;

    /**Bouton pour mettre à jour les informations du département sélectionné dans la TableView */
    @FXML
    private Button update;

    /**Texte pour afficher des messages d'action ou d'erreur */
    @FXML
    private Text actionTarget;

    /**Bouton pour mettre à jour les informations du département après avoir modifié l'affichage */
    @FXML
    private Button update1;

    /**Bouton pour supprimer le département sélectionné dans la TableView */
    @FXML
    private Button supprimer;

    /**Bouton pour annuler les modifications et retourner à l'affichage précédent */
    @FXML
    private Button retourModif;

    /**Pane racine de l'affichage des départements */
    @FXML
    private AnchorPane departementTable;

    /**Bouton pour exporter les données des départements vers un fichier CSV */
    @FXML
    private Button export;

    /**Champ de texte pour saisir le texte de recherche pour filtrer les départements affichés */
    @FXML
    private TextField searchBar;

    /**DAO pour interagir avec la base de données et gérer les objets Departement */
    private DepartementDAO departementDAO;

    /**
     * Initialise le contrôleur. Charge les données des départements dans la TableView,
     * et configure les écouteurs pour la sélection et la recherche.
     */
    @FXML
    public void initialize(){
        departementDAO = new DepartementDAO();
        loadTableData();

        // Add a listener to the table selection model
        tableDepartement.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
     * Charge les données des départements depuis la base de données et les affiche dans la TableView.
     */
    private void loadTableData() {
        List<Departement> departementList = departementDAO.findAll();
        ObservableList<Departement> departementObservableList = FXCollections.observableArrayList(departementList);

        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("idDep"));
        nomTableColumn.setCellValueFactory(new PropertyValueFactory<>("nomDep"));
        investTableColumn.setCellValueFactory(new PropertyValueFactory<>("invesCulturel2019"));
        //communeTableColumn.setCellValueFactory();
        tableDepartement.setItems(departementObservableList);
    }

    /**
     * Gère l'action de suppression d'un département sélectionné dans la TableView.
     * Supprime le département sélectionné de la base de données et rafraîchit la TableView.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    void handleDeleteAction(ActionEvent event) {
        Departement selectedDepartement = tableDepartement.getSelectionModel().getSelectedItem();
        if (selectedDepartement != null) {
            departementDAO.delete(selectedDepartement);
            loadTableData();
        }
    }

    /**
     * Gère l'action de mise à jour des informations d'un département sélectionné dans la TableView.
     * Met à jour les informations du département avec les valeurs saisies par l'utilisateur,
     * puis met à jour la base de données et rafraîchit la TableView.
     * Affiche un message d'erreur si les paramètres saisis sont invalides.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    void handleUpdateAction(ActionEvent event) {
        Departement departement = tableDepartement.getSelectionModel().getSelectedItem();
        try {
            departement.setIdDep(Integer.parseInt(idAjout.getText()));
            departement.setNomDep(nomAjout.getText());
            departement.setInvesCulturel2019(Double.parseDouble(investAjout.getText()));
            // liste de communes à ajouter
            departementDAO.update(departement);
        }
        catch (RuntimeException e){
            actionTarget.setText("Param\u00e8tres invalides");
        }
        actionTarget.setText("");
        emptyTextField();
        loadTableData();
    }

    /**
     * Gère l'action de modification de l'affichage pour ajouter ou mettre à jour un département.
     * Cache les boutons actuels et affiche les champs nécessaires pour l'ajout ou la modification.
     * @param event L'événement d'action déclenché par l'utilisateur (ajouter ou modifier).
     */
    @FXML
    void handleModifyAction(ActionEvent event) {
        gridAction.setId("modifEnCours");
        supprimer.setVisible(false);
        ajouter.setVisible(false);
        update.setVisible(false);
        retourModif.setVisible(true);
        idLabel.setVisible(true);
        idAjout.setVisible(true);
        nomLabel.setVisible(true);
        nomAjout.setVisible(true);
        investLabel.setVisible(true);
        investAjout.setVisible(true);
        if ((Button) event.getSource() == ajouter){
            ajouter1.setVisible(true);
        }
        else if ((Button) event.getSource() == update){
            Departement selectedDepartement = tableDepartement.getSelectionModel().getSelectedItem();
                if (selectedDepartement != null) {
                    idAjout.setText(selectedDepartement.getIdDep()+"");
                }
            update1.setVisible(true);
        }
    }

    /**
     * Gère l'action d'ajout d'un nouveau département avec les valeurs saisies par l'utilisateur.
     * Crée un nouvel objet Département et l'ajoute à la base de données.
     * Affiche un message d'erreur si les paramètres saisis sont invalides.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    void handleAddAction(ActionEvent event) {
        try {
            int idDep = Integer.parseInt(idAjout.getText());
            String nomDep = nomAjout.getText();
            double invesCulturel2019 = Double.parseDouble(investAjout.getText());
            // liste de communes à ajouter
            Departement departement = new Departement(idDep, nomDep, invesCulturel2019, null);
            int checkException = departementDAO.create(departement);
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
     * Réinitialise les champs de texte utilisés pour l'ajout ou la modification d'un département.
     */
    public void emptyTextField() {
        idAjout.setText("");
        nomAjout.setText("");
        investAjout.setText("");
    }

    /**
     * Rétablit l'affichage initial après une opération de modification (ajout ou mise à jour).
     * Cache les champs de saisie et réaffiche les boutons d'actions.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    void retourModif(ActionEvent event) {
        emptyTextField();

        gridAction.setId("actionGrid");
        
        supprimer.setVisible(true);
        ajouter.setVisible(true);
        update.setVisible(true);
        retourModif.setVisible(false);
        idLabel.setVisible(false);
        idAjout.setVisible(false);
        nomLabel.setVisible(false);
        nomAjout.setVisible(false);
        investLabel.setVisible(false);
        investAjout.setVisible(false);
        ajouter1.setVisible(false);
        update1.setVisible(false);
    }
    
    /**
     * Gère l'action de redimensionner la fenêtre et changer de vue en fonction de l'état actuel de l'application.
     * Si la vue actuelle est celle des communes, agrandit la fenêtre vers le plein écran.
     * Si la vue actuelle est celle des départements, revient à l'écran principal.
     * @param event L'événement d'action déclenché par l'utilisateur (Agrandir/Retour).
     */
    @FXML
    void agrandirEtRetour(ActionEvent event) {

        if(AppCommunes.getRoot().getId().equals("main")){
            
            Scene scene=null;
            try {
                Parent root = FXMLLoader.load(getClass ().getResource("/tableViewDepartement.fxml"));
                AppCommunes.setRoot(root);
                scene = new Scene(AppCommunes.getRoot() , 900, 600);
            } catch (IOException e) {
                e.printStackTrace ();
            }
            Stage stage= (Stage) ((Node)event.getSource()).getScene ().getWindow ();
            stage.setScene(scene);
            stage.setFullScreen(true);
        }

        else if(AppCommunes.getRoot().getId().equals("departementTable")){
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
     * Exporte les données des départements affichés dans la TableView vers un fichier CSV.
     * Crée une instance de FileAccess, récupère les données de la TableView, les ajoute à l'instance,
     * spécifie le nom du fichier CSV, puis appelle la méthode writeToCSV pour exporter les données.
     * Affiche un message indiquant que les données ont été exportées avec succès.
     * @param event L'événement d'action déclenché par l'utilisateur (Exporter).
     */
    @FXML
    void exportToCSV(ActionEvent event) {
        // Create an instance of GareFileAccess
        FileAccess departementFileAccess = new FileAccess();
        
        // Retrieve the data from the TableView
        ObservableList<Departement> departementList = tableDepartement.getItems();
        
        // Add the retrieved data to the GareFileAccess instance
        departementFileAccess.departements.addAll(departementList);
        
        // Specify the file name and call the writeToCSV method to export the data
        String fileName = "export_departements.csv";
        departementFileAccess.writeToCSVDepartement(fileName);
        
        // Provide feedback to the user
        actionTarget.setText("Donn\u00e9es export\u00e9es dans " + fileName);

    }

    /**
     * Filtrer les données affichées dans la TableView en fonction du texte de recherche.
     * Crée une liste observable filtrée pour contenir les départements correspondant au critère de recherche.
     * Met à jour les données affichées dans la TableView en fonction du résultat filtré.
     * @param searchText Le texte de recherche saisi par l'utilisateur.
     */
    private void filterTableData(String searchText) {
        ObservableList<Departement> filteredList = FXCollections.observableArrayList();
        List<Departement> departementList = departementDAO.findAll();
    
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(departementList);
        } else {
            for (Departement departement : departementList) {
                if (String.valueOf(departement.getIdDep()).contains(searchText) || 
                    departement.getNomDep().toLowerCase().contains(searchText.toLowerCase()) || 
                    String.valueOf(departement.getInvesCulturel2019()).contains(searchText)) {
                    filteredList.add(departement);
                }
            }
        }
        tableDepartement.setItems(filteredList);
    }
}