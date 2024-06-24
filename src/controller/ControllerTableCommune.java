package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.dao.*;
import model.data.*;
import model.fileAccess.FileAccess;
import view.AppCommunes;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 * Contrôleur pour la vue TableViewCommune.fxml, gère l'affichage et la manipulation des données des communes.
 */
public class ControllerTableCommune {

    /**Champ de texte pour entrer le nom de la nouvelle commune à ajouter */
    @FXML
    private TextField nomAjout;

    /**Bouton pour ajouter une nouvelle commune */
    @FXML
    private Button ajouter;

    /**Bouton pour retourner à l'écran précédent */
    @FXML
    private Button retour;

    /**Bouton pour ajouter une nouvelle commune après avoir modifié l'affichage */
    @FXML
    private Button ajouter1;

    /**Bouton pour mettre à jour les informations de la commune sélectionnée dans la TableView */
    @FXML
    private Button update;

    /**Texte pour afficher des messages d'action ou d'erreur */
    @FXML
    private Text actionTarget;

    /**Pane racine de l'affichage des communes */
    @FXML
    private AnchorPane communeTable;

    /**Label pour afficher l'identifiant du département de la commune sélectionné pour modification */
    @FXML
    private Label idDepLabel;

    /**Bouton pour mettre à jour les informations de la commune après avoir modifié l'affichage */
    @FXML
    private Button update1;

    /**Label pour afficher l'identifiant de la commune sélectionné pour modification */
    @FXML
    private Label codeCommuneLabel;

    /**Champ de texte pour entrer l'identifiant de la commune à ajouter */
    @FXML
    private TextField idAjout;

    /**Bouton pour supprimer la commune sélectionné dans la TableView */
    @FXML
    private Button supprimer;

    /**GridPane qui contient les boutons d'action pour la gestion des communes */
    @FXML
    private GridPane gridAction;

    /**Champ de texte pour entrer l'identifiant du département de la commune à ajouter */
    @FXML
    private TextField departementAjout;

    /**Label pour afficher le nom de la commune sélectionné pour modification */
    @FXML
    private Label nomLabel;

    /**Bouton pour annuler les modifications et retourner à l'affichage précédent */
    @FXML
    private Button retourModif;

    /**TableView qui affiche la liste des communes */
    @FXML
    private TableView<Commune> tableCommune;

    /**TableColumn qui affiche l'identifiant de la commune dans la TableView */
    @FXML
    private TableColumn<Commune, Integer> idTableColumn;

    /**TableColumn qui affiche le nom de la commune dans la TableView */
    @FXML
    private TableColumn<Commune, String> nomTableColumn;

    /**TableView qui affiche le département de la commune */
    @FXML
    private TableColumn<Commune, String> departementTableColumn;

    /**Bouton pour exporter les données des communes vers un fichier CSV */
    @FXML
    private Button export;

    /**Champ de texte pour saisir le texte de recherche pour filtrer les communes affichées */
    @FXML
    private TextField searchBar;
    
    /**TableView qui affiche les communes voisines de la commune */
    @FXML
    private TableColumn<Commune, String> voisinesTableColumn;

    /**TableView qui affiche les gares de la commune */
    @FXML
    private TableColumn<Commune, String> gareTableColumn;


   /**
     * Représente une grille pour afficher les composants permettant de gérer les communes voisines.
     */
    @FXML
    private GridPane gridVoisines;

    /**
     * Bouton pour supprimer une commune voisine.
     */
    @FXML
    private Button deleteButton;

    /**
     * Étiquette pour afficher le nom de la commune sélectionnée.
     */
    @FXML
    private Label nomCommune;

    /**
     * Étiquette pour afficher la liste des communes voisines.
     */
    @FXML
    private Label listeCommunes;

    /**
     * Champ de texte pour saisir l'ID de la commune voisine à supprimer.
     */
    @FXML
    private TextField deleteVoisine;

    /**
     * Champ de texte pour saisir l'ID de la commune.
     */
    @FXML
    private TextField idCommune;

    /**
     * Bouton pour ajouter une nouvelle commune voisine.
     */
    @FXML
    private Button addButton;

    /**
     * Pane ancre pour la barre avec tous les composants pour les opérations CRUD.
     */
    @FXML
    private AnchorPane actionBar;

    /**
     * Bouton pour retourner à la page précédente.
     */
    @FXML
    private Button retourModif1;

    /**
     * Champ de texte pour saisir l'ID de la nouvelle commune voisine à ajouter.
     */
    @FXML
    private TextField addVoisine;

    /**
     * Bouton pour gérer les communes voisines.
     */
    @FXML
    private Button communeVoisine;


    /**DAO pour interagir avec la base de données et gérer les objets Commune */
    private CommuneDAO communeDAO;


    /**
     * Initialise le contrôleur après que son contenu a été complètement chargé.
     * Initialise le DAO des communes, charge les données dans la TableView,
     * et configure les écouteurs pour la sélection et la recherche.
     */
    @FXML
    public void initialize(){
        communeDAO = new CommuneDAO();
        loadTableData();

        // Add a listener to the table selection model
        tableCommune.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Enable the "Supprimer" and "Metre a jour" button if a row is selected
            supprimer.setDisable(newValue == null);
            update.setDisable(newValue == null);


            communeVoisine.setDisable(newValue == null);
            if (newValue != null){
                idCommune.setText(newValue.getIdCommune()+"");
                nomCommune.setText(newValue.getNomCommune());
                newValue.setCommunesVoisines(communeDAO.findAllVoisines(newValue));
                listeCommunes.setText(newValue.getIdCommunesVoisines()+"");
    
            }
            // Update the row with its voisines
            updateRow();
        });

        // Add a listener to the searchBar text property
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTableData(newValue);
        });
    }

    /**
     * Met à jour les données des communes voisines et des gares pour la commune sélectionnée dans la TableView.
     * Récupère la commune sélectionnée dans la TableView, puis met à jour sa liste de communes voisines
     */
    private void updateRow() {
        Commune selectedCommune = tableCommune.getSelectionModel().getSelectedItem();
        if (selectedCommune != null) {
            selectedCommune.setCommunesVoisines(communeDAO.findAllVoisines(selectedCommune));
            selectedCommune.setListeGare(communeDAO.findAllGares(selectedCommune));
            // Notifiez le TableView pour mettre à jour la vue
            tableCommune.refresh();
        }
    }

    /**
     * Charge les données des communes depuis la base de données et les affiche dans la TableView.
     */
    private void loadTableData() {
        List<Commune> communeList = communeDAO.findAll();
    
        ObservableList<Commune> communeObservableList = FXCollections.observableArrayList(communeList);
    
        idTableColumn.setCellValueFactory(new PropertyValueFactory<Commune, Integer>("idCommune"));
        nomTableColumn.setCellValueFactory(new PropertyValueFactory<>("nomCommune"));
        departementTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Commune, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Commune, String> param) {
                Departement departement = param.getValue().getDepartement();
                return new SimpleStringProperty(departement != null ? departement.getNomDep() : "N/A");
            }
        });
    
        voisinesTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Commune, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Commune, String> param) {
                List<Commune> voisines = param.getValue().getCommunesVoisines();
                if (voisines == null || voisines.isEmpty()) {
                    return new SimpleStringProperty("");
                } else {
                    StringBuilder voisinesNames = new StringBuilder();
                    for (Commune voisine : voisines) {
                        if (voisinesNames.length() > 0) {
                            voisinesNames.append(", ");
                        }
                        voisinesNames.append(voisine.getNomCommune());
                    }
                    return new SimpleStringProperty(voisinesNames.toString());
                }
            }
        });

        gareTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Commune, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Commune, String> param) {
                List<Gare> gares = param.getValue().getListeGares();
                if (gares == null || gares.isEmpty()) {
                    return new SimpleStringProperty("");
                } else {
                    StringBuilder garesNames = new StringBuilder();
                    for (Gare gare : gares) {
                        if (garesNames.length() > 0) {
                            garesNames.append(", ");
                        }
                        garesNames.append(gare.getNomGare());
                    }
                    return new SimpleStringProperty(garesNames.toString());
                }
            }
        });
    
        tableCommune.setItems(communeObservableList);
    }

    /**
     * Gère l'action de suppression d'une commune sélectionnée dans la TableView.
     * Supprime la commune sélectionnée de la base de données et actualise la TableView.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    public void handleDeleteAction(ActionEvent event) {
        Commune selectedCommune = tableCommune.getSelectionModel().getSelectedItem();
        if (selectedCommune != null) {
            communeDAO.delete(selectedCommune);
            loadTableData();
        }
    }

    /**
     * Gère l'action de mise à jour des informations d'une commune sélectionnée dans la TableView.
     * Met à jour l'id, le nom et le département de la commune sélectionnée dans la base de données
     * en fonction des données saisies dans les champs de texte, puis actualise la TableView.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    public void handleUpdateAction(ActionEvent event) {
        
        Commune commune = tableCommune.getSelectionModel().getSelectedItem();
        try {
            commune.setIdCommune(Integer.parseInt(idAjout.getText()));
            commune.setNomCommune(nomAjout.getText());
            
            int idDep= Integer.parseInt(departementAjout.getText());
            DepartementDAO departementDAO = new DepartementDAO();
            commune.setDepartement(departementDAO.findById(idDep));
            communeDAO.update(commune);
        }
        catch (RuntimeException e){
            actionTarget.setText("Param\u00e8tres invalides");
        }
        actionTarget.setText("");
        emptyTextField();
        loadTableData();
    }

    /**
     * Gère l'action de modification d'affichage pour ajouter ou mettre à jour une commune.
     * Modifie la visibilité des éléments de l'interface utilisateur en fonction de l'action souhaitée
     * (ajouter ou mettre à jour une commune).
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    public void handleModifyAction (ActionEvent event){
        gridAction.setId("modifEnCours");
        supprimer.setVisible(false);
        ajouter.setVisible(false);
        communeVoisine.setVisible(false);
        update.setVisible(false);
        retourModif.setVisible(true);
        codeCommuneLabel.setVisible(true);
        idAjout.setVisible(true);
        nomLabel.setVisible(true);
        nomAjout.setVisible(true);
        idDepLabel.setVisible(true);
        departementAjout.setVisible(true);
        if ((Button) event.getSource() == ajouter){
            ajouter1.setVisible(true);
        }
        else if ((Button) event.getSource() == update){
            Commune selectedCommune = tableCommune.getSelectionModel().getSelectedItem();
                if (selectedCommune != null) {
                    idAjout.setText(selectedCommune.getIdCommune()+"");
                }
            update1.setVisible(true);
        }
    }

    /**
     * Gère l'action d'ajout d'une nouvelle commune à la base de données.
     * Récupère les informations saisies dans les champs de texte, crée un nouvel objet Commune,
     * l'ajoute à la base de données et actualise la TableView.
     * Affiche des messages d'erreur si les paramètres sont invalides.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    public void handleAddAction(ActionEvent event) {
        try {
            int idCommune = Integer.parseInt(idAjout.getText());
            String nomCommune = nomAjout.getText();
            int departementID = Integer.parseInt(departementAjout.getText());
            DepartementDAO departementDAO = new DepartementDAO();
            
            Departement departement = departementDAO.findById(departementID);
            Commune commune = new Commune(idCommune, nomCommune, departement, null, null);
            int checkException = communeDAO.create(commune);
            if (checkException == -1){
                actionTarget.setText("Ce code est d\u00e9j\u00e0 utilis\u00e9e par une autre commune");
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
     * Réinitialise les champs de texte de l'interface utilisateur.
     */
    public void emptyTextField(){
        
        idAjout.setText("");
        nomAjout.setText("");
        departementAjout.setText("");
        idCommune.setText("");
        nomCommune.setText("");
        addVoisine.setText("");
        deleteVoisine.setText("");
        searchBar.setText("");
        listeCommunes.setText("");
    }

    /**
     * Rétablit l'affichage précédent avant une modification d'ajout ou de mise à jour de commune.
     * Cache les éléments de l'interface utilisateur ajoutés pour l'ajout ou la mise à jour,
     * et réaffiche les boutons principaux.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    public void retourModif (ActionEvent event){
        gridAction.setId("actionGrid");
        if (event.getSource() == retourModif){
            emptyTextField();
            supprimer.setVisible(true);
            ajouter.setVisible(true);
            update.setVisible(true);
            communeVoisine.setVisible(true);
            retourModif.setVisible(false);
            codeCommuneLabel.setVisible(false);
            idAjout.setVisible(false);
            nomLabel.setVisible(false);
            nomAjout.setVisible(false);
            idDepLabel.setVisible(false);
            departementAjout.setVisible(false);
            ajouter1.setVisible(false);
            update1.setVisible(false);

        } else if (event.getSource() == retourModif1) {
            gridAction.setVisible(true);
            gridVoisines.setVisible(false);
        }
    }

    /**
     * Gère l'action d'agrandissement ou de retour à la vue principale en plein écran.
     * En fonction de l'état actuel de l'application (vue principale ou agrandie),
     * ajuste la taille de la fenêtre et modifie le texte du bouton "Retour" en conséquence.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    public void agrandirEtRetour (ActionEvent event ){

        if(AppCommunes.getRoot().getId().equals("main")){
            
            Scene scene=null;
            try {
                Parent root = FXMLLoader.load(getClass ().getResource("/tableViewCommune.fxml"));
                AppCommunes.setRoot(root);
                scene = new Scene(AppCommunes.getRoot() , 900, 600);
            } catch (IOException e) {
                e.printStackTrace ();
            }
            Stage stage= (Stage) ((Node)event.getSource()).getScene ().getWindow ();
            stage.setScene(scene);
            stage.setFullScreen(true);
        }

        else if(AppCommunes.getRoot().getId().equals("communeTable")){
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
     * Exporte les données des communes affichées dans la TableView vers un fichier CSV.
     * Crée une instance de FileAccess, récupère les données de la TableView,
     * spécifie le nom du fichier CSV et appelle la méthode writeToCSV pour exporter les données.
     * Affiche un message de confirmation à l'utilisateur une fois l'exportation terminée.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    public void exportToCSV(ActionEvent event) {
        // Create an instance of FileAccess
        FileAccess fileAccess = new FileAccess();
        
        // Retrieve the data from the TableView
        ObservableList<Commune> communeList = tableCommune.getItems();
        
        // Add the retrieved data to the FileAccess instance
        fileAccess.communes.addAll(communeList);
        
        // Specify the file name and call the writeToCSV method to export the data
        String fileName = "export_communes.csv";
        fileAccess.writeToCSVCommune(fileName);
        
        // Provide feedback to the user
        actionTarget.setText("Donn\u00e9es export\u00e9es dans " + fileName);

    }

    /**
     * Filtrer les données affichées dans la TableView en fonction du texte de recherche.
     * Crée une liste filtrée basée sur le texte de recherche spécifié.
     * Actualise la TableView pour afficher uniquement les communes correspondantes.
     * Si le champ de recherche est vide, affiche toutes les communes.
     * @param searchText Le texte de recherche saisi par l'utilisateur.
     */
    private void filterTableData(String searchText) {
        ObservableList<Commune> filteredList = FXCollections.observableArrayList();
        List<Commune> communeList = communeDAO.findAll();
        
        if (searchText == null || searchText.isEmpty()) {
            filteredList.addAll(communeList);
        } else {
            for (Commune commune : communeList) {
                if (String.valueOf(commune.getIdCommune()).contains(searchText) || 
                    commune.getNomCommune().toLowerCase().contains(searchText.toLowerCase()) ||
                    (commune.getDepartement() != null && commune.getDepartement().getNomDep().toLowerCase().contains(searchText.toLowerCase()))) {
                    filteredList.add(commune);
                }
            }
        }
        tableCommune.setItems(filteredList);
    }


    /**
     * Rend visible la grille permettant l'ajout et le retrait de communes voisines.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    void handleCommuneVoisineAction(ActionEvent event) {
        // Hide the current action grid
        gridAction.setVisible(false);

        gridVoisines.setVisible(true);
    }

    /**
     * Permet de suprimer un voisinage de la base de données.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    public void deleteCommune(ActionEvent event){
        String communeId = deleteVoisine.getText();
        if (String.valueOf(listeCommunes).contains(communeId)){
            communeDAO.deleteVoisinage(communeDAO.findById(Integer.parseInt(idCommune.getText())) ,communeDAO.findById(Integer.parseInt(communeId)));
        }
        emptyTextField();
        loadTableData();
    }

    /**
     * Permet d'ajouter un nouveau voisinage de la base de données.
     * @param event L'événement d'action déclenché par l'utilisateur.
     */
    @FXML
    public void addCommune(ActionEvent event){

        String communeId = addVoisine.getText();
        communeDAO.createVoisinage(communeDAO.findById(Integer.parseInt(idCommune.getText())) ,communeDAO.findById(Integer.parseInt(communeId)));
        emptyTextField();
        loadTableData();
    }

}
