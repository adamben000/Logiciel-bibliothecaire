public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String genre;
    private boolean estEmprunter;

    public Livre(int id, String titre, String auteur, String genre, boolean estEmprunter){
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.genre = genre;
        this.estEmprunter = estEmprunter;
    }
    public int getId(){
        return id;
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
    public boolean getEstEmprunter(){
        return estEmprunter;
    }
    public void setId(int newId){
        this.id = newId;
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
    public void setEstEmprunter(boolean newEstEmprunter){
        this.estEmprunter = newEstEmprunter;
    }
}
