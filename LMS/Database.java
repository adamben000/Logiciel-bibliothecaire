package LMS;

import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class Database {
    private final File livresFichier = new File("db/livres.txt");
    private final File utilisateursFichier = new File("db/utilisateurs.txt");
    private final File empruntsFichier = new File ("db/emprunts.txt");

    private void fichierExiste(File fichier) throws IOException {
        if (!fichier.isFile()) {
            fichier.getParentFile().mkdirs();
            fichier.createNewFile();
        }
    }

    public boolean checkUtilisateur(String username) throws IOException {
        fichierExiste(utilisateursFichier);
        try (Scanner sc = new Scanner(utilisateursFichier)){
            while (sc.hasNextLine()){
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()){
                    continue;
                }

                String[] utilisateurs = donnee.split(",");
                if (utilisateurs.length > 0 && utilisateurs[0].trim().equals(username)){
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
        estNull(utilisateur);
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
                    String ligneActualiser = utilisateur.getUsername() + "," + utilisateur.getMotDePasse() + "," + utilisateur.getLivresEmpruntes();
                    lignesUtilisateur.add(ligneActualiser);
                    utilisateurExiste = true;
                } else {
                    lignesUtilisateur.add(donnee);
                }
            }

            if (!utilisateurExiste){
                String nouvelleLigne = utilisateur.getUsername() + "," + utilisateur.getMotDePasse() + "," + utilisateur.getLivresEmpruntes();
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

    public void ecrireFichier(List<String> lignes, File fichier) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichier))) {
            for (String line : lignes) {
                pw.println(line);
            }
        }
    }
    public void estNull(Object object){
        if (object==null){
            throw new IllegalArgumentException("Ne peut pas etre nulle!");
        }
    }

    public void emprunterLivre(Utilisateur utilisateur, Livre livre) throws IOException {
        estNull(livre);
        estNull(utilisateur);
        fichierExiste(empruntsFichier);

        if (livre.getEstEmprunter()){
            return;
        }
        livre.setEstEmprunter(true);
        ajouterLivre(livre);

        LocalDate dateEmprunt = LocalDate.now();
        LocalDate dateRetour = dateEmprunt.plusWeeks(2);

        List<String> lignesEmpruntes = new ArrayList<>();

        try (Scanner sc = new Scanner(empruntsFichier)){
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()) {
                    continue;
                }
                String[] empruntes = donnee.split(",");
                if (empruntes.length > 0) {
                }
            }
        }
    }

    public void ajouterLivre(Livre livre) throws IOException {
        estNull(livre);
        fichierExiste(livresFichier);
        boolean livreExiste = false;
        List<String> lignesLivre = new ArrayList<>();
        try (Scanner sc = new Scanner(livresFichier)) {
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()) {
                    continue;
                }

                String[] livres = donnee.split(",");
                if (livres.length > 0 && Integer.parseInt(livres[0].trim()) == livre.getLivreId()) {
                    String ligneActualiser = livre.getLivreId() + "," + livre.getTitre() + "," +
                            livre.getAuteur() + "," + livre.getGenre() + "," +
                            livre.getQuantite() + "," + livre.getEstEmprunter();
                    lignesLivre.add(ligneActualiser);
                    livreExiste = true;
                } else {
                    lignesLivre.add(donnee);
                }
            }

            if (!livreExiste) {
                String nouvelleLigne = livre.getLivreId() + "," + livre.getTitre() + "," +
                        livre.getAuteur() + "," + livre.getGenre() + "," +
                        livre.getQuantite() + "," + livre.getEstEmprunter();
                lignesLivre.add(nouvelleLigne);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            ecrireFichier(lignesLivre, livresFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void supprimerLivre (Livre livre) throws IOException {
        estNull(livre);
        fichierExiste(livresFichier);
        List<String> lignesLivre = new ArrayList<>();
        try (Scanner sc = new Scanner(livresFichier)) {
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()) {
                    continue;
                }
                String[] livres = donnee.split(",");
                if (livres.length > 0 && Integer.parseInt(livres[0].trim()) == livre.getLivreId()) {
                    continue;
                } else {
                    lignesLivre.add(donnee);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try {
            ecrireFichier(lignesLivre, livresFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
