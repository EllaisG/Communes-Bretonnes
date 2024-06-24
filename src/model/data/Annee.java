package model.data;

/**
 * La classe Annee represente une annee avec son taux d'inflation
 */
public class Annee{

    /**L'annee */
    private int annee;
    /**Le taux d'inflation de l'annee */
    private double tauxInflation;

    /**
     * Constructeur de la classe Annee
     * @param anneeP l'annee
     * @param tauxInflationP le taux d'inflation
     */
    public Annee(int anneeP, double tauxInflationP) throws IllegalArgumentException {
        
        if (anneeP >= 0) {
            this.annee = anneeP;
            this.tauxInflation = tauxInflationP;
        } else {
            throw new IllegalArgumentException("Les parametres ne peuvent pas être negatifs");
        }
    }

    /**
     * Getter pour l'annee
     * @return l'annee
     */
    public int getAnnee(){
        return this.annee;
    }

    /**
     * Getter pour le taux d'inflation
     * @return le taux d'inflation
     */
    public double getTauxInflation(){
        return this.tauxInflation;
    }

    /**
     * Modifie l'annee de l'objet avec l'annee en parametre, si elle est valide
     * @param annee la nouvelle annee
     * @throws IllegalArgumentException si l'annee est negative
     */
    public void setAnnee(int annee) throws IllegalArgumentException {
        if (annee < 0){
            throw new IllegalArgumentException("L'annee ne peut pas être negative");
        } else {
            this.annee = annee;
        }
    }

    /**
     * Modifie le taux de l'inflation de l'objet avec le taux de l'inflation en parametre, s'il est valide
     * @param tauxInflation le nouveau taux d'inflation
     */
    public void setTauxInflation(double tauxInflation){
        this.tauxInflation = tauxInflation;
    }

    /**
     * Methode affichant les informations d'une annee.
     * @return une chaine de caracteres decrivant l'annee
     */
    public String toString(){
        return "Annee " + this.annee + " :\n- Taux d'inflation = " + this.tauxInflation;
    }

    /**
     * Methode permettant de comparer le taux d'inflation avec une autre annee.
     * @param autreAnnee l'autre annee a comparer
     * @return 1 si le taux d'inflation de l'annee courante est superieur, -1 s'il est inferieur, 0 s'ils sont egaux, 2 si erreur
     */
    public int comparerInflation(Annee autreAnnee) {
        int ret = 2;
        if (this.tauxInflation > autreAnnee.getTauxInflation()){
            ret = 1;
        } else if (this.tauxInflation < autreAnnee.getTauxInflation()){
            ret = -1;
        } else {
            ret = 0;
        }
        return ret;
    }
}
