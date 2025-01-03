package LMS;

import java.util.*;
import java.io.*;
import java.io.IOException;

public class Database {
    private final File livresFichier = new File("db/livres.txt");
    private final File utilisateursFichier = new File("db/utilisateurs.txt");
    private final File empruntsFichier = new File ("db/emprunts.txt");

    private void fichierExiste(File fichier){
        if (!fichier.isFile()) {
            try{
                if(fichier.createNewFile()){
                    System.out.println("Fichier cree");
                }
            } catch (IOException e){
                System.err.println("Erreur lors de la création du fichier : " + fichier.getName());
                e.printStackTrace();
            }
        }
    }
    public boolean checkAdmin(String utilisateur, String motDePasse){
        return utilisateur.equals("admin") && motDePasse.equals("7425");
    }
    public boolean utilisateurExisteSansPass (String utilisateur) throws IOException {
        fichierExiste(utilisateursFichier);
        try (Scanner sc = new Scanner(utilisateursFichier)){
            while (sc.hasNextLine()){
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()){
                    continue;
                }

                String[] utilisateurs = donnee.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(utilisateur)){
                    return true;
                }
            }
            return false;
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean utilisateurExiste (String utilisateur, String motDePasse) throws IOException {
        fichierExiste(utilisateursFichier);
        try (Scanner sc = new Scanner(utilisateursFichier)){
            while (sc.hasNextLine()){
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()){
                    continue;
                }

                String[] utilisateurs = donnee.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(utilisateur) && utilisateurs[1].trim().equals(motDePasse) ){
                    return true;
                }
            }
            return false;
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkUtilisateur(String utilisateur) throws IOException {
        fichierExiste(utilisateursFichier);
        if (utilisateur.equals("admin")){
            return false;
        }
        try (Scanner sc = new Scanner(utilisateursFichier)){
            while (sc.hasNextLine()){
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()){
                    continue;
                }

                String[] utilisateurs = donnee.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(utilisateur)){
                    return false;
                }
            }
            return true;
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) throws IOException {
        fichierExiste(utilisateursFichier);
        boolean utilisateurExiste = false;
        List<String> lignesUtilisateur = new ArrayList<>();
        try (Scanner sc = new Scanner(utilisateursFichier)){
            while (sc.hasNextLine()){
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()){
                    continue;
                }

                String[] utilisateurs = donnee.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(utilisateur.getUsername())){
                    String ligneActualiser = utilisateur.getUsername() + "," + utilisateur.getMotDePasse();
                    lignesUtilisateur.add(ligneActualiser);
                    utilisateurExiste = true;
                } else {
                    lignesUtilisateur.add(donnee);
                }
            }

            if (!utilisateurExiste){
                String nouvelleLigne = utilisateur.getUsername() + "," + utilisateur.getMotDePasse();
                lignesUtilisateur.add(nouvelleLigne);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            ecrireFichier(lignesUtilisateur, utilisateursFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void supprimerUtilisateur(String utilisateur){
        fichierExiste(utilisateursFichier);
        List<String> lignesUtilisateur = new ArrayList<>();
        try (Scanner sc = new Scanner(utilisateursFichier)){
            while (sc.hasNextLine()){
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()){
                    continue;
                }

                String[] utilisateurs = donnee.split(",");
                if (utilisateurs.length > 0 && !(utilisateurs[0].trim().equals(utilisateur))){
                    lignesUtilisateur.add(donnee);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            ecrireFichier(lignesUtilisateur, utilisateursFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void ajouterLivre(Livre livre) throws IOException {
        fichierExiste(livresFichier);
        List<String> lignesLivres = new ArrayList<>();
        boolean livreExistant = false;

        try (Scanner sc = new Scanner(livresFichier)) {
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");
                if (livres.length > 0 && Integer.parseInt(livres[4].trim()) == livre.getLivreId()) {
                    String ligneActualiser = livre.getTitre() + "," + livre.getAuteur() + "," + livre.getGenre() + "," + livre.getQuantite() + "," + livre.getLivreId();
                    lignesLivres.add(ligneActualiser);
                    livreExistant = true;
                } else {
                    lignesLivres.add(donnee);
                }
            }
        }

        if (!livreExistant) {
            String nouvelleLigne = livre.getTitre() + "," + livre.getAuteur() + "," + livre.getGenre() + "," + livre.getQuantite() + "," + livre.getLivreId();
            lignesLivres.add(nouvelleLigne);
        }

        try {
            ecrireFichier(lignesLivres, livresFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void supprimerLivre(String livreId) throws IOException {
        fichierExiste(livresFichier);
        List<String> lignesLivres = new ArrayList<>();

        try (Scanner sc = new Scanner(livresFichier)) {
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");
                if (livres.length > 0 && !(livres[4].equals(livreId))) {
                    lignesLivres.add(donnee);
                }
            }
        }
        try {
            ecrireFichier(lignesLivres, livresFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean aEmprunt(String utilisateur){
        fichierExiste(empruntsFichier);
        try (Scanner sc = new Scanner(empruntsFichier)) {
            while (sc.hasNextLine()){
                String ligne = sc.nextLine();
                if (!ligne.isEmpty()){
                    String[] donnes = ligne.split(",");
                    if (donnes[0].trim().equals(utilisateur)){
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean emprunterLivre(String utilisateur, String livreId) throws IOException {
        fichierExiste(empruntsFichier);
        if (!livreExiste(livreId) || !utilisateurExisteSansPass(utilisateur) || aEmprunt(utilisateur)){
            return false;
        }

        if (!livreDisponible(livreId, 1)){
            return false;
        } else if (livreDisponible(livreId, 1)){
            updateBookQuantity(livreId, -1);
        }

        java.time.LocalDate dateEmprunt = java.time.LocalDate.now();
        java.time.LocalDate dateRetour = dateEmprunt.plusDays(14);

        String nouvelEmprunt = utilisateur + "," + getLivreTitre(livreId) + "," + livreId + "," + dateEmprunt + "," + dateRetour;

        List<String> lignesEmprunts = new ArrayList<>();
        try (Scanner sc = new Scanner(empruntsFichier)) {
            while (sc.hasNextLine()) {
                lignesEmprunts.add(sc.nextLine());
            }
        }
        lignesEmprunts.add(nouvelEmprunt);

        ecrireFichier(lignesEmprunts, empruntsFichier);
        return true;
    }

    public boolean retournerLivre(String utilisateur) throws IOException {
        fichierExiste(empruntsFichier);
        List<String> lignesEmprunts = new ArrayList<>();

        try (Scanner sc = new Scanner(empruntsFichier)) {
            while (sc.hasNextLine()) {
                String ligne = sc.nextLine().trim();
                if (ligne.isEmpty()) {
                    continue;
                }
                String[] donnees = ligne.split(",");

                if (!donnees[0].equals(utilisateur)) {
                    lignesEmprunts.add(ligne);
                } else {
                    String livreId = donnees[2];
                    ecrireFichier(lignesEmprunts, empruntsFichier);
                    updateBookQuantity(livreId, 1);
                    return true;
                }
            }
        }
        return false;
    }

    private String getLivreTitre(String livreId) throws IOException {
        try (Scanner sc = new Scanner(livresFichier)) {
            while (sc.hasNextLine()) {
                String ligne = sc.nextLine();
                if (!ligne.isEmpty()) {
                    String[] donnees = ligne.split(",");
                    if (donnees[4].trim().equals(livreId)) {
                        return donnees[0].trim();
                    }
                }
            }
        }
        throw new IOException("Livre non trouve!");
    }

    public void updateBookQuantity(String livreId, int quantiter){
        fichierExiste(livresFichier);
        List<String> lignesLivres = new ArrayList<>();
        try (Scanner sc = new Scanner(livresFichier)){
            while (sc.hasNextLine()){
                String donnee = sc.nextLine();
                if (donnee.isEmpty()){
                    continue;
                }

                String[] livres = donnee.split(",");
                if (livres.length > 0 && livres[4].trim().equals(livreId)){
                    int quantite = Integer.parseInt(livres[3]) + quantiter;
                    String ligneActualiser = livres[0] + "," + livres[1] + "," + livres[2] + "," + quantite + "," + livreId;
                    lignesLivres.add(ligneActualiser);
                } else {
                    lignesLivres.add(donnee);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            ecrireFichier(lignesLivres, livresFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean supprimerDefinitementCheck(String livreId){
        fichierExiste(livresFichier);
        fichierExiste(empruntsFichier);
        try (Scanner sc = new Scanner(livresFichier)) {
            boolean quantiteLivre = true;
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");
                if (livres.length > 0 && livres[4].trim().equals(livreId)) {
                    int quantite = Integer.parseInt(livres[3]);
                    quantiteLivre = quantite == 0;
                }
            }
            if (!quantiteLivre) {
                return false;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        try (Scanner sc = new Scanner(empruntsFichier)) {
            int quantiteReel = 0;
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");
                if (livres.length > 0 && livres[2].equals(livreId)) {
                    quantiteReel+=1;
                }
            }
            if (quantiteReel>0){
                return false;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean livreDisponible(String livreId, int quantiterPrise) throws IOException {
        fichierExiste(livresFichier);
        try (Scanner sc = new Scanner(livresFichier)) {
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");
                if (livres.length > 0 && livres[4].equals(livreId)) {
                    int quantite = Integer.parseInt(livres[3]);
                    return (quantite - quantiterPrise) >= 0;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean livreExiste(Livre livre) {
        fichierExiste(livresFichier);
        try (Scanner sc = new Scanner(livresFichier)) {
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");

                String livreTitre = livres[0].replaceAll("\\s", "").toLowerCase();
                String livreT = livre.getTitre().replaceAll("\\s", "").toLowerCase();

                if (livreTitre.equals(livreT)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean livreExiste(String livreId){
        fichierExiste(livresFichier);
        try (Scanner sc = new Scanner(livresFichier)) {
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");
                if (livres.length > 0 && livres[4].equals(livreId)){
                    return true;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    private void ecrireFichier(List<String> lignes, File fichier) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichier))) {
            for (String line : lignes) {
                pw.println(line);
            }
        }
    }
}
