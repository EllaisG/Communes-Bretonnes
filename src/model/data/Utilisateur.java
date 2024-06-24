package model.data;

/**
 * La classe utilisateur representant un utilisateur de l'application avec son identifiant et son mot de passe
 */
public class Utilisateur {

    /**L'identifiant de l'utilisateur */
    private String identifiant;
    /**Le mdp de l'utilisateur */
    private String mdp;

    /**
     * Constructeur de la classe Utilisateur
     * @param identifiantP l'identifiant
     * @param mdpP le mot de passe
     */
    public Utilisateur(String identifiantP , String mdpP) throws IllegalArgumentException{
        if (identifiantP != null && mdpP != null){
            this.identifiant = identifiantP;
            this.mdp = mdpP;
        } else {
            throw new IllegalArgumentException("Les parametres ne peuvent pas être nuls");
        }
    }
    
    /**
     * Retourne l'identifiant de l'utilisateur
     * @return l'identifiant de l'utilisateur
     */
    public String getIdentifiant () {
        return this.identifiant;
    }
    
    /**
     * Retourne le mot de passe de l'utilisateur
     * @return le mot de passe de l'utilisateur
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Modifie l'identifiant de l'utilisateur avec l'identifiant en parametre, s'il est valide
     * @param identifiantP le nouvel identifiant
     */
    public void setIdentifiant(String identifiantP) {
        this.identifiant = identifiantP;
    }

    /**
     * Methode permettant d'afficher les informations de l'utilisateur
     * @return les informations de l'utilisateur
     */
    public String toString(){
        return "Identifiant: "+this.identifiant+"\n - Mot de pas: "+this.mdp;
    }

    /**
     * Methode qui permet de changer le mot de passe
     * @param ancienMdp l'ancien mot de passe 
     * @param nouveauMdp le nouveau mot de passe 
     */
    public void nouveauMdp(String ancienMdp, String nouveauMdp) throws IllegalArgumentException {
        if (ancienMdp.equals(this.mdp)){
            if (nouveauMdp == null){
                throw new IllegalArgumentException("Mot de passe null, reessayer");
            } else if(ancienMdp.trim().isEmpty() || nouveauMdp.trim().isEmpty()){
                    throw new IllegalArgumentException("Mot de passe vide, reessayer");
            } else {
                this.mdp = nouveauMdp;
            }
        } else {
            throw new IllegalArgumentException("Ancien mot de passe incorrect");
        }
    }
}
