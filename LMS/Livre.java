package LMS;
import java.time.LocalDate;

public class Livre {
    private int livreId;
    private String titre;
    private String auteur;
    private String genre;
    private int quantite;
    private boolean estEmprunter;

    public Livre(int livreId, String titre, String auteur, String genre, int quantite, boolean estEmprunter) {
        this.livreId = livreId;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.quantite = quantite;
        this.estEmprunter = estEmprunter;
    }

    public int getLivreId() {
        return livreId;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getGenre() {
        return genre;
    }

    public int getQuantite() {
        return quantite;
    }

    public boolean getEstEmprunter() {
        return estEmprunter;
    }

    public void setLivreId(int newlivreId) {
        this.livreId = newlivreId;
    }

    public void setTitre(String newTitre) {
        this.titre = newTitre;
    }

    public void setAuteur(String newAuteur) {
        this.auteur = newAuteur;
    }

    public void setGenre(String newGenre) {
        this.genre = newGenre;
    }

    public void setQuantite(int newQuantite) {
        this.quantite = newQuantite;
    }

    public void setEstEmprunter(boolean newEstEmprunter) {
        this.estEmprunter = newEstEmprunter;
    }

}