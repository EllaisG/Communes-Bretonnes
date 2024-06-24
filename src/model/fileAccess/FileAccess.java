package model.fileAccess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.data.Aeroport;
import model.data.Annee;
import model.data.Commune;
import model.data.Departement;
import model.data.DonneesAnnuelles;
import model.data.Gare;
import model.data.Utilisateur;

/**
 * Classe FileAccess pour gérer l'accès et la manipulation des données.
 * Cette classe contient des listes de différents objets pour stocker des données sur des aéroports, années, communes,
 * départements, données annuelles, gares et utilisateurs.
 */
public class FileAccess {

    /**
     * Liste des aéroports.
     */
    public ArrayList<Aeroport> aeroports;

    /**
     * Liste des années.
     */
    public ArrayList<Annee> annees;

    /**
     * Liste des communes.
     */
    public ArrayList<Commune> communes;

    /**
     * Liste des départements.
     */
    public ArrayList<Departement> departements;

    /**
     * Liste des données annuelles.
     */
    public ArrayList<DonneesAnnuelles> donneesAnnuelles;

    /**
     * Liste des gares.
     */
    public ArrayList<Gare> gares;

    /**
     * Liste des utilisateurs.
     */
    public ArrayList<Utilisateur> utilisateurs;

    /**
     * Constructeur qui initialise les listes des differents attributs
     */
    public FileAccess() {
        aeroports = new ArrayList<Aeroport>();
        annees = new ArrayList<Annee>();
        communes = new ArrayList<Commune>();
        departements = new ArrayList<Departement>();
        donneesAnnuelles = new ArrayList<DonneesAnnuelles>();
        gares = new ArrayList<Gare>();
        utilisateurs = new ArrayList<Utilisateur>();
    }

    /**
     * Retourne la liste d'aeroports
     * @return la liste d'aeroports
     */
    public ArrayList<Aeroport> getAeroports() {
        return this.aeroports;
    }

    /**
     * Retourne la liste d'annees
     * @return la liste d'annees
     */
    public ArrayList<Annee> getAnnees() {
        return this.annees;
    }

    /**
     * Retourne la liste de communes 
     * @return la liste de communes
     */
    public ArrayList<Commune> getCommunes() {
        return this.communes;
    }

    /**
     * Retourne la liste de departements
     * @return la liste de departements
     */
    public ArrayList<Departement> getDepartements() {
        return this.departements;
    }

    /**
     * Retourne la liste de donnees annuelles
     * @return la liste de donnees annuelles 
     */
    public ArrayList<DonneesAnnuelles> getDonneesAnnuelles() {
        return this.donneesAnnuelles;
    }

    /**
     * Retourne la liste des gares
     * @return la liste des gares
     */
    public ArrayList<Gare> getGares() {
        return this.gares;
    }

    /**
     * Retourne la liste des utilisateurs
     * @return la liste des utilisateurs
     */
    public ArrayList<Utilisateur> getUtilisateurs() {
        return this.utilisateurs;
    }

    /**
     * Ecrit dans un fichier, les données de la liste des aeroports
     * @param nomFichier le nom du fichier 
     */
    public void writeToCSVAeroport(String nomFichier) {
        BufferedWriter tampon;
        try {
            tampon = new BufferedWriter(new FileWriter(nomFichier, true)); // true pour append mode

            for (Aeroport aeroport : this.aeroports) {
                // Construire une chaîne avec les informations de l'étudiant
                StringBuilder aeroportInfo = new StringBuilder();
                aeroportInfo.append(aeroport.getNomAeroport()).append(";");
                aeroportInfo.append(aeroport.getAdresseAeroport()).append(";");
                aeroportInfo.append(aeroport.getDepartement().getIdDep());

                // Écrire la chaîne dans le fichier
                tampon.write(aeroportInfo.toString());
                tampon.newLine(); // Ajouter une nouvelle ligne après chaque étudiant
            }

            tampon.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ecrit dans un fichier, les données de la liste des annees
     * @param nomFichier le nom du fichier 
     */
    public void writeToCSVAnnee(String nomFichier) {
        BufferedWriter tampon;
        try {
            tampon = new BufferedWriter(new FileWriter(nomFichier, true)); // true pour append mode

            for (Annee annee : this.annees) {
                // Construire une chaîne avec les informations de l'étudiant
                StringBuilder anneeInfo = new StringBuilder();
                anneeInfo.append(annee.getAnnee()).append(";");
                anneeInfo.append(annee.getTauxInflation()).append(";");

                // Écrire la chaîne dans le fichier
                tampon.write(anneeInfo.toString());
                tampon.newLine(); // Ajouter une nouvelle ligne après chaque étudiant
            }

            tampon.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ecrit dans un fichier, les données de la liste des communes
     * @param nomFichier le nom du fichier 
     */
    public void writeToCSVCommune(String nomFichier) {
        BufferedWriter tampon;
        try {
            tampon = new BufferedWriter(new FileWriter(nomFichier, true)); // true pour append mode

            for (Commune commune : this.communes) {
                // Construire une chaîne avec les informations de l'étudiant
                StringBuilder communeInfo = new StringBuilder();
                communeInfo.append(commune.getIdCommune()).append(";");
                communeInfo.append(commune.getNomCommune()).append(";");
                communeInfo.append(commune.getDepartement().getIdDep()).append(";");
                communeInfo.append(commune.getIdCommunesVoisines()).append(";");
                communeInfo.append(commune.getCodeListeGares()).append(";");

                // Écrire la chaîne dans le fichier
                tampon.write(communeInfo.toString());
                tampon.newLine(); // Ajouter une nouvelle ligne après chaque étudiant
            }

            tampon.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ecrit dans un fichier, les données de la liste des departements
     * @param nomFichier le nom du fichier 
     */
    public void writeToCSVDepartement(String nomFichier) {
        BufferedWriter tampon;
        try {
            tampon = new BufferedWriter(new FileWriter(nomFichier, true)); // true pour append mode

            for (Departement departement : this.departements) {
                // Construire une chaîne avec les informations de l'étudiant
                StringBuilder departementInfo = new StringBuilder();
                departementInfo.append(departement.getIdDep()).append(";");
                departementInfo.append(departement.getNomDep()).append(";");
                departementInfo.append(departement.getInvesCulturel2019()).append(";");
                departementInfo.append(departement.getIdListeCommunes()).append(";");
                // Écrire la chaîne dans le fichier
                tampon.write(departementInfo.toString());
                tampon.newLine(); // Ajouter une nouvelle ligne après chaque étudiant
            }

            tampon.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ecrit dans un fichier, les données de la liste des donnees annuelles
     * @param nomFichier le nom du fichier 
     */
    public void writeToCSVDonneesAnnuelles(String nomFichier) {
        BufferedWriter tampon;
        try {
            tampon = new BufferedWriter(new FileWriter(nomFichier, true)); // true pour append mode

            for (DonneesAnnuelles donneeAnnuelle : this.donneesAnnuelles) {
                // Construire une chaîne avec les informations de l'étudiant
                StringBuilder donneesAnnuellesInfo = new StringBuilder();
                donneesAnnuellesInfo.append(donneeAnnuelle.getAnnee().getAnnee()).append(";");
                donneesAnnuellesInfo.append(donneeAnnuelle.getCommune().getIdCommune()).append(";");
                donneesAnnuellesInfo.append(donneeAnnuelle.getNbMaison()).append(";");
                donneesAnnuellesInfo.append(donneeAnnuelle.getNbAppart()).append(";");
                donneesAnnuellesInfo.append(donneeAnnuelle.getPrixMoyen()).append(";");
                donneesAnnuellesInfo.append(donneeAnnuelle.getPrixM2Moyen()).append(";");
                donneesAnnuellesInfo.append(donneeAnnuelle.getSurfaceMoy()).append(";");
                donneesAnnuellesInfo.append(donneeAnnuelle.getDepCulturellesTotales()).append(";");
                donneesAnnuellesInfo.append(donneeAnnuelle.getBudgetTotal()).append(";");
                donneesAnnuellesInfo.append(donneeAnnuelle.getPopulation()).append(";");

                // Écrire la chaîne dans le fichier
                tampon.write(donneesAnnuellesInfo.toString());
                tampon.newLine(); // Ajouter une nouvelle ligne après chaque étudiant
            }

            tampon.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ecrit dans un fichier, les données de la liste des gares
     * @param nomFichier le nom du fichier 
     */
    public void writeToCSVGare(String nomFichier) {
        BufferedWriter tampon;
        try {
            tampon = new BufferedWriter(new FileWriter(nomFichier, true)); // true pour append mode

            for (Gare gare : this.gares) {
                // Construire une chaîne avec les informations de l'étudiant
                StringBuilder gareInfo = new StringBuilder();
                gareInfo.append(gare.getCodeGare()).append(";");
                gareInfo.append(gare.getNomGare()).append(";");
                gareInfo.append(gare.getEstFret()).append(";");
                gareInfo.append(gare.getEstVoyageur()).append(";");
                gareInfo.append(gare.getCommune().getIdCommune()).append(";");

                // Écrire la chaîne dans le fichier
                tampon.write(gareInfo.toString());
                tampon.newLine(); // Ajouter une nouvelle ligne après chaque étudiant
            }

            tampon.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ecrit dans un fichier, les données de la liste des utilisateurs
     * @param nomFichier le nom du fichier 
     */
    public void writeToCSVUtilisateur(String nomFichier) {
        BufferedWriter tampon;
        try {
            tampon = new BufferedWriter(new FileWriter(nomFichier, true)); // true pour append mode

            for (Utilisateur utilisateur : this.utilisateurs) {
                // Construire une chaîne avec les informations de l'étudiant
                StringBuilder utilisateurInfo = new StringBuilder();
                utilisateurInfo.append(utilisateur.getIdentifiant()).append(";");
                utilisateurInfo.append(utilisateur.getMdp()).append(";");

                // Écrire la chaîne dans le fichier
                tampon.write(utilisateurInfo.toString());
                tampon.newLine(); // Ajouter une nouvelle ligne après chaque étudiant
            }

            tampon.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
