package LMS;
import java.util.*;
import java.io.*;

public class Livre {
    private int livreId;
    private String titre;
    private String auteur;
    private String genre;
    private int quantite;
    private boolean estEmprunter;
    private Scanner sc = new Scanner(System.in);

    public Livre(){
        this.livreId = -1;
        this.titre = " ";
        this.auteur = " ";
        this.genre = " ";
        this.quantite = 0;
        this.estEmprunter = false;
    }
    public Livre(int livreId, String titre, String auteur, String genre, int quantite, boolean estEmprunter){
        this.livreId = livreId;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.quantite = quantite;
        this.estEmprunter = estEmprunter;
    }
    public int getlivreId(){
        return livreId;
    }
    public String getTitre(){
        return titre;
    }
    public String getAuteur(){
        return auteur;
    }
    public String getGenre(){
        return genre;
    }
    public int getQuantite(){
        return quantite;
    }
    public boolean getEstEmprunter(){
        return estEmprunter;
    }
    public void setlivreId(int newlivreId){
        this.livreId = newlivreId;
    }
    public void setTitre(String newTitre){
        this.titre = newTitre;
    }
    public void setAuteur(String newAuteur){
        this.auteur = newAuteur;
    }
    public void setGenre(String newGenre){
        this.genre = newGenre;
    }
    public void setQuantite(int newQuantite){
        this.quantite = newQuantite;
    }
    public void setEstEmprunter(boolean newEstEmprunter){
        this.estEmprunter = newEstEmprunter;
    }
    public void actuliserInfo(int livreId, String titre, String auteur, String genre, boolean estEmprunter){
        try {
            File f = new File("dbt.txt");
            PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
            pw.append("\n "+livreId+","+titre+","+auteur+","+genre+","+estEmprunter);
            pw.close();
        } catch(IOException e){

        }
    }
}