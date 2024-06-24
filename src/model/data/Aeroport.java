package model.data;

import java.util.ArrayList;

/**
 * La classe Aeroport represente un aeroport avec son nom, son adresse et son departement
 */
public class Aeroport {

    /**Le nom de l'Aeroport */
    private String nomAeroport;
    /**L'adresse de l'aeroport */
    private String adresseAeroport;
    /**Le departement de l'aeroport */
    private Departement departement;

    /**
     * Constructeur de la classe Aeroport
     * @param nomAeroportP le nom de l'aeroport
     * @param adresseAeroportP l'adresse de l'aeroport
     * @param departementP le departement de l'aeroport
     */
    public Aeroport(String nomAeroportP, String adresseAeroportP, Departement departementP) throws IllegalArgumentException {
        
        if (nomAeroportP != null && adresseAeroportP != null && departementP != null) {
            this.nomAeroport = nomAeroportP;
            this.adresseAeroport = adresseAeroportP;
            this.departement = departementP;
        } else {
            throw new IllegalArgumentException("Les parametres ne peuvent pas être nuls");
        }
    }

    /**
     * Getter pour le nom de l'aeroport
     * @return le nom de l'aeroport
     */
    public String getNomAeroport(){
        return this.nomAeroport;
    }

    /**
     * Getter pour l'adresse de l'aeroport
     * @return l'adresse de l'aeroport
     */
    public String getAdresseAeroport(){
        return this.adresseAeroport;
    }

    /**
     * Getter pour le departement de l'aeroport
     * @return le departement de l'aeroport
     */
    public Departement getDepartement(){
        return this.departement;
    }

    /**
     * Modifie le nom de l'aeroport avec le nom en parametre, s'il est valide
     * @param nomAeroportP le nouveau nom de l'aeroport
     * @throws IllegalArgumentException si le nom est null
     */
    public void setNomAeroport(String nomAeroportP) throws IllegalArgumentException {
        if (nomAeroportP == null){
            throw new IllegalArgumentException("Le nom ne peut pas être nul");
        }
        else{
            this.nomAeroport = nomAeroportP;
        }
    }

    /**
     * Modifie l'adresse de l'aeroport avec l'adresse en parametre, si elle est valide
     * @param adresseAeroportP la nouvelle adresse de l'aeroport
     * @throws IllegalArgumentException si l'adresse est null
     */
    public void setAdresseAeroport(String adresseAeroportP) throws IllegalArgumentException {
        if (adresseAeroportP == null){
            throw new IllegalArgumentException("L'adresse ne peut pas être nulle");
        } else {
            this.adresseAeroport = adresseAeroportP;
        }
    }

    /**
     * Modifie le departement de l'aeroport avec le departement en parametre, s'il est valide
     * @param departementP le nouveau departement de l'aeroport
     * @throws IllegalArgumentException si le departement est null
     */
    public void setDepartement(Departement departementP) throws IllegalArgumentException {
        if (departementP == null){
            throw new IllegalArgumentException("Le departement ne peut pas être nul");
        } else {
            this.departement = departementP;
        }
    }

    /**
     * Methode permettant d'afficher un aeroport
     * @return une chaine de caracteres representant l'aeroport
     */
    public String toString(){
        return "Aeroport " + this.nomAeroport + " :\n- Adresse : " + this.adresseAeroport + " (" + this.departement.getNomDep() + ")";
    }
    
    /**
     * Methode permettant d'obtenir la liste des communes du departement de cet aeroport.
     * @return une liste des communes du departement
     */
    public ArrayList<Commune> obtenirCommunesDepartement() {
        return this.departement.getListeCommunes();
    }
}
