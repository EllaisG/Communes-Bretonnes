package model.data;

/**
 * Classe représentant les données annuelles d'une communes pour chaque année 
 */
public class DonneesAnnuelles {
    
    /**L'année des données */
    private Annee annee;
    /**La commune d'où provient les données */
    private Commune commune;
    /**Le nombre de maisons vendues */
    private int nbMaison;
    /**Le nombre d'appartements vendus */
    private int nbAppart;
    /**Le prix moyen */
    private double prixMoyen;
    /**Le prix moyen au mètre carré */
    private double prixM2Moyen;
    /**La surface moyenne */
    private double surfaceMoy;
    /**Le total des dépenses culturelles */
    private double depCulturellesTotales;
    /**Le budget total */
    private double budgetTotal;
    /**Le population de la commune pour une année donnée */
    private double population;

    /**
     * Constructeur de la classe DonneesAnnuelles
     * @param anneeP l'année
     * @param communeP la commune
     * @param nbMaisonP le nombre de maisons
     * @param nbAppartP le nombre d'appartements
     * @param prixMoyenP le prix moyen
     * @param prixM2MoyenP le prix moyen au mètre carré
     * @param surfaceMoyP la surface moyenne
     * @param depCulturellesTotalesP le total des dépenses culturelles
     * @param budgetTotalP le budget total
     * @param populationP la population
     */
    public DonneesAnnuelles(Annee anneeP, Commune communeP, int nbMaisonP, int nbAppartP, double prixMoyenP, double prixM2MoyenP, double surfaceMoyP, double depCulturellesTotalesP, double budgetTotalP, double populationP) throws IllegalArgumentException {
        if (anneeP != null && communeP != null && nbMaisonP >= 0 && nbAppartP >= 0 && prixMoyenP >= 0 && prixM2MoyenP >= 0 && surfaceMoyP >= 0) {
            this.annee = anneeP;
            this.commune = communeP;
            this.nbMaison = nbMaisonP;
            this.nbAppart = nbAppartP;
            this.prixMoyen = prixMoyenP;
            this.prixM2Moyen = prixM2MoyenP;
            this.surfaceMoy = surfaceMoyP;
            this.depCulturellesTotales = depCulturellesTotalesP;
            this.budgetTotal = budgetTotalP;
            this.population = populationP;
        } else {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être nuls ou négatifs");
        }
    }

    /**
     * Getter pour l'année
     * @return l'année
     */
    public Annee getAnnee(){
        return this.annee;
    }

    /**
     * Getter pour la commune
     * @return la commune
     */
    public Commune getCommune(){
        return this.commune;
    }

    /**
     * Getter pour le nombre de maisons
     * @return le nombre de maisons
     */
    public int getNbMaison(){
        return this.nbMaison;
    }

    /**
     * Getter pour le nombre d'appartements
     * @return le nombre d'appartements
     */
    public int getNbAppart(){
        return this.nbAppart;
    }

    /**
     * Getter pour le prix moyen
     * @return le prix moyen
     */
    public double getPrixMoyen(){
        return this.prixMoyen;
    }

    /**
     * Getter pour le prix moyen au mètre carré
     * @return le prix moyen au mètre carré
     */
    public double getPrixM2Moyen(){
        return this.prixM2Moyen;
    }

    /**
     * Getter pour la surface moyenne
     * @return la surface moyenne
     */
    public double getSurfaceMoy(){
        return this.surfaceMoy;
    }

    /**
     * Getter pour le total des dépenses culturelles
     * @return le total des dépenses culturelles
     */
    public double getDepCulturellesTotales(){
        return this.depCulturellesTotales;
    }

    /**
     * Getter pour le budget total
     * @return le budget total
     */
    public double getBudgetTotal(){
        return this.budgetTotal;
    }

    /**
     * Getter pour la population
     * @return la population
     */
    public double getPopulation(){
        return this.population;
    }

    /**
     * Modifie l'année de la donnée annuelle avec l'année en paramètre, si elle est valide
     * @param anneeP la nouvelle année
     */
    public void setAnnee(Annee anneeP) throws IllegalArgumentException {
        if (anneeP == null){
            throw new IllegalArgumentException("L'année ne peut pas être nulle");
        } else {
            this.annee = anneeP;
        }
    }

    /**
     * Modifie la commune de la donnée annuelle avec la commune en paramètre, si elle est valide
     * @param communeP la nouvelle commune
     */
    public void setCommune(Commune communeP) throws IllegalArgumentException {
        if (communeP == null){
            throw new IllegalArgumentException("La commune ne peut pas être nulle");
        } else {
            this.commune = communeP;
        }
    }

    /**
     * Modifie le nombre de maisons vendues avec le nombre de maisons en paramètre, s'il est valide
     * @param nbMaisonP le nouveau nombre de maisons
     */
    public void setNbMaison(int nbMaisonP) throws IllegalArgumentException {
        if (nbMaisonP < 0){
            throw new IllegalArgumentException("Le nombre de maisons ne peut pas être négatif");
        } else {
            this.nbMaison = nbMaisonP;
        }
    }

    /**
     * Modifie le nombre d'appartements vendues avec le nombre d'appartements en paramètre, s'il est valide
     * @param nbAppartP le nouveau nombre d'appartements
     */
    public void setNbAppart(int nbAppartP) throws IllegalArgumentException {
        if (nbAppartP < 0){
            throw new IllegalArgumentException("Le nombre d'appartements ne peut pas être négatif");
        } else {
            this.nbAppart = nbAppartP;
        }
    }

    /**
     * Modifie le prix moyen avec le prix moyen en paramètre, s'il est valide
     * @param prixMoyenP le nouveau prix moyen
     */
    public void setPrixMoyen(double prixMoyenP) throws IllegalArgumentException {
        if (prixMoyenP < 0){
            throw new IllegalArgumentException("Le prix moyen ne peut pas être négatif");
        } else {
            this.prixMoyen = prixMoyenP;
        }
    }

    /**
     * Modifie le prix moyen au mètre carré avec le prix moyen au mètre carré en paramètre, s'il est valide
     * @param prixM2MoyenP le nouveau prix moyen au mètre carré
     */
    public void setPrixM2Moyen(double prixM2MoyenP) throws IllegalArgumentException {
        if (prixM2MoyenP < 0){
            throw new IllegalArgumentException("Le prix moyen au mètre carré ne peut pas être négatif");
        } else {
            this.prixM2Moyen = prixM2MoyenP;
        }
    }

    /**
     * Modifie la surface moyenne avec la surface moyenne en paramètre, si elle est valide
     * @param surfaceMoyP la nouvelle surface moyenne
     */
    public void setSurfaceMoy(double surfaceMoyP) throws IllegalArgumentException {
        if (surfaceMoyP < 0){
            throw new IllegalArgumentException("La surface moyenne ne peut pas être négative");
        } else {
            this.surfaceMoy = surfaceMoyP;
        }
    }

    /**
     * Modifie le total des dépenses culturelles avec le total en paramètre, s'il est valide
     * @param depCulturellesTotalesP le nouveau total des dépenses culturelles
     */
    public void setDepCulturellesTotales(double depCulturellesTotalesP) throws IllegalArgumentException {
        if (depCulturellesTotalesP < 0){
            throw new IllegalArgumentException("Le total des dépenses culturelles ne peut pas être négatif");
        } else {
            this.depCulturellesTotales = depCulturellesTotalesP;
        }
    }

    /**
     * Modifie le budget total avec le budget en paramètre, s'il est valide
     * @param budgetTotalP le nouveau budget total
     */
    public void setBudgetTotal(double budgetTotalP) throws IllegalArgumentException {
        if (budgetTotalP < 0){
            throw new IllegalArgumentException("Le budget total ne peut pas être négatif");
        } else {
            this.budgetTotal = budgetTotalP;
        }
    }

    /**
     * Modifie la population avec la population en paramètre, si elle est valide
     * @param populationP la nouvelle population
     */
    public void setPopulation(double populationP) throws IllegalArgumentException {
        if (populationP < 0){
            throw new IllegalArgumentException("La population ne peut pas être négative");
        } else {
            this.population = populationP;
        }
    }

    /**
     * Méthode permettant d'afficher les données annuelles
     * @return les données annuelles
     */
    public String toString(){
        return "Données annuelles pour la commune de " + this.commune.getNomCommune() + " (id : " + this.commune.getIdCommune() + ") en " + this.annee.getAnnee() + " : \n- " + this.nbMaison + " maisons\n- " + this.nbAppart + " appartements\n- Prix moyen = " + this.prixMoyen + " euros\n- Prix moyen au mètre carré = " + this.prixM2Moyen + " euros\n- Surface moyenne = " + this.surfaceMoy + " m²\n- Total des dépenses culturelles = " + this.depCulturellesTotales + " euros\n- Budget total = " + this.budgetTotal + " euros\n- Population = " + this.population + " habitants";
    }

    /**
     * Méthode permettant de comparer le budget total avec celui d'une autre donnée annuelle.
     * @param autreDonnees les données annuelles d'une autre année
     * @return une chaine de caractères indiquant si le budget a augmenté, diminué ou est resté le même
     */
    public String comparerBudgetTotal(DonneesAnnuelles autreDonnees) {
        String resultat;
        if (this.budgetTotal > autreDonnees.getBudgetTotal()) {
            resultat = "Le budget total a augmenté par rapport à " + autreDonnees.getAnnee().getAnnee();
        } else if (this.budgetTotal < autreDonnees.getBudgetTotal()) {
            resultat = "Le budget total a diminué par rapport à " + autreDonnees.getAnnee().getAnnee();
        } else {
            resultat = "Le budget total est resté le même par rapport à " + autreDonnees.getAnnee().getAnnee();
        }
        return resultat;
    }
}
