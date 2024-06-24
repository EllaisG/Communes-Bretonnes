package model.data;

import java.util.ArrayList;

/**
 * La classe Departement represente un departement tel que son identifiant, son nom, etc
 */
public class Departement{

    /**L'identifiant du departement */
    private int idDep;
    /**Le nom du departement */
    private String nomDep;
    /**L'investissement culturel de 2019 du departement */
    private double invesCulturel2019;
    /**La liste de communes du departement */
    private ArrayList<Commune> listeCommunes;
    /**Liste des noms de departements valides */
    private static final ArrayList<String> LISTEDEP = new ArrayList<String>();
    
    /**
     * Constructeur de la classe Departement
     * @param idDepP l'identifiant du departement
     * @param nomDepP le nom du departement
     * @param invesCulturel2019P l'investissement culturel en 2019
     * @param listeCommunesP la liste des communes du departement
     * @throws IllegalArgumentException si l'id est negatif, si le nom est nul, si l'investissement est negatif ou si la liste de communes est nulle
     */
    public Departement(int idDepP, String nomDepP, double invesCulturel2019P, ArrayList<Commune> listeCommunesP) throws IllegalArgumentException {
        LISTEDEP.add("MORBIHAN");
        LISTEDEP.add("ILLE-ET-VILAINE");
        LISTEDEP.add("COTES-D-ARMOR");
        LISTEDEP.add("FINISTERE");
        
        if (idDepP >= 0 && nomDepP != null && invesCulturel2019P >= 0) {
            if (LISTEDEP.contains(nomDepP.toUpperCase())){
                this.nomDep = nomDepP;
            } else {
                throw new IllegalArgumentException("Le nom du departement doit être dans les departement de Bretagne");
            }
            this.idDep = idDepP;
            this.invesCulturel2019 = invesCulturel2019P;
            if (listeCommunesP != null){
                this.listeCommunes = listeCommunesP;
            } else {
                this.listeCommunes = new ArrayList<Commune>();
            }
        } else {
            throw new IllegalArgumentException("Les parametres ne peuvent pas être nuls ou negatifs");
        }
    }

    /**
     * Getter pour l'identifiant du departement
     * @return l'identifiant du departement
     */
    public int getIdDep () {
        return this.idDep;
    }

    /**
     * Getter pour le nom du departement
     * @return le nom du departement
     */
    public String getNomDep(){
        return this.nomDep;
    }

    /**
     * Getter pour l'investissement culturel en 2019
     * @return l'investissement culturel en 2019
     */
    public double getInvesCulturel2019(){
        return this.invesCulturel2019;
    }

    /**
     * Getter pour la liste des communes du departement
     * @return la liste des communes du departement
     */
    public ArrayList<Commune> getListeCommunes(){
        return this.listeCommunes;
    }

    /**
     * Getter pour la liste des identifiants des communes voisines
     * @return la liste des identifiants des communes voisines
     */
    public ArrayList<Integer> getIdListeCommunes(){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (Commune commune : this.listeCommunes){
            ret.add(commune.getIdCommune());
        }
        return ret;
    }

    /**
     * Modifie l'identifiant du departement avec l'identifiant en parametre, s'il est valide
     * @param idDepP le nouvel identifiant du departement
     * @throws IllegalArgumentException si l'id est negatif
     */
    public void setIdDep(int idDepP) throws IllegalArgumentException {
        if (idDepP < 0){
            throw new IllegalArgumentException("L'id ne peut pas être negatif");
        } else {
            this.idDep = idDepP;
        }
    }

    /**
     * Modifie le nom du departement avec le nom en parametre, s'il est valide
     * @param nomDepP le nouveau nom du departement
     * @throws IllegalArgumentException si le nom est nul
     */
    public void setNomDep(String nomDepP) throws IllegalArgumentException {
        if (nomDepP == null){
            throw new IllegalArgumentException("Le nom ne peut pas être nul");
        } else {
            this.nomDep = nomDepP;
        }
    }

    /**
     * Modifie l'investissement culturel en 2019 du departement avec l'investissement en parametre, s'il est valide
     * @param invesCulturel2019P le nouvel investissement culturel en 2019
     * @throws IllegalArgumentException si l'investissement est negatif
     */
    public void setInvesCulturel2019(double invesCulturel2019P) throws IllegalArgumentException {
        if (invesCulturel2019P < 0){
            throw new IllegalArgumentException("L'investissement ne peut pas être negatif");
        } else {
            this.invesCulturel2019 = invesCulturel2019P;
        }
    }

    /**
     * Modifie la liste des commune du departement avec la liste en parametre, si elle est valide
     * @param listeCommunesP la nouvelle liste des communes du departement
     * @throws IllegalArgumentException si la liste de communes est nulle
     */
    public void setListeCommunes(ArrayList<Commune> listeCommunesP) throws IllegalArgumentException {
        if (listeCommunesP == null){
            throw new IllegalArgumentException("La liste de communes ne peut pas être nulle");
        } else {
            this.listeCommunes = listeCommunesP;
        }
    }
    
    /**
     * Methode permettant d'afficher les informations du departement.
     * @return les informations du departement
     */
    public String toString() {
        String ret = "Departement " + this.nomDep + " : " + this.invesCulturel2019 + "€ d'investissement en 2019";
        for (Commune c : this.listeCommunes){
            ret += "Commune " + c.getNomCommune() + " (id : " + c.getIdCommune() + ")";
        }
        return ret;
    }

    /**
     * Methode permettant d'ajouter une commune a la liste des communes du departement
     * @param communeP la commune a ajouter
     * @throws IllegalArgumentException si la commune est nulle
     */
    public void ajouterUneCommune(Commune communeP) throws IllegalArgumentException {
        if (communeP == null){
            throw new IllegalArgumentException("La commune ne peut pas être nulle");
        } else {
            this.listeCommunes.add(communeP);
        }
    }

    /**
     * Methode permettant de supprimer une commune de la liste des communes du departement
     * @param communeP la commune a supprimer
     * @throws IllegalArgumentException si la commune est nulle
     */
    public void supprimerUneCommune(Commune communeP) throws IllegalArgumentException {
        if (communeP == null){
            throw new IllegalArgumentException("La commune ne peut pas être nulle");
        } else {
            this.listeCommunes.remove(communeP);
        }
    }    

    /**
     * Methode permettant de recuperer une commune de la liste des communes du departement
     * @param idCommuneP l'identifiant de la commune a recuperer
     * @return la commune correspondante
     * @throws IllegalArgumentException si l'id est negatif
     */
    public Commune getUneCommune(int idCommuneP) throws IllegalArgumentException {
        Commune c = null;
        if (idCommuneP < 0){
            throw new IllegalArgumentException("L'id ne peut pas être negatif");
        } else {
            for (Commune commune : this.listeCommunes){
                if (commune.getIdCommune() == idCommuneP){
                    c = commune;
                }
            }
        }
        return c;
    }

    /**
     * Methode permettant de trouver une commune grâce a son nom.
     * @param nomCommuneP le nom de la commune a rechercher
     * @return la commune correspondante ou null si elle n'existe pas
     * @throws IllegalArgumentException si le nom de la commune est null
     */
    public Commune trouverCommuneParNom(String nomCommuneP) throws IllegalArgumentException {
        if (nomCommuneP == null) {
            throw new IllegalArgumentException("Le nom de la commune ne peut pas être nul");
        }
        Commune c = null;
        for (Commune commune : this.listeCommunes) {
            if (commune.getNomCommune().toUpperCase().equals(nomCommuneP.toUpperCase())) {
                c = new Commune(commune.getIdCommune(), commune.getNomCommune(), commune.getDepartement(), commune.getCommunesVoisines(), commune.getListeGares());
            }
        }
        return c;
    }
}
