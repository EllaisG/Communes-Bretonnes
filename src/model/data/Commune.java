package model.data;

import java.util.ArrayList;

/**
 * La classe Commune represente une commune avec son identifiant, son nom, son departement, ses communes voisines et ses gares.
 */
public class Commune {

    /**L'identifiant de la commune */
    private int idCommune;
    /**Le nom de la commune */
    private String nomCommune;
    /**Le departement de la commune */
    private Departement departement;
    /**Les communes voisines de la commune courante */
    private ArrayList<Commune> communesVoisines;
    /**Les gares de la commune */
    private ArrayList<Gare> listeGares;

    /**
     * Constructeur de la classe Commune
     * @param idCommuneP l'identifiant de la commune
     * @param nomCommuneP le nom de la commune
     * @param departementP le departement de la commune
     * @param communesVoisinesP la liste des communes voisines
     * @param listeGaresP les gares de la commune
     */
    public Commune(int idCommuneP, String nomCommuneP, Departement departementP, ArrayList<Commune> communesVoisinesP, ArrayList<Gare> listeGaresP) throws IllegalArgumentException {
        
        if (idCommuneP >= 0 && nomCommuneP != null && departementP != null) {
            this.idCommune = idCommuneP;
            this.nomCommune = nomCommuneP;
            this.departement = departementP;

            if (communesVoisinesP != null && listeGaresP != null){
                this.communesVoisines = communesVoisinesP;
                this.listeGares = listeGaresP;
            } else {
                this.communesVoisines = new ArrayList<Commune>();
                this.listeGares = new ArrayList<Gare>();
            }
        } else {
            throw new IllegalArgumentException("Les parametres id, nom de la commune et departement ne peuvent pas être nuls ou negatifs");
        }
    }

    /**
     * Getter pour l'identifiant de la commune
     * @return l'identifiant de la commune
     */
    public int getIdCommune(){
        return this.idCommune;
    }

    /**
     * Getter pour le nom de la commune
     * @return le nom de la commune
     */
    public String getNomCommune(){
        return this.nomCommune;
    }

    /**
     * Getter pour le departement de la commune
     * @return le departement de la commune
     */
    public Departement getDepartement(){
        return this.departement;
    }

    /**
     * Getter pour la liste des communes voisines
     * @return la liste des communes voisines
     */
    public ArrayList<Commune> getCommunesVoisines(){
        return this.communesVoisines;
    }

    /**
     * Getter pour la liste des identifiants des communes voisines
     * @return la liste des identifiants des communes voisines
     */
    public ArrayList<Integer> getIdCommunesVoisines(){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (Commune commune : this.communesVoisines){
            ret.add(commune.getIdCommune());
        }
        return ret;
    }

    /** Getter pour la liste de gares de la commune
     * @return les gares de la commune
     */
    public ArrayList<Gare> getListeGares() {
        return this.listeGares;
    }

    /**
     * Getter pour la liste des identifiants des communes voisines
     * @return la liste des identifiants des communes voisines
     */
    public ArrayList<Integer> getCodeListeGares(){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (Gare gare : this.listeGares){
            ret.add(gare.getCodeGare());
        }
        return ret;
    }

    /**
     * Modifie l'identifiant de la commune avec l'identifiant en parametre, s'il est valide
     * @param idCommuneP le nouvel identifiant de la commune
     * @throws IllegalArgumentException si l'id est negatif
     */
    public void setIdCommune(int idCommuneP) throws IllegalArgumentException{
        if (idCommuneP < 0){
            throw new IllegalArgumentException("L'id ne peut pas être negatif");
        } else {
            this.idCommune = idCommuneP;
        }
    }

    /**
     * Modifie le nom de la commune avec le nom en parametre, s'il est valide
     * @param nomCommuneP le nouveau nom de la commune
     * @throws IllegalArgumentException si le nom est null
     */
    public void setNomCommune(String nomCommuneP) throws IllegalArgumentException {
        if (nomCommuneP == null){
            throw new IllegalArgumentException("Le nom ne peut pas être nul");
        } else {
            this.nomCommune = nomCommuneP;
        }
    }

    /**
     * Modifie le departement de la commune avec le departement en parametre, s'il est valide
     * @param departementP le nouveau departement de la commune
     * @throws IllegalArgumentException si le departement est null
     */
    public void setDepartement(Departement departementP) throws IllegalArgumentException {
        if (departementP == null) {
            throw new IllegalArgumentException("Le departement ne peut pas être nul");
        } else {
            this.departement = departementP;
        }
    }

    /**
     * Modifie la liste des communes voisines de la commune avec la liste en parametre, si elle est valide
     * @param communesVoisinesP la nouvelle liste des communes voisines
     * @throws IllegalArgumentException si la liste est null
     */
    public void setCommunesVoisines(ArrayList<Commune> communesVoisinesP) throws IllegalArgumentException {
        if (communesVoisinesP == null){
            throw new IllegalArgumentException("La liste des communes voisines ne peut pas être nulle");
        } else {
            this.communesVoisines = communesVoisinesP;
        }
    }

    /**
     * Modifie la liste des gares de la commune avec la liste en parametre, si elle est valide
     * @param listeP les gares de la commune
     * @throws IllegalArgumentException si la liste de gares est null
     */
    public void setListeGare(ArrayList<Gare> listeP) throws IllegalArgumentException {
        if (listeP == null) {
            throw new IllegalArgumentException("La liste des gares ne peut pas être nulle.");
        } else {
            this.listeGares = listeP;
        }
    }

    /**
     * Methode permettant d'afficher les informations d'une commune.
     * @return les informations d'une commune.
     */
    public String toString() {
        String ret = "Commune " + this.nomCommune + " (id : " + this.idCommune + ")\n- Departement : " + this.departement.getNomDep() + "\n- Communes Voisines : ";
        for (Commune commune : this.communesVoisines) {
            ret += commune.getNomCommune() + "(id : " + commune.getIdCommune() + "), ";
        }
        ret += "\n- Gares : ";
        for (Gare gare : this.listeGares) {
            ret += gare.getNomGare() + "(id : " + gare.getCodeGare() + "), ";
        }
        return ret;
    }

    /**
     * Permet d'ajoute une gare a la liste des gares de la commune
     * @param gareP la nouvelle gare a ajouter
     * @throws IllegalArgumentException si la gare est null
     */
    public void ajouterGare(Gare gareP) throws IllegalArgumentException {
        if (gareP == null) {
            throw new IllegalArgumentException("La gare ne peut pas être nulle");
        } else if (this.listeGares.contains(gareP)) {
            throw new IllegalArgumentException("La gare est deja dans la liste");
        } else {
            this.listeGares.add(gareP);
        }
    }

    /**
     * Methode permettant d'ajouter une commune voisine a la liste des communes voisines
     * @param communeP la commune a ajouter
     * @throws IllegalArgumentException si la commune est null
     * @throws IllegalArgumentException si la commune est deja dans la liste
     * @throws IllegalArgumentException si la commune est la commune elle-même
     */
    public void ajouterCommuneVoisine(Commune communeP) throws IllegalArgumentException {
        if (communeP == null) {
            throw new IllegalArgumentException("La commune ne peut pas être nulle");
        } else if (this.communesVoisines.contains(communeP)) {
            throw new IllegalArgumentException("La commune est deja dans la liste");
        } else if (this.equals(communeP)) {
            throw new IllegalArgumentException("La commune ne peut pas être la commune elle-même");
        } else {
            this.communesVoisines.add(communeP);
        }
    }

    /**
     * Methode permettant de supprimer une commune voisine de la liste des communes voisines
     * @param communeP la commune a supprimer
     * @throws IllegalArgumentException si la commune est nulle
     * @throws IllegalArgumentException si la commune n'est pas dans la liste
     */
    public void supprimerCommuneVoisine(Commune communeP) throws IllegalArgumentException {
        if (communeP == null) {
            throw new IllegalArgumentException("La commune ne peut pas être nulle");
        } else if (!this.communesVoisines.contains(communeP)) {
            throw new IllegalArgumentException("La commune n'est pas dans la liste");
        } else {
            this.communesVoisines.remove(communeP);
        }
    }

    /**
     * Methode permettant de recuperer une commune voisine de la liste des communes voisines
     * @param idCommuneP l'identifiant de la commune a recuperer
     * @return la liste des communes voisines
     * @throws IllegalArgumentException si l'id est negatif
     * @throws IllegalArgumentException si la commune n'est pas dans la liste
     */
    public Commune getUneCommuneVoisine(int idCommuneP) throws IllegalArgumentException {
        Commune c = null;
        boolean trouve = false;
        if (idCommuneP < 0) {
            throw new IllegalArgumentException("L'id ne peut être negatif");
        } 
        if (idCommuneP == this.idCommune) {
            throw new IllegalArgumentException("L'id ne peut être le même que la commune courante");
        } 
        for (Commune commune : this.communesVoisines){
            if (commune.getIdCommune() == idCommuneP){
                c = new Commune(commune.getIdCommune(), commune.getNomCommune(), commune.getDepartement(), commune.getCommunesVoisines(), commune.getListeGares());
                trouve = true;
            }
        }
        if (trouve == false) {
            throw new IllegalArgumentException("Commune voisine non trouve");
        }
        return c;
    }

    /**
     * Trouver les donnees annuelles de la commune courante
     * @param listeDonneesA la liste de donnees annuelles a chercher
     * @return la liste des donnees annuelles de la commune 
     */
    public ArrayList<DonneesAnnuelles> trouverDonneesAnnuelles(ArrayList<DonneesAnnuelles> listeDonneesA){
        ArrayList<DonneesAnnuelles> ret = new ArrayList<DonneesAnnuelles>();
        for (DonneesAnnuelles donnees : listeDonneesA){
            if (donnees.getCommune().getIdCommune() == this.idCommune){
                ret.add(donnees);
            }
        }
        return ret;
    }
}
