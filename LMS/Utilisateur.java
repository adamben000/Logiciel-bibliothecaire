package LMS;
import GUI.RegistreGUI;

import java.util.*;

public class Utilisateur {
    private String username;
    private String motDePasse;
    private List<String> livresEmpruntes ;

    public Utilisateur(String username, String motDePasse, List<String> livresEmpruntes){
            this.username = username;
            this.motDePasse = motDePasse;
            this.livresEmpruntes = livresEmpruntes;
    }
    public String getUsername(){
        return username;
    }

    public String getMotDePasse(){
        return motDePasse;
    }

    public List<String> getLivresEmpruntes(){
        return livresEmpruntes;
    }

    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public void setMotDePasse(String newMotDePasse){
        this.motDePasse = newMotDePasse;
    }

    public void setLivresEmpruntes(List<String> newLivresEmpruntes){
        this.livresEmpruntes = newLivresEmpruntes;
        }
}
