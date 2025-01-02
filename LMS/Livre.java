package LMS;
import java.util.HashSet;
import java.util.Random;

public class Livre {
    private static final HashSet<Integer> idsUtiliser = new HashSet<>();
    private static final Random random = new Random();
    private static final int maxId = 2000;
    private int livreId;
    private String titre;
    private String auteur;
    private String genre;
    private int quantite;

    public Livre(String titre, String auteur, String genre, int quantite) {
        this.livreId = genererId();
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.quantite = quantite;
    }
    private int genererId() {
        int id = random.nextInt(maxId) + 1;
        while (idsUtiliser.contains(id)) {
            id = random.nextInt(maxId) + 1;
        }
        idsUtiliser.add(id);
        return id;
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
}