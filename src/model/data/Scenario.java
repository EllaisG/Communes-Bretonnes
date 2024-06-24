package model.data;
import java.util.ArrayList;

/**
 * Classe permettant de creer un scenario d'utilisation des classes : Aeroport, Annee, Commune, 
 * Departement, DonneesAnnuelles, Gare et Utilisateur.
 */
public class Scenario {

    /**
     * La methode d'entre de la classe Scenario 
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        //Initialisation d'un objet Departement
        Departement d1 = new Departement(1, "Morbihan", 14558645, null);

        //Initialisation des objets Commune
        Commune c1 = new Commune(1, "Lorient", d1, null, null);
        Commune c2 = new Commune(2, "Vannes", d1, null, null);
        
        //Initialisation des objets Gares
        Gare g1 = new Gare(1, "toto", false, true, c1);
        Gare g2 = new Gare(2, "titi", true, true, c2);

        //Ajout des communes dans le departement
        d1.ajouterUneCommune(c1);
        d1.ajouterUneCommune(c2);

        //Ajout des gares dans l'attribut listeGares de chaque commune 
        c1.ajouterGare(g1);
        c2.ajouterGare(g2);

        //Ajout des communes voisines (chaque commune est voisine de l'autre)
        c1.ajouterCommuneVoisine(c2);
        c2.ajouterCommuneVoisine(c1);

        //Trouver la commune Lorient et ses informations
        System.out.println("=> Recherche d'une commune dans le departement: ");
        Commune ex1 = d1.trouverCommuneParNom("Lorient");
        System.out.println(ex1.toString());

        //Regarder les communes voisines de c1
        System.out.println("=> Affichage des communes voisines de "+ c1.getNomCommune()+" : ");
        ArrayList<Commune> ex2 = c1.getCommunesVoisines();
        for (Commune commune : ex2){
            System.out.println(commune.toString());
        } 

        //Recuperer une commune voisine de c2
        System.out.println("=> Affichage d'une commune voisine de "+ c2.getNomCommune()+" : ");
        Commune ex3 = c2.getUneCommuneVoisine(1);
        System.out.println(ex3.toString());

        //Trouver les communes voisines d'une gare
        System.out.println("=> Affichage des commune voisines d'une gare: ");
        ArrayList<Commune> ex4 = g1.rechercheCommuneVoisine();
        for (Commune commune : ex4){
            System.out.println(commune.toString());
        }

        System.out.println("=> Le type de gare de "+g2.getNomGare()+" : ");
        String ex5 = g2.obtenirTypeGare();
        System.out.println(ex5);


        //Initialisation d'un objet Aeroport
        Aeroport ae1 = new Aeroport("nono", "rue de la liberte", d1);

        //Regarder les communes du departement susceptible d'aller dans cet aeroport
        ae1.obtenirCommunesDepartement();

        //Initialisation d'un objet Utilisateur
        Utilisateur u1 = new Utilisateur("root", "root");

        //Methode Utilisateur
        u1.getIdentifiant();
        u1.setIdentifiant("Student");


        //Initialisation des objets Annee et DonneesAnnuelles
        Annee a1 = new Annee(2005, 0.5);
        Annee a2 = new Annee(2006, 1.2);
        DonneesAnnuelles da1 = new DonneesAnnuelles(a1, c1, 10, 2, 5951, 0, 0, 0, 10000, 0);
        DonneesAnnuelles da2 = new DonneesAnnuelles(a2, c1, 11, 5, 45122, 0, 0, 0, 50000, 0);

        a1.comparerInflation(a2);
        da1.comparerBudgetTotal(da2);
    }
}
