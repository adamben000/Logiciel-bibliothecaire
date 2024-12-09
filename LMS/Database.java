package LMS;

import LMS.Livre;
import LMS.Utilisateur;
import java.util.*;
import java.io.*;

public class Database {
    private final File livresFichier = new File("db/livres.txt");
    private final File utilisateursFichier = new File("db/utilisateurs.txt");

    private void fichierExiste(File fichier) throws IOException {
        if (!fichier.exists()) {
            fichier.getParentFile().mkdirs();
            fichier.createNewFile();
        }
    }
    public void ecrireFichier(List<String> lignes, File fichier) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichier))) {
            for (String line : lignes) {
                pw.println(line);
            }
        }
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
                if (utilisateurs.length > 0 && Integer.parseInt(utilisateurs[0].trim()) == utilisateur.getUserId()){
                    String ligneActualiser = utilisateur.getUserId() + "," + utilisateur.getUsername() + "," +
                            utilisateur.getMotDePasse() + "," + utilisateur.getLivresEmpruntes();
                    lignesUtilisateur.add(ligneActualiser);
                    utilisateurExiste = true;
                } else {
                    lignesUtilisateur.add(donnee);
                }
            }

            if (!utilisateurExiste){
                String nouvelleLigne = utilisateur.getUserId() + "," + utilisateur.getUsername() + "," +
                        utilisateur.getMotDePasse() + "," + utilisateur.getLivresEmpruntes();
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

    public void supprimerUtilisateur(Utilisateur utilisateur) throws IOException {
        fichierExiste(utilisateursFichier);
        List<String> lignesUtlisateur = new ArrayList<>();
        try (Scanner sc = new Scanner(utilisateursFichier)) {
            while (sc.hasNextLine()) {
                String donnee = sc.nextLine().trim();
                if (donnee.isEmpty()) {
                    continue;
                }
                String[] utilisateurs = donnee.split(",");
                if (utilisateurs.length > 0 && Integer.parseInt(utilisateurs[0].trim()) == utilisateur.getUserId()) {
                    continue;
                } else {
                    lignesUtlisateur.add(donnee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ecrireFichier(lignesUtlisateur, utilisateursFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ajouterLivre(Livre livre) throws IOException {
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
