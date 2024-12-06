package LMS;
import java.util.*;

public class Utilisateur {
    private int userId;
    private String username;
    private String motDePasse;
    private List livresEmpruntes;

    public Utilisateur(int userId, String username, String motDePasse, List livresEmpruntes){
            this.userId = userId;
            this.username = username;
            this.motDePasse = motDePasse;
            this.livresEmpruntes = livresEmpruntes;
    }
    public int getuserId(){
        return userId;
    }
    public String getusername(){
        return username;
    }
    public String getmotDePasse(){
        return motDePasse;
    }
    public List getlivresEmpruntes(){
        return livresEmpruntes;
    }
    public void setuserId(int newUserId){
        this.userId = newUserId;
    }
    public void setUsername(String newUsername){
        this.username = newUsername;
    }
    public void setMotDePasse(String newMotDePasse){
        this.motDePasse = newMotDePasse;
    }
    public void setLivresEmpruntes(List newLivresEmpruntes){
        this.livresEmpruntes = newLivresEmpruntes;
        }
}
