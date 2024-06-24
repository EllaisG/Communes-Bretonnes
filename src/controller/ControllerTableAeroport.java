package controller;

import model.data.*;
import model.fileAccess.FileAccess;
import view.AppCommunes;
import model.dao.DepartementDAO;
import model.dao.AeroportDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Contrôleur pour la vue TableViewAeroport.fxml, gère l'affichage et la manipulation des données des aéroports.
 */
public class ControllerTableAeroport {

    /**TableColumn qui affiche le nom des aéroports dans la TableView */
    @FXML
    private TableColumn<Aeroport, String> nomTableColumn;

    /**TableView qui affiche la liste des aéroports */
    @FXML
    private TableView<Aeroport> tableAeroport;

    /**TableColumn qui affiche l'adresse des aéroports dans la TableView */
    @FXML
    private TableColumn<Aeroport, String> adresseTableColumn;

    /**TableColumn qui affiche l'identifiant du département des aéroports dans la TableView */
    @FXML
    private TableColumn<Aeroport, Integer> idDepTableColumn;

    /**GridPane qui contient les boutons d'action pour la gestion des aéroports */
    @FXML
    private GridPane gridAction;

    /**Label pour afficher le nom de l'aéroport sélectionné pour modification */
    @FXML
    private Label nomLabel;

    /**Label pour afficher l'adresse de l'aéroport sélectionné pour modification */
    @FXML
    private Label adresseLabel;

    /**Label pour afficher le département de l'aéroport sélectionné pour modification */
    @FXML
    private Label departementLabel;

    /**Champ de texte pour entrer le nom d'un nouvel aéroport à ajouter */
    @FXML
    private TextField nomAjout;

    /**Champ de texte pour entrer l'adresse d'un nouvel aéroport à ajouter */
    @FXML
    private TextField adresseAjout;

    /**Champ de texte pour entrer l'identifiant du département d'un nouvel aéroport à ajouter */
    @FXML
    private TextField depAjout;

    /**Bouton pour ajouter un nouvel aéroport */
    @FXML
    private Button ajouter;

    /**Bouton pour supprimer l'aéroport sélectionné dans la TableView */
    @FXML
    private Button supprimer;

    /**Bouton pour mettre à jour les informations de l'aéroport sélectionné dans la TableView */
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

    /**Bouton pour ajouter un nouvel aéroport après avoir modifié l'affichage */
    @FXML
    private Button ajouter1;

    /**Bouton pour mettre à jour les informations de l'aéroport après avoir modifié l'affichage */
    @FXML
    private Button update1;

    /**Bouton pour exporter les données des aéroports vers un fichier CSV */
    @FXML
    private Button export;

    /**Champ de texte pour saisir le texte de recherche pour filtrer les aéroports affichés */
    @FXML
    private TextField searchBar;

    /**DAO pour interagir avec la base de données et gérer les objets Aeroport */
    private AeroportDAO aeroportDAO;

    /**
     * Initialise le contrôleur après que son contenu ait été complètement chargé.
     * Initialise la liste des aéroports dans la TableView et configure les listeners.
     */
    @FXML
    public void initialize() {
        aeroportDAO = new AeroportDAO();
        loadTableData();

        // Add a listener to the table selection model
        tableAeroport.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
     * Charge les données des aéroports depuis la base de données et les affiche dans la TableView.
     */
    private void loadTableData() {
        List<Aeroport> aeroportList = aeroportDAO.findAll();

        ObservableList<Aeroport> aeroportObservableList = FXCollections.observableArrayList(aeroportList);

        nomTableColumn.setCellValueFactory(new PropertyValueFactory<>("nomAeroport"));
        adresseTableColumn.setCellValueFactory(new PropertyValueFactory<>("adresseAeroport"));
        idDepTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Aeroport, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Aeroport, Integer> param) {
                Departement departement = param.getValue().getDepartement();
                return new SimpleObjectProperty<>(departement != null ? departement.getIdDep() : null);
            }
        });
        tableAeroport.setItems(aeroportObservableList);
    }

    /**
     * Gère l'action de suppression d'un aéroport sélectionné dans la TableView.
     * Supprime l'aéroport de la base de données et actualise la TableView.
     * @param event L'événement associé à l'action de suppression.
     */
    public void handleDeleteAction(ActionEvent event) {
        Aeroport selectedAeroport = tableAeroport.getSelectionModel().getSelectedItem();
        if (selectedAeroport != null) {
            aeroportDAO.delete(selectedAeroport);
            loadTableData();
        }
    }

    /**
     * Gère l'action de mise à jour des informations d'un aéroport sélectionné dans la TableView.
     * Met à jour les informations de l'aéroport dans la base de données et actualise la TableView.
     * @param event L'événement associé à l'action de mise à jour.
     */
    public void handleUpdateAction(ActionEvent event) {
        Aeroport aeroport = tableAeroport.getSelectionModel().getSelectedItem();
        try {
            aeroport.setNomAeroport(nomAjout.getText());
            aeroport.setAdresseAeroport(adresseAjout.getText());
            int idDep = Integer.parseInt(depAjout.getText());
            DepartementDAO departementDAO = new DepartementDAO();
            aeroport.setDepartement(departementDAO.findById(idDep));
            aeroportDAO.update(aeroport);
        } catch (RuntimeException e) {
            actionTarget.setText("Param\u00e8tres invalides");
        }
        actionTarget.setText("");
        emptyTextField();
        loadTableData();
    }

    /**
     * Gère l'action de modification de l'interface utilisateur pour l'ajout ou la mise à jour d'un aéroport.
     * Affiche ou cache les composants nécessaires à l'ajout ou à la mise à jour d'un aéroport.
     * @param event L'événement associé à l'action de modification.
     */
    public void handleModifyAction(ActionEvent event) {
        gridAction.setId("modifEnCours");
        supprimer.setVisible(false);
        ajouter.setVisible(false);
        update.setVisible(false);
        retourModif.setVisible(true);
        nomLabel.setVisible(true);
        nomAjout.setVisible(true);
        adresseLabel.setVisible(true);
        adresseAjout.setVisible(true);
        departementLabel.setVisible(true);
        depAjout.setVisible(true);
        if ((Button) event.getSource() == ajouter) {
            ajouter1.setVisible(true);
        } else if ((Button) event.getSource() == update) {
            Aeroport selectedAeroport = tableAeroport.getSelectionModel().getSelectedItem();
            if (selectedAeroport != null) {
                nomAjout.setText(selectedAeroport.getNomAeroport());
            }
            update1.setVisible(true);
        }

    }

    /**
     * Gère l'action d'ajout d'un nouvel aéroport à la base de données.
     * Ajoute un nouvel aéroport avec les informations saisies et actualise la TableView.
     * @param event L'événement associé à l'action d'ajout.
     */
    public void handleAddAction(ActionEvent event) {
        try {
            String nomAeroport = nomAjout.getText();
            
            int idDepartement = Integer.parseInt(depAjout.getText());
            DepartementDAO departementDAO = new DepartementDAO();
            Departement departement = departementDAO.findById(idDepartement);
            Aeroport aeroport = new Aeroport(nomAeroport, nomAeroport, departement);
            int checkException = aeroportDAO.create(aeroport);
            if (checkException == -1) {
                actionTarget.setText("Ce code est d\u00e9j\u00e0 utilis\u00e9e par un autre aeroport");
            }
        } catch (RuntimeException e) {
            actionTarget.setText("Param\u00e8tres invalides");
        }
        actionTarget.setText("");
        emptyTextField();
        loadTableData();
    }

    
    /**
     * Réinitialise l'interface utilisateur pour annuler l'ajout ou la mise à jour d'un aéroport.
     * Cache les composants utilisés pour l'ajout ou la mise à jour et réaffiche ceux de base.
     * @param event L'événement associé à l'action de retour à la modification.
     */
    public void retourModif(ActionEvent event) {
        emptyTextField();

        gridAction.setId("actionGrid");
        
        supprimer.setVisible(true);
        ajouter.setVisible(true);
        update.setVisible(true);
        retourModif.setVisible(false);
        nomLabel.setVisible(false);
        nomAjout.setVisible(false);
        adresseLabel.setVisible(false);
        adresseAjout.setVisible(false);
        departementLabel.setVisible(false);
        depAjout.setVisible(false);
        ajouter1.setVisible(false);
        update1.setVisible(false);
        

    }

    /**
     * Efface le texte des champs de saisie d'ajout ou de mise à jour.
     */
    public void emptyTextField() {
        nomAjout.setText("");
        adresseAjout.setText("");
        depAjout.setText("");
    }

    /**
     * Gère l'action d'agrandir ou de réduire la fenêtre principale de l'application.
     * Change la scène pour agrandir ou réduire la vue actuelle en fonction de son état.
     * @param event L'événement associé à l'action d'agrandissement ou de retour.
     */
    public void agrandirEtRetour(ActionEvent event) {

        if (AppCommunes.getRoot().getId().equals("main")) {

            Scene scene = null;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tableViewAeroport.fxml"));
                AppCommunes.setRoot(root);
                scene = new Scene(AppCommunes.getRoot(), 900, 600);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setFullScreen(true);
        }

        else if (AppCommunes.getRoot().getId().equals("aeroportTable")) {
            Scene scene = null;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
                AppCommunes.setRoot(root);
                scene = new Scene(AppCommunes.getRoot(), 900, 600);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }

    }

    /**
     * Exporte les données actuellement affichées dans la TableView des aéroports vers un fichier CSV.
     * Crée un fichier CSV avec les données des aéroports et affiche un message de confirmation.
     * @param event L'événement associé à l'action d'exportation.
     */
    @FXML
    void exportToCSV(ActionEvent event) {
        // Create an instance of GareFileAccess
        FileAccess aeroportFileAccess = new FileAccess();
        
        // Retrieve the data from the TableView
        ObservableList<Aeroport> aeroportList = tableAeroport.getItems();
        
        // Add the retrieved data to the GareFileAccess instance
        aeroportFileAccess.aeroports.addAll(aeroportList);
        
        // Specify the file name and call the writeToCSV method to export the data
        String fileName = "export_aeroports.csv";
        aeroportFileAccess.writeToCSVAeroport(fileName);
        
        // Provide feedback to the user
        actionTarget.setText("Donn\u00e9es export\u00e9es dans " + fileName);

    }

    /**
     * Filtre les données affichées dans la TableView en fonction du texte saisi dans la barre de recherche.
     * Affiche seulement les aéroports dont le nom, l'adresse ou l'identifiant de département correspond à la recherche.
     * @param searchText Le texte saisi dans la barre de recherche pour filtrer les données.
     */
    private void filterTableData(String searchText) {
        ObservableList<Aeroport> filteredList = FXCollections.observableArrayList();
        List<Aeroport> aeroportList = aeroportDAO.findAll();
    
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(aeroportList);
        } else {
            for (Aeroport aeroport : aeroportList) {
                if (aeroport.getNomAeroport().toLowerCase().contains(searchText.toLowerCase()) ||
                    aeroport.getAdresseAeroport().toLowerCase().contains(searchText.toLowerCase()) ||
                    (aeroport.getDepartement() != null && Integer.toString(aeroport.getDepartement().getIdDep()).contains(searchText))) {
                    filteredList.add(aeroport);
                }
            }
        }
    
        tableAeroport.setItems(filteredList);
    }
}
