package LMS;

public class Utilisateur {
    private String utilisateur;
    private String motDePasse;

    public Utilisateur(String username, String motDePasse){
            this.utilisateur = username;
            this.motDePasse = motDePasse;
    }
    public String getNomUtilisateur(){
        return utilisateur;
    }

    public String getMotDePasse(){
        return motDePasse;
    }

    public void setNomUtilisateur(String nouveauNomUtilisateur){
        this.utilisateur = nouveauNomUtilisateur;
    }

    public void setMotDePasse(String nouveauMotDePasse){
        this.motDePasse = nouveauMotDePasse;
    }
}
