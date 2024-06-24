package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.dao.AnneeDAO;
import model.dao.CommuneDAO;
import model.dao.DonneesAnnuellesDAO;
import model.fileAccess.FileAccess;
import view.AppCommunes;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import model.data.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import model.dao.DepartementDAO;


import javafx.scene.control.TableColumn.CellDataFeatures;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Contrôleur pour la vue TableViewDonneeAnnuelle.fxml, gère l'affichage et la manipulation des données des données annuelles.
 */
public class ControllerTableDonneeAnnuelle {

    /**Bouton pour ajouter une nouvelle donnée annuelle */
    @FXML
    private Button ajouter;

    /**Bouton pour retourner à l'écran précédent */
    @FXML
    private Button retour;

    /**Bouton pour mettre à jour les informations de la donnée annuelle sélectionnée dans la TableView */
    @FXML
    private Button update;

    /**Pane racine de l'affichage des données annuelles */
    @FXML
    private AnchorPane donneeAnnuelleTable;

    /**Bouton pour supprimer la donnée annuelle sélectionnée dans la TableView */
    @FXML
    private Button supprimer;

    /**GridPane qui contient les boutons d'action pour la gestion des départements */
    @FXML
    private GridPane gridAction;

    /**GridPane qui contient les informations pour la gestion des départements */
    @FXML
    private GridPane gridPrincipale;

    /**TableView qui affiche la liste des données annuelles */
    @FXML
    private TableView<DonneesAnnuelles> tableDonneesAnnuelles;

    /**TableColumn qui affiche l'année de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, Integer> anneeTableColumn;

    /**TableColumn qui affiche l'identifiant et le nom de la commune de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, String> communeTableColumn;

    /**TableColumn qui affiche le nombre de maison vendues de la commune de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, Integer> maisonTableColumn;

    /**TableColumn qui affiche le nombre d'appartements vendus de la commune de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, Integer> appartTableColumn;

    /**TableColumn qui affiche le prix moyen de la commune de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, Double> prixMoyTableColumn;

    /**TableColumn qui affiche le prix moyen par m2 de la commune de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, Double> prixMoyM2TableColumn;

    /**TableColumn qui affiche la surface moyenne de la commune de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, Double> surfaceTableColumn;

    /**TableColumn qui affiche la dépense culturelle de la commune de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, String> depCultTableColumn;

    /**TableColumn qui affiche le budget de la commune de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, String> budgetTableColumn;

    /**TableColumn qui affiche la population de la commune de la donnée annuelle dans la TableView */
    @FXML
    private TableColumn<DonneesAnnuelles, String> populationTableColumn;

    /**DAO pour interagir avec la base de données et gérer les objets DonneesAnnuelles */
    DonneesAnnuellesDAO donneesAnnuellesDAO;

    /**Label pour afficher le nombre de maisons sélectionné pour modification */
    @FXML
    private Label maisonLabel;

    /**Champ de texte pour entrer l'identifiant de la commune de la donnée à ajouter */
    @FXML
    private TextField commune;

    /**Champ de texte pour entrer le budget de la commune de la donnée à ajouter */
    @FXML
    private TextField budgetTot;

    /**Bouton pour ajouter une nouvelle donnée après avoir modifié l'affichage */
    @FXML
    private Button ajouter1;

    /**Label pour afficher la dépense culturelle sélectionné pour modification */
    @FXML
    private Label depCultLabel;

    /**Bouton pour mettre à jour les informations de la commune après avoir modifié l'affichage */
    @FXML
    private Button modifier1;

    /**Champ de texte pour entrer l'année de la donnée à ajouter */
    @FXML
    private TextField annee;

    /**Label pour afficher la surface moyenne sélectionnée pour modification */
    @FXML
    private Label surfaceMoyenneLabel;

    /**Label pour afficher le nombre d'appartements vendus sélectionné pour modification */
    @FXML
    private Label appartementLabel;

    /**Champ de texte pour entrer le prix moyen de la donnée à ajouter */
    @FXML
    private TextField prixMoyen;

    /**Champ de texte pour entrer le nombre de maisons vendues de la donnée à ajouter */
    @FXML
    private TextField maison;

    /**Label pour afficher le population sélectionné pour modification */
    @FXML
    private Label populationLabel;

    /**Bouton pour exporter les données des départements vers un fichier CSV */
    @FXML
    private Button export;

    /**ScrollPane qui gère le défilement du contenu*/
    @FXML
    private ScrollPane scrollPane;

    /**Label pour afficher l'identifiant et le nom de la commune sélectionné pour modification */
    @FXML
    private Label communeLabel;

    /**Label pour afficher le prix moyen par m2 sélectionné pour modification */
    @FXML
    private Label prixMoyenM2Label;

    /**Champ de texte pour entrer le nombre d'appartements vendus de la donnée à ajouter */
    @FXML
    private TextField appartement;

    /**Champ de texte pour entrer la surface moyenne de la donnée à ajouter */
    @FXML
    private TextField surfaceMoyenne;

    /**Label pour afficher des messages d'action ou d'erreur */
    @FXML
    private Label actionTarget;

    /**Label pour afficher des messages d'action ou d'erreur */
    @FXML
    private Label actionTarget1;

    /**Label pour afficher le prix moyen de la donnée sélectionné pour modification */
    @FXML
    private Label prixMoyenLabel;

    /**Champ de texte pour entrer la population de la donnée à ajouter */
    @FXML
    private TextField population;

    /**Label pour afficher le budget total de la donnée sélectionné pour modification */
    @FXML
    private Label budgetTotLabel;

    /**Champ de texte pour entrer le prix moyen par m2 de la donnée à ajouter */
    @FXML
    private TextField prixMoyenM2;

    /**Champ de texte pour entrer la dépense culturelle de la donnée à ajouter */
    @FXML
    private TextField depCult;

    /**Champ de texte pour saisir le texte de recherche pour filtrer les départements affichés */
    @FXML
    private TextField searchBar;

    /**Label pour afficher l'année de la donnée sélectionné pour modification */
    @FXML
    private Label anneeLabel;


    /**
     * Initialise le contrôleur après que son élément racine ait été complètement traité.
     * Charge les données de la table et configure les observateurs.
     */
    @FXML
    public void initialize(){
        donneesAnnuellesDAO = new DonneesAnnuellesDAO();
        loadTableData();

        // Add a listener to the table selection model
        tableDonneesAnnuelles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
     * Charge les données de la table des données annuelles depuis la base de données et les affiche dans la TableView.
     */
    private void loadTableData() {
        List<DonneesAnnuelles> donneesAnnuellesList = donneesAnnuellesDAO.findAll();
        ObservableList<DonneesAnnuelles> donneesAnnuellesObservableList = FXCollections.observableArrayList(donneesAnnuellesList);

        // Set up the anneeTableColumn to display the integer year value
        anneeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DonneesAnnuelles, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<DonneesAnnuelles, Integer> param) {
                return new SimpleObjectProperty<>(param.getValue().getAnnee().getAnnee());
            }
        });

        // Set up the communeTableColumn to display the commune name and id
        communeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DonneesAnnuelles, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DonneesAnnuelles, String> param) {
                Commune commune = param.getValue().getCommune();
                String displayText = commune.getIdCommune() + "\n" + commune.getNomCommune();
                return new SimpleStringProperty(displayText);
            }
        });

        maisonTableColumn.setCellValueFactory(new PropertyValueFactory<>("nbMaison"));
        appartTableColumn.setCellValueFactory(new PropertyValueFactory<>("nbAppart"));
        prixMoyTableColumn.setCellValueFactory(new PropertyValueFactory<>("prixMoyen"));
        prixMoyM2TableColumn.setCellValueFactory(new PropertyValueFactory<>("prixM2Moyen"));
        surfaceTableColumn.setCellValueFactory(new PropertyValueFactory<>("surfaceMoy"));

        // Leave cells blank for negative values in depCultTableColumn, budgetTableColumn, and populationTableColumn
        depCultTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DonneesAnnuelles, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DonneesAnnuelles, String> param) {
                double depCulturellesTotales = param.getValue().getDepCulturellesTotales();
                String displayText = depCulturellesTotales >= 0 ? String.valueOf(depCulturellesTotales) : "";
                return new SimpleStringProperty(displayText);
            }
        });

        budgetTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DonneesAnnuelles, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DonneesAnnuelles, String> param) {
                double budgetTotal = param.getValue().getBudgetTotal();
                String displayText = budgetTotal >= 0 ? String.valueOf(budgetTotal) : "";
                return new SimpleStringProperty(displayText);
            }
        });

        populationTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DonneesAnnuelles, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DonneesAnnuelles, String> param) {
                double population = param.getValue().getPopulation();
                String displayText = population >= 0 ? String.valueOf(population) : "";
                return new SimpleStringProperty(displayText);
            }
        });

        tableDonneesAnnuelles.setItems(donneesAnnuellesObservableList);
    }

    /**
     * Gère l'action de suppression d'une donnée annuelle sélectionnée dans la TableView.
     * Supprime la donnée annuelle sélectionnée de la base de données et recharge les données dans la TableView.
     * @param event L'événement d'action déclenché
     */
    public void handleDeleteAction(ActionEvent event) {
        DonneesAnnuelles selectedDonnees = tableDonneesAnnuelles.getSelectionModel().getSelectedItem();
        if (selectedDonnees != null) {
            donneesAnnuellesDAO.delete(selectedDonnees);
            loadTableData();
        }
    }

    /**
     * Gère l'action de mise à jour des informations d'une donnée annuelle sélectionnée dans la TableView.
     * Met à jour les informations de la donnée annuelle dans la base de données avec les nouvelles valeurs saisies.
     * Affiche un message d'erreur si les paramètres saisis ne sont pas valides.
     * Recharge les données dans la TableView après la mise à jour.
     * @param event L'événement d'action déclenché
     */
    @FXML
    public void handleUpdateAction(ActionEvent event) {
        
        DonneesAnnuelles donneesAnnuelles = tableDonneesAnnuelles.getSelectionModel().getSelectedItem();
        try {
            
            int anneeInt = Integer.parseInt(annee.getText());
            AnneeDAO anneeDAO = new AnneeDAO();
            donneesAnnuelles.setAnnee(anneeDAO.findById(anneeInt));

            int idCommune = Integer.parseInt(commune.getText());
            CommuneDAO communeDAO = new CommuneDAO();
            donneesAnnuelles.setCommune(communeDAO.findById(idCommune));

            donneesAnnuelles.setNbMaison(Integer.parseInt(maison.getText()));
            donneesAnnuelles.setNbAppart(Integer.parseInt(appartement.getText()));
            donneesAnnuelles.setPrixMoyen(Double.parseDouble(prixMoyen.getText()));
            donneesAnnuelles.setPrixM2Moyen(Double.parseDouble(prixMoyenM2.getText()));
            donneesAnnuelles.setSurfaceMoy(Double.parseDouble(surfaceMoyenne.getText()));
            donneesAnnuelles.setDepCulturellesTotales(Double.parseDouble(depCult.getText()));
            donneesAnnuelles.setBudgetTotal(Double.parseDouble(budgetTot.getText()));
            donneesAnnuelles.setPopulation(Integer.parseInt(population.getText()));

            donneesAnnuellesDAO.update(donneesAnnuelles);
        }
        catch (RuntimeException e){
            actionTarget.setText("Param\u00e8tres invalides");
        }
        actionTarget.setText("");
        emptyTextField();
        loadTableData();
    }

    /**
     * Gère l'action de modification des détails d'une donnée annuelle sélectionnée dans la TableView.
     * Affiche les détails de la donnée annuelle sélectionnée dans les champs de texte pour modification.
     * Cache le formulaire d'action actuel et affiche le formulaire de modification.
     * @param event L'événement d'action déclenché
     */
    @FXML
    public void handleModifyAction(ActionEvent event) {
        scrollPane.setId("modifEnCours");
        // Hide the current action grid
        gridAction.setVisible(false);

        scrollPane.setVisible(true);

        Button clickedButton = (Button) event.getSource();
        if (clickedButton == ajouter){
            ajouter1.setVisible(true);
        } else {
            DonneesAnnuelles donneesAnnuelles = tableDonneesAnnuelles.getSelectionModel().getSelectedItem();
            modifier1.setVisible(true);
            annee.setText(donneesAnnuelles.getAnnee().getAnnee()+"");
            commune.setText(donneesAnnuelles.getCommune().getIdCommune()+"");
        } 
    }

    /**
     * Gère l'action d'ajout d'une nouvelle donnée annuelle.
     * Ajoute une nouvelle entrée de données annuelles dans la base de données avec les valeurs saisies.
     * Affiche un message d'erreur si les paramètres saisis ne sont pas valides.
     * Recharge les données dans la TableView après l'ajout.
     * @param event L'événement d'action déclenché
     */
    @FXML
    public void handleAddAction(ActionEvent event) {
        try {
            int anneeInt = Integer.parseInt(annee.getText());
            AnneeDAO anneeDAO = new AnneeDAO();
            Annee annee = anneeDAO.findById(anneeInt);

            int idCommune = Integer.parseInt(commune.getText());
            CommuneDAO communeDAO = new CommuneDAO();
            Commune commune = communeDAO.findById(idCommune);

            int nbMaison = Integer.parseInt(maison.getText());
            int nbAppart = Integer.parseInt(appartement.getText());
            double prixMoyenDouble = Double.parseDouble(prixMoyen.getText());
            double prixMoyenM2Double = Double.parseDouble(prixMoyenM2.getText());
            double surfaceMoy = Double.parseDouble(surfaceMoyenne.getText());
            double depCulturellesTotales = Double.parseDouble(depCult.getText());
            double budgetTotal = Double.parseDouble(budgetTot.getText());
            double populationTotale = Double.parseDouble(population.getText());

            DonneesAnnuelles donneesAnnuelles = new DonneesAnnuelles(annee, commune, nbMaison, nbAppart, prixMoyenDouble, prixMoyenM2Double, surfaceMoy, depCulturellesTotales, budgetTotal, populationTotale);
            int checkException = donneesAnnuellesDAO.create(donneesAnnuelles);
            if (checkException == -1){
                actionTarget.setText("Ce code est d\u00e9j\u00e0 utilis\u00e9e par une autre donneesAnnuelles");
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
     * Vide tous les champs de texte de saisie de données.
     * Réinitialise également les zones de texte d'affichage des messages d'action.
     */
    public void emptyTextField(){
        annee.setText("");
        commune.setText("");
        maison.setText("");
        appartement.setText("");
        prixMoyen.setText("");
        prixMoyenM2.setText("");
        surfaceMoyenne.setText("");
        depCult.setText("");
        budgetTot.setText("");
        population.setText("");
        actionTarget1.setText("");
        actionTarget.setText("");
    }

    /**
     * Retourne à l'écran de modification ou d'ajout précédent.
     * Cache le formulaire de modification ou d'ajout et réaffiche le tableau principal.
     * @param event L'événement d'action déclenché
     */
    public void retourModif (ActionEvent event){

        gridAction.setId("actionGrid");
         
        emptyTextField();
        ajouter1.setVisible(false);
        modifier1.setVisible(false);
        scrollPane.setVisible(false);
        gridAction.setVisible(true);
    
    }

    /**
     * Redimensionne la vue en plein écran ou retourne à la vue principale en fonction de l'état actuel.
     * @param event L'événement d'action déclenché
     */
    public void agrandirEtRetour (ActionEvent event ){

        if(AppCommunes.getRoot().getId().equals("main")){
            
            Scene scene=null;
            try {
                Parent root = FXMLLoader.load(getClass ().getResource("/tableViewDonneeAnnuelle.fxml"));
                AppCommunes.setRoot(root);
                scene = new Scene(AppCommunes.getRoot() , 900, 600);
            } catch (IOException e) {
                e.printStackTrace ();
            }
            Stage stage= (Stage) ((Node)event.getSource()).getScene ().getWindow ();
            stage.setScene(scene);
            stage.setFullScreen(true);
        }

        else if(AppCommunes.getRoot().getId().equals("donneeAnnuelleTable")){
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
     * Exporte les données actuellement affichées dans la TableView vers un fichier CSV.
     * Utilise FileAccess pour gérer l'écriture des données dans le fichier CSV.
     * Affiche un message indiquant que les données ont été exportées avec succès.
     * @param event L'événement d'action déclenché
     */
    @FXML
    public void exportToCSV(ActionEvent event) {
        // Create an instance of GareFileAccess
        FileAccess fileAccess = new FileAccess();
        
        // Retrieve the data from the TableView
        ObservableList<DonneesAnnuelles> donneesAnnuellesList = tableDonneesAnnuelles.getItems();
        
        // Add the retrieved data to the GareFileAccess instance
        fileAccess.donneesAnnuelles.addAll(donneesAnnuellesList);
        
        // Specify the file name and call the writeToCSV method to export the data
        String fileName = "export_donneesAnnuelles.csv";
        fileAccess.writeToCSVDonneesAnnuelles(fileName);
        
        // Provide feedback to the user
        actionTarget1.setText("Donn\u00e9es export\u00e9es dans " + fileName);

    }

    /**
     * Filtrage des données affichées dans la TableView en fonction du texte de recherche spécifié.
     * Actualise la TableView pour afficher uniquement les données correspondant au texte de recherche.
     * @param searchText Texte de recherche à appliquer pour filtrer les données
     */
    private void filterTableData(String searchText) {
        ObservableList<DonneesAnnuelles> filteredList = FXCollections.observableArrayList();
        List<DonneesAnnuelles> donneesAnnuellesList = donneesAnnuellesDAO.findAll();
    
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(donneesAnnuellesList);
        } else {
            for (DonneesAnnuelles donneesAnnuelles : donneesAnnuellesList) {
                if (String.valueOf(donneesAnnuelles.getAnnee().getAnnee()).contains(searchText) ||
                    donneesAnnuelles.getCommune().getNomCommune().toLowerCase().contains(searchText.toLowerCase()) ||
                    String.valueOf(donneesAnnuelles.getNbMaison()).contains(searchText) ||
                    String.valueOf(donneesAnnuelles.getNbAppart()).contains(searchText) ||
                    String.valueOf(donneesAnnuelles.getPrixMoyen()).contains(searchText) ||
                    String.valueOf(donneesAnnuelles.getPrixM2Moyen()).contains(searchText) ||
                    String.valueOf(donneesAnnuelles.getSurfaceMoy()).contains(searchText) ||
                    String.valueOf(donneesAnnuelles.getDepCulturellesTotales()).contains(searchText) ||
                    String.valueOf(donneesAnnuelles.getBudgetTotal()).contains(searchText) ||
                    String.valueOf(donneesAnnuelles.getPopulation()).contains(searchText)) {
                    filteredList.add(donneesAnnuelles);
                }
            }
        }
        tableDonneesAnnuelles.setItems(filteredList);
    }
}
