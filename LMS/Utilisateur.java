package LMS;
import java.util.*;

public class Utilisateur {
    private int userId;
    private String username;
    private String motDePasse;
    private List<String> livresEmpruntes ;

    public Utilisateur(int userId, String username, String motDePasse, List<String> livresEmpruntes){
            this.userId = userId;
            this.username = username;
            this.motDePasse = motDePasse;
            this.livresEmpruntes = livresEmpruntes;
    }
    public int getUserId(){
        return userId;
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

    public void setUserId(int newUserId){
        this.userId = newUserId;
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
