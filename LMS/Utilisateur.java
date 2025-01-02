package LMS;

public class Utilisateur {
    private String username;
    private String motDePasse;

    public Utilisateur(String username, String motDePasse){
            this.username = username;
            this.motDePasse = motDePasse;
    }
    public String getUsername(){
        return username;
    }

    public String getMotDePasse(){
        return motDePasse;
    }

    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public void setMotDePasse(String newMotDePasse){
        this.motDePasse = newMotDePasse;
    }
}
