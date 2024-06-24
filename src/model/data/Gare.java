package model.data;

import java.util.ArrayList;

/**
 * La classe Gare represente une gare avec son code, son nom, son type et sa commune 
 */
public class Gare{
    
    /**Le code de la gare */
    private int codeGare;
    /**Le nom de la gare */
    private String nomGare;
    /**La gare gere-t-elle les fret ? */
    private boolean estFret;
    /**La gare accueille-t-elle des voyageurs ? */
    private boolean estVoyageur;
    /**La commune de la gare */
    private Commune commune;

    /**
     * Constructeur de la classe Gare
     * @param codeGareP le code de la gare
     * @param nomGareP le nom de la gare
     * @param estFretP la gare est-elle une gare de fret ?
     * @param estVoyageurP la gare est-elle une gare de voyageurs ?
     * @param communeP la commune de la gare
     */
    public Gare(int codeGareP, String nomGareP, boolean estFretP, boolean estVoyageurP, Commune communeP) throws IllegalArgumentException {

        if (codeGareP >= 0 && nomGareP != null && communeP != null) {
            this.codeGare = codeGareP;
            this.nomGare = nomGareP;
            this.estFret = estFretP;
            this.estVoyageur = estVoyageurP;
            this.commune = communeP;
        } else {
            throw new IllegalArgumentException("Les parametres ne peuvent pas être nuls ou negatifs");
        }
    }

    /**
     * Getter pour le code de la gare
     * @return le code de la gare
     */
    public int getCodeGare() {
        return this.codeGare;
    }

    /**
     * Getter pour le nom de la gare
     * @return le nom de la gare
     */
    public String getNomGare() {
        return this.nomGare;
    }

    /**
     * Getter pour savoir si la gare est une gare de fret
     * @return true si la gare est une gare de fret, false sinon
     */
    public String getEstFretBoolean() {
        return this.nomGare;
    }

    /**
     * Getter pour savoir si la gare est une gare de voyageurs
     * @return true si la gare est une gare de voyageurs, false sinon
     */
    public String getEstVoyageurBoolean() {
        return this.nomGare;
    }

    /**
     * Getter pour savoir si la gare est une gare de fret
     * @return 1 si la gare est une gare de fret, 0 sinon
     */
    public int getEstFret() {
        int ret = 0;
        if (this.estFret == true){
            ret = 1;
        }
        return ret;
    }

    /**
     * Getter pour savoir si la gare est une gare de voyageurs
     * @return 1 si la gare est une gare de voyageurs, 0 sinon
     */
    public int getEstVoyageur() {
        int ret = 0;
        if (this.estVoyageur == true){
            ret = 1;
        }
        return ret;
    }

    /**
     * Getter pour la commune de la gare
     * @return la commune de la gare
     */
    public Commune getCommune() {
        return this.commune;
    }

    /**
     * Modifie le code de la gare avec le code en parametre, s'il est valide
     * @param codeGareP le nouveau code de la gare
     */
    public void setCodeGare(int codeGareP) throws IllegalArgumentException {
        if (codeGareP < 0){
            throw new IllegalArgumentException("Le code ne peut pas être negatif.");
        } else {
            this.codeGare = codeGareP;
        }
    }

    /**
     * Modifie le nom de la gare avec le nom en parametre, s'il est valide
     * @param nomGareP le nouveau nom de la gare
     */
    public void setNomGare(String nomGareP) throws IllegalArgumentException {
        if (nomGareP == null){
            throw new IllegalArgumentException("Le nom ne peut pas être nul.");
        } else {
            this.nomGare = nomGareP;
        }
    }

    /**
     * Modifie le type estFret de la gare
     * @param estFretP true si la gare est une gare de fret, false sinon
     */
    public void setEstFret(boolean estFretP) {
        this.estFret = estFretP;
    }

    /**
     * Modifie le type estVoyageur de la gare
     * @param estVoyageurP true si la gare est une gare de voyageurs, false sinon
     */
    public void setEstVoyageurs(boolean estVoyageurP) {
        this.estVoyageur = estVoyageurP;
    }

    /**
     * Modifie la commune de la gare avec la commune en parametre, si elle est valide
     * @param communeP la nouvelle commune de la gare
     */
    public void setCommune(Commune communeP) throws IllegalArgumentException {
        if (communeP == null){
            throw new IllegalArgumentException("La commune ne peut pas être nulle.");
        } else {
            this.commune = communeP;
        }
    }

    /**
     * Methode toString
     * @return une chaine de caracteres decrivant la gare
     */
    public String toString() {
        return "Gare " + this.nomGare + " (code : " + this.codeGare + ") : \n- Fret = " + this.estFret + "\n- Voyageur = " + this.estVoyageur + "\n- Commune = " + this.commune;
    }
    
    /**
     * Retourne les communes au alentour de la gares
     * @return la liste des communes voisines de cette gare
     */
    public ArrayList<Commune> rechercheCommuneVoisine(){
        return this.commune.getCommunesVoisines();
    }

    /**
     * Methode pour obtenir le type de gare.
     * @return une chaine de caracteres indiquant le type de la gare : "Fret", "Voyageur", ou "Mixte"
     */
    public String obtenirTypeGare() {
        String typeGare;

        if (this.estFret && this.estVoyageur) {
            typeGare = "Mixte";
        } else if (this.estFret) {
            typeGare = "Fret";
        } else if (this.estVoyageur) {
            typeGare = "Voyageur";
        } else {
            typeGare = "Inconnu";
        }
        return typeGare;
    }
}
