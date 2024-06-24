package model.fileAccess;

import model.data.*;
import model.fileAccess.*;
import java.util.ArrayList;

public class TestFileAccess {
    public static void main(String[] args) {
        //Init départements
        ArrayList<Departement> departements = new ArrayList<Departement>();
        departements.add(new Departement(1, "Morbihan", 100, null));
        //Init Communes
        ArrayList<Commune> communes = new ArrayList<Commune>();
        communes.add(new Commune(0, "toto", departements.get(0), communes, null));
        //Init Gares
        ArrayList<Gare> gares = new ArrayList<Gare>();
        gares.add(new Gare(0, "titi", false, true, communes.get(0)));
        communes.get(0).setListeGare(gares);
        departements.get(0).setListeCommunes(communes);
        //Init Aeroport
        ArrayList<Aeroport> aeroports = new ArrayList<Aeroport>();
        aeroports.add(new Aeroport("ae", "null", departements.get(0)));
        //Init Annee
        ArrayList<Annee> annees = new ArrayList<Annee>();
        annees.add(new Annee(2020, 1.5));
        //Init DonneesAnnuelles
        ArrayList<DonneesAnnuelles> donneesAnn = new ArrayList<DonneesAnnuelles>();
        donneesAnn.add(new DonneesAnnuelles(annees.get(0), communes.get(0), 0, 0, 0, 0, 0, 0, 0, 0));
        //Init Utilisateur
        ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        utilisateurs.add(new Utilisateur("null", "null"));

        //Exportation
        FileAccess fileA = new FileAccess();
        fileA.departements = departements;
        fileA.communes = communes;
        fileA.gares = gares;
        fileA.aeroports = aeroports;
        fileA.annees = annees;
        fileA.donneesAnnuelles = donneesAnn;
        fileA.utilisateurs = utilisateurs;
        fileA.writeToCSVDepartement("testDep");
        fileA.writeToCSVCommune("testCom");
        fileA.writeToCSVGare("testGare");
        fileA.writeToCSVAeroport("testAe");
        fileA.writeToCSVAnnee("testAnn");
        fileA.writeToCSVDonneesAnnuelles("testDA");
        fileA.writeToCSVUtilisateur("testUti");
    }
}
