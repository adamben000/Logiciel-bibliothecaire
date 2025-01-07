package LMS;

import java.util.*;
import java.io.*;
import java.io.IOException;

public class Database {
    private final File fichierLivres = new File("db/livres.txt");
    private final File fichierUtilisateurs = new File("db/utilisateurs.txt");
    private final File fichierEmprunts = new File("db/emprunts.txt");

    private void verifierFichier(File fichier){
        if (!fichier.isFile()) {
            try{
                if(fichier.createNewFile()){
                    System.out.println("Fichier créé");
                }
            } catch (IOException exception){
                System.err.println("Erreur lors de la création du fichier : " + fichier.getName());
                exception.printStackTrace();
            }
        }
    }

    public boolean verifierAdmin(String nomUtilisateur, String motDePasse){
        return nomUtilisateur.equals("admin") && motDePasse.equals("7425");
    }

    public boolean utilisateurExisteSansMotDePasse(String nomUtilisateur) throws IOException {
        verifierFichier(fichierUtilisateurs);
        try (Scanner lecteur = new Scanner(fichierUtilisateurs)){
            while (lecteur.hasNextLine()){
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()){
                    continue;
                }

                String[] utilisateurs = ligne.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(nomUtilisateur)){
                    return true;
                }
            }
            return false;
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return false;
    }

    public boolean utilisateurExiste(String nomUtilisateur, String motDePasse) throws IOException {
        verifierFichier(fichierUtilisateurs);
        try (Scanner lecteur = new Scanner(fichierUtilisateurs)){
            while (lecteur.hasNextLine()){
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()){
                    continue;
                }

                String[] utilisateurs = ligne.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(nomUtilisateur) && utilisateurs[1].trim().equals(motDePasse)){
                    return true;
                }
            }
            return false;
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return false;
    }

    public boolean verifierNomUtilisateur(String nomUtilisateur) throws IOException {
        verifierFichier(fichierUtilisateurs);
        if (nomUtilisateur.equals("admin")){
            return false;
        }
        try (Scanner lecteur = new Scanner(fichierUtilisateurs)){
            while (lecteur.hasNextLine()){
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()){
                    continue;
                }

                String[] utilisateurs = ligne.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(nomUtilisateur)){
                    return false;
                }
            }
            return true;
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return false;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) throws IOException {
        verifierFichier(fichierUtilisateurs);
        boolean utilisateurExistant = false;
        List<String> lignesUtilisateurs = new ArrayList<>();
        try (Scanner lecteur = new Scanner(fichierUtilisateurs)){
            while (lecteur.hasNextLine()){
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()){
                    continue;
                }

                String[] utilisateurs = ligne.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(utilisateur.getNomUtilisateur())){
                    String ligneActualisee = utilisateur.getNomUtilisateur() + "," + utilisateur.getMotDePasse();
                    lignesUtilisateurs.add(ligneActualisee);
                    utilisateurExistant = true;
                } else {
                    lignesUtilisateurs.add(ligne);
                }
            }

            if (!utilisateurExistant){
                String nouvelleLigne = utilisateur.getNomUtilisateur() + "," + utilisateur.getMotDePasse();
                lignesUtilisateurs.add(nouvelleLigne);
            }
        } catch (IOException exception){
            exception.printStackTrace();
        }

        try{
            ecrireDansFichier(lignesUtilisateurs, fichierUtilisateurs);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void supprimerUtilisateur(String nomUtilisateur){
        verifierFichier(fichierUtilisateurs);
        List<String> lignesUtilisateurs = new ArrayList<>();
        try (Scanner lecteur = new Scanner(fichierUtilisateurs)){
            while (lecteur.hasNextLine()){
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()){
                    continue;
                }

                String[] utilisateurs = ligne.split(",");
                if (utilisateurs.length > 0 && !(utilisateurs[0].trim().equals(nomUtilisateur))){
                    lignesUtilisateurs.add(ligne);
                }
            }
        } catch (IOException exception){
            exception.printStackTrace();
        }

        try{
            ecrireDansFichier(lignesUtilisateurs, fichierUtilisateurs);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public void changerUtilisateur(String ancienUtilisateur, String nouveauUtilisateur) throws IOException {
        verifierFichier(fichierUtilisateurs);
        verifierFichier(fichierEmprunts);

        // Vérifier si le nouveau nom n'est pas déjà utilisé (sauf si c'est le même utilisateur)
        if (!ancienUtilisateur.equals(nouveauUtilisateur) && utilisateurExisteSansMotDePasse(nouveauUtilisateur)) {
            throw new IOException("Ce nom d'utilisateur existe déjà");
        }

        List<String> lignesUtilisateurs = new ArrayList<>();
        String motDePasseActuel = null;

        try (Scanner lecteur = new Scanner(fichierUtilisateurs)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()) continue;

                String[] utilisateurs = ligne.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(ancienUtilisateur)) {
                    motDePasseActuel = utilisateurs[1].trim();
                    lignesUtilisateurs.add(nouveauUtilisateur + "," + motDePasseActuel);
                } else {
                    lignesUtilisateurs.add(ligne);
                }
            }
        }

        List<String> lignesEmprunts = new ArrayList<>();
        boolean empruntTrouve = false;

        try (Scanner lecteur = new Scanner(fichierEmprunts)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()) continue;

                String[] donnees = ligne.split(",");
                if (donnees[0].trim().equals(ancienUtilisateur)) {
                    empruntTrouve = true;
                    lignesEmprunts.add(nouveauUtilisateur + "," +
                            donnees[1] + "," +
                            donnees[2] + "," +
                            donnees[3] + "," +
                            donnees[4]);
                } else {
                    lignesEmprunts.add(ligne);
                }
            }
        }

        ecrireDansFichier(lignesUtilisateurs, fichierUtilisateurs);
        if (empruntTrouve) {
            ecrireDansFichier(lignesEmprunts, fichierEmprunts);
        }
    }
    public boolean changerMotDePasse(String utilisateur, String ancienMotDePasse, String nouveauMotDePasse){
        verifierFichier(fichierUtilisateurs);
        List<String> lignesUtilisateurs = new ArrayList<>();
        try (Scanner lecteur = new Scanner(fichierUtilisateurs)){
            while (lecteur.hasNextLine()){
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()){
                    continue;
                }

                String[] utilisateurs = ligne.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(utilisateur)){
                    if (utilisateurs[1].equals(ancienMotDePasse)){
                        String ligneActualisee = utilisateurs[0] + "," + nouveauMotDePasse;
                        lignesUtilisateurs.add(ligneActualisee);
                    } else {
                        return false;
                    }
                } else {
                    lignesUtilisateurs.add(ligne);
                }
            }
        } catch (IOException exception){
            exception.printStackTrace();
        }

        try{
            ecrireDansFichier(lignesUtilisateurs, fichierUtilisateurs);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return true;
    }
    public void ajouterLivre(Livre livre) throws IOException {
        verifierFichier(fichierLivres);
        if (livre.getQuantite() < 0) {
            throw new IllegalArgumentException("La quantité de livres ne peut pas être négative");
        }

        String titreNormalise = livre.getTitre().trim().toLowerCase();
        List<String> lignesLivres = new ArrayList<>();
        boolean livreExistant = false;

        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()) {
                    continue;
                }

                String[] livres = ligne.split(",");
                String titreExistantNormalise = livres[0].trim().toLowerCase();

                if (titreExistantNormalise.equals(titreNormalise)) {
                    livreExistant = true;
                    break;
                }
                lignesLivres.add(ligne);
            }
        }

        if (livreExistant) {
            throw new IOException("Un livre avec ce titre existe déjà");
        }
        String nouvelleLigne = livre.getTitre().trim() + "," + livre.getAuteur().trim() + "," + livre.getGenre().trim() + "," + livre.getQuantite() + "," + livre.getQuantite() + "," + livre.getLivreId();
        lignesLivres.add(nouvelleLigne);

        ecrireDansFichier(lignesLivres, fichierLivres);
    }

    public void supprimerLivre(String idLivre) throws IOException {
        verifierFichier(fichierLivres);
        List<String> lignesLivres = new ArrayList<>();

        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()) {
                    continue;
                }

                String[] livres = ligne.split(",");
                if (livres.length > 0 && !(livres[5].equals(idLivre))) {
                    lignesLivres.add(ligne);
                }
            }
        }
        try {
            ecrireDansFichier(lignesLivres, fichierLivres);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean aEmprunt(String nomUtilisateur) {
        verifierFichier(fichierEmprunts);
        try (Scanner lecteur = new Scanner(fichierEmprunts)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (!ligne.isEmpty()) {
                    String[] donnees = ligne.split(",");
                    if (donnees[0].trim().equals(nomUtilisateur)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean emprunterLivre(String nomUtilisateur, String idLivre) throws IOException {
        verifierFichier(fichierEmprunts);

        if (!livreExiste(idLivre) || !utilisateurExisteSansMotDePasse(nomUtilisateur)) {
            return false;
        }

        try (Scanner lecteur = new Scanner(fichierEmprunts)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (!ligne.isEmpty()) {
                    String[] donnees = ligne.split(",");
                    if (donnees[0].trim().equals(nomUtilisateur)) {
                        return false;
                    }
                }
            }
        }

        int copiesDisponibles = 0;
        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (!ligne.isEmpty()) {
                    String[] donnees = ligne.split(",");
                    if (donnees[5].trim().equals(idLivre)) {
                        copiesDisponibles = Integer.parseInt(donnees[4].trim());
                        break;
                    }
                }
            }
        }

        if (copiesDisponibles <= 0) {
            return false;
        }

        mettreAJourCopiesDisponibles(idLivre, -1);

        java.time.LocalDate dateEmprunt = java.time.LocalDate.now();
        java.time.LocalDate dateRetour = dateEmprunt.plusDays(14);

        String nouvelEmprunt = nomUtilisateur.trim() + "," + obtenirTitreLivre(idLivre).trim() + "," + idLivre.trim() + "," + dateEmprunt + "," + dateRetour;

        List<String> lignesEmprunts = new ArrayList<>();
        try (Scanner lecteur = new Scanner(fichierEmprunts)) {
            while (lecteur.hasNextLine()) {
                lignesEmprunts.add(lecteur.nextLine());
            }
        }
        lignesEmprunts.add(nouvelEmprunt);
        ecrireDansFichier(lignesEmprunts, fichierEmprunts);

        return true;
    }

    public void mettreAJourEmprunt(String nomUtilisateur, String nouveauNomUtilisateur) throws IOException {
        verifierFichier(fichierEmprunts);
        List<String> lignesEmprunts = new ArrayList<>();
        boolean utilisateurTrouve = false;

        try (Scanner lecteur = new Scanner(fichierEmprunts)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()) {
                    continue;
                }
                String[] donnees = ligne.split(",");

                if (!donnees[0].equals(nomUtilisateur)) {
                    lignesEmprunts.add(ligne);
                } else {
                    utilisateurTrouve = true;
                    String ligneActualisee = nouveauNomUtilisateur + "," + donnees[1] + "," + donnees[2] + "," + donnees[3] + "," + donnees[4];
                    lignesEmprunts.add(ligneActualisee);
                }
            }

            if (utilisateurTrouve) {
                ecrireDansFichier(lignesEmprunts, fichierEmprunts);
            }
        }
    }

    public int enRetard(String nomUtilisateur) throws IOException {
        verifierFichier(fichierEmprunts);

        try (Scanner lecteur = new Scanner(fichierEmprunts)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()) {
                    continue;
                }
                String[] donnees = ligne.split(",");

                if (donnees[0].equals(nomUtilisateur)) {
                    java.time.LocalDate dateRetour = java.time.LocalDate.parse(donnees[4]);
                    java.time.LocalDate dateActuelle = java.time.LocalDate.now();
                    if (dateActuelle.isAfter(dateRetour)) {
                        return (int) java.time.temporal.ChronoUnit.DAYS.between(dateRetour, dateActuelle);
                    }
                    return 0;
                }
            }
        }
        return 0;
    }

    public boolean retournerLivre(String nomUtilisateur) throws IOException {
        verifierFichier(fichierEmprunts);
        List<String> lignesEmprunts = new ArrayList<>();
        boolean livreTrouve = false;
        String idLivre = null;

        try (Scanner lecteur = new Scanner(fichierEmprunts)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()) {
                    continue;
                }
                String[] donnees = ligne.split(",");

                if (!donnees[0].equals(nomUtilisateur)) {
                    lignesEmprunts.add(ligne);
                } else {
                    livreTrouve = true;
                    idLivre = donnees[2];
                    java.time.LocalDate dateRetour = java.time.LocalDate.parse(donnees[4]);
                    if (java.time.LocalDate.now().isAfter(dateRetour)) {
                        System.out.println("Livre rendu en retard : " + donnees[1]);
                    }
                }
            }
        }

        if (livreTrouve && idLivre != null) {
            ecrireDansFichier(lignesEmprunts, fichierEmprunts);
            mettreAJourCopiesDisponibles(idLivre, 1);
            return true;
        }
        return false;
    }

    private String obtenirTitreLivre(String idLivre) throws IOException {
        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine();
                if (!ligne.isEmpty()) {
                    String[] donnees = ligne.split(",");
                    if (donnees[5].trim().equals(idLivre)) {
                        return donnees[0].trim();
                    }
                }
            }
        }
        throw new IOException("Livre non trouvé !");
    }

    public String[] detecterEmprunt(String nomUtilisateur) {
        try (BufferedReader reader = new BufferedReader(new FileReader("db/emprunts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] empruntInfo = line.split(",");
                if (empruntInfo[0].equals(nomUtilisateur)) {
                    return empruntInfo;
                }
            }
            return new String[]{"PAS_D_EMPRUNT"};
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur: Fichier introuvable.");
        }
        return null;
    }

    public void mettreAJourQuantiteLivre(String idLivre, int changementQuantite) throws IOException {
        verifierFichier(fichierLivres);
        List<String> lignesLivres = new ArrayList<>();
        boolean livreTrouve = false;

        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()) {
                    continue;
                }

                String[] livres = ligne.split(",");
                if (livres.length > 0 && livres[5].trim().equals(idLivre)) {
                    livreTrouve = true;
                    int totalActuel = Integer.parseInt(livres[3].trim());
                    int disponiblesActuels = Integer.parseInt(livres[4].trim());
                    int nouveauTotal = totalActuel + changementQuantite;
                    int nouveauDisponible = disponiblesActuels + changementQuantite;

                    if (nouveauTotal < 0 || nouveauDisponible < 0) {
                        throw new IllegalArgumentException("Impossible de réduire la quantité en dessous de 0");
                    }
                    if (nouveauDisponible > nouveauTotal) {
                        throw new IllegalArgumentException("Les copies disponibles ne peuvent pas dépasser le total");
                    }

                    String ligneActualisee = livres[0] + "," + livres[1] + "," + livres[2] + "," + nouveauTotal + "," + nouveauDisponible + "," + idLivre;
                    lignesLivres.add(ligneActualisee);
                } else {
                    lignesLivres.add(ligne);
                }
            }
        }

        if (!livreTrouve) {
            throw new IOException("Livre avec l'ID " + idLivre + " non trouvé");
        }

        ecrireDansFichier(lignesLivres, fichierLivres);
    }

    private void mettreAJourCopiesDisponibles(String idLivre, int nombre) {
        verifierFichier(fichierLivres);
        List<String> lignesLivres = new ArrayList<>();
        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (ligne.isEmpty()) {
                    continue;
                }

                String[] livres = ligne.split(",");
                if (livres.length > 0 && livres[5].trim().equals(idLivre)) {
                    int totalCopies = Integer.parseInt(livres[3].trim());
                    int disponibles = Integer.parseInt(livres[4].trim()) + nombre;

                    if (disponibles < 0 || disponibles > totalCopies) {
                        throw new IllegalStateException("Mise à jour invalide de la quantité de livres");
                    }

                    String ligneActualisee = livres[0] + "," + livres[1] + "," + livres[2] + "," + totalCopies + "," + disponibles + "," + idLivre;
                    lignesLivres.add(ligneActualisee);
                } else {
                    lignesLivres.add(ligne);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        try {
            ecrireDansFichier(lignesLivres, fichierLivres);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean supprimerLivreDefinitivementVerification(String idLivre) throws IOException {
        verifierFichier(fichierLivres);
        verifierFichier(fichierEmprunts);

        if (!livreExiste(idLivre)) {
            return false;
        }

        boolean quantiteNulle = false;
        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (!ligne.isEmpty()) {
                    String[] donnees = ligne.split(",");
                    if (donnees[5].trim().equals(idLivre)) {
                        int quantiteTotale = Integer.parseInt(donnees[3].trim());
                        int quantiteDisponible = Integer.parseInt(donnees[4].trim());
                        quantiteNulle = (quantiteTotale == 0 && quantiteDisponible == 0);
                        break;
                    }
                }
            }
        }

        if (!quantiteNulle) {
            return false;
        }

        try (Scanner lecteur = new Scanner(fichierEmprunts)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (!ligne.isEmpty()) {
                    String[] donnees = ligne.split(",");
                    if (donnees[2].trim().equals(idLivre)) {
                        return false;
                    }
                }

            }
        }

        return true;
    }

    public boolean livreDisponible(String idLivre, int quantiteRequise) throws IOException {
        if (quantiteRequise <= 0) {
            throw new IllegalArgumentException("La quantité demandée doit être positive");
        }

        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine().trim();
                if (!ligne.isEmpty()) {
                    String[] donnees = ligne.split(",");
                    if (donnees[5].trim().equals(idLivre)) {
                        int disponibles = Integer.parseInt(donnees[4].trim());
                        return disponibles >= quantiteRequise;
                    }
                }
            }
        }
        return false;
    }

    public boolean livreExiste(Livre livre) {
        verifierFichier(fichierLivres);
        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String donnee = lecteur.nextLine().trim();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");

                String titreLivre = livres[0].replaceAll("\\s", "").toLowerCase();
                String titreDonne = livre.getTitre().replaceAll("\\s", "").toLowerCase();

                if (titreLivre.equals(titreDonne)) {
                    return true;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean livreExiste(String idLivre) {
        verifierFichier(fichierLivres);
        try (Scanner lecteur = new Scanner(fichierLivres)) {
            while (lecteur.hasNextLine()) {
                String donnee = lecteur.nextLine().trim();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");
                if (livres.length > 0 && livres[5].trim().equals(idLivre)) {
                    return true;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private void ecrireDansFichier(List<String> lignes, File fichier) throws IOException {
        try (PrintWriter ecrivain = new PrintWriter(new FileWriter(fichier))) {
            for (String ligne : lignes) {
                ecrivain.println(ligne);
            }
        }
    }
}
