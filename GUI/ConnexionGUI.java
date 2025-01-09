package GUI;

import LMS.Database;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnexionGUI extends JPanel {
    // Déclaration des composants graphiques
    JLabel titre = new JLabel("Page de connexion");
    JLabel utilisateurTitre = new JLabel("Nom d'utilisateur:");
    JTextField utilisateur = new JTextField("", 10);
    JLabel motDePasseTitre = new JLabel("Mot de passe:");
    JPasswordField motDePasse = new JPasswordField("", 8);
    JButton seConnecterBouton = new JButton("Connexion");
    JButton creationDeCompteBouton = new JButton("Création de compte");
    JLabel creationDeCompteLabel = new JLabel("Pas de compte?");

    // Variable pour sauvegarder le nom d'utilisateur
    public String nomUtilisateur;

    // Layout et contraintes pour organiser les composants
    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    // Variables pour gérer les cartes
    CardLayout cardLayout;
    JPanel cardPanel;

    // Instance de la base de données pour vérifier les utilisateurs
    private Database db = new Database();

    // Constructeur qui initialise les composants et configure les actions
    public ConnexionGUI(CardLayout cardLayout, JPanel cardPanel, JFrame cadre) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setLayout(gridLayout); // Utiliser GridBagLayout pour la mise en page

        // Paramétrage des contraintes pour les composants
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement autour des composants

        // Titre de la page de connexion
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        add(titre, gbc);

        // Champ pour le nom d'utilisateur
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(utilisateurTitre, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(utilisateur, gbc);

        // Champ pour le mot de passe
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(motDePasseTitre, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(motDePasse, gbc);

        // Bouton de connexion
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        seConnecterBouton.setPreferredSize(new Dimension(200, 40));
        add(seConnecterBouton, gbc);

        // Liens pour la création de compte
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(creationDeCompteLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(creationDeCompteBouton, gbc);

        // Style du bouton de connexion
        seConnecterBouton.setBackground(Color.black);
        seConnecterBouton.setForeground(Color.white);

        // Action lorsque le bouton de connexion est cliqué
        seConnecterBouton.addActionListener(e -> {
            String nom = utilisateur.getText();
            String pass = new String(motDePasse.getPassword());

            if (verification()) { // Vérifie si les entrées sont valides
                try {
                    // Vérification de l'existence de l'utilisateur dans la base de données
                    if (db.utilisateurExiste(nom, pass)) {
                        enleverCharacteres(); // Effacer les champs après connexion
                        nomUtilisateur = nom;

                        // Retirer et ajouter les bons panneaux pour l'utilisateur connecté
                        Component[] components = cardPanel.getComponents();
                        for (Component component : components) {
                            if (component instanceof UtilisateurGUI) {
                                cardPanel.remove(component);
                            }
                        }

                        cardPanel.add(new UtilisateurGUI(cardLayout, cardPanel, cadre, nomUtilisateur), "Utilisateur");
                        cadre.setSize(1003, 600); // Modifier la taille de la fenêtre
                        cadre.setTitle("Utilisateurs-Librairie"); // Titre de la fenêtre
                        cadre.setLocationRelativeTo(null); // Centrer la fenêtre
                        cardLayout.show(cardPanel, "Utilisateur"); // Afficher la vue utilisateur

                        // Actualiser les informations pour l'utilisateur
                        Component[] components1 = cardPanel.getComponents();
                        for (Component component1 : components1) {
                            if (component1 instanceof UtilisateurGUI) {
                                UtilisateurGUI Utilisateur = (UtilisateurGUI) component1;
                                Utilisateur.actualiserLeTableau();
                                Utilisateur.empruntDetection();
                            }
                        }

                    } else if (db.verifierAdmin(nom, pass)) { // Vérification si c'est un administrateur
                        enleverCharacteres(); // Effacer les champs
                        cardLayout.show(cardPanel, "AdminOptionStack"); // Afficher la vue administrateur
                    } else {
                        // Affichage d'un message d'erreur en cas d'échec de connexion
                        JOptionPane.showMessageDialog(
                                ConnexionGUI.this,
                                "Utilisateur ou mot de passe incorrecte!",
                                "Erreur:",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (IOException e1) {
                    System.out.println("Erreur de connexion à la base de données");
                }
            }
        });

        // Action lorsque le bouton de création de compte est cliqué
        creationDeCompteBouton.addActionListener(e -> {
            cadre.setTitle("Registre-Librairie");
            cardLayout.show(cardPanel, "Registre"); // Passer à la vue de création de compte
        });
    }

    // Vérification des champs de connexion
    private boolean verification() {
        String nom = utilisateur.getText();
        String pass = new String(motDePasse.getPassword());

        List<String> errorMessages = new ArrayList<>(); // Liste des messages d'erreur

        // Vérification du nom d'utilisateur
        if (nom.isEmpty()) {
            errorMessages.add("Le champ \"Nom d'utilisateur\" ne peut pas être vide !");
        } else {
            if (nom.length() > 10) {
                errorMessages.add("Le nom d'utilisateur ne peut pas dépasser 10 caractères.");
            }
            if (nom.contains(" ")) {
                errorMessages.add("Le nom d'utilisateur ne peut pas contenir d'espace.");
            }
        }

        // Vérification du mot de passe
        if (pass.isEmpty()) {
            errorMessages.add("Le champ \"Mot de passe\" ne peut pas être vide !");
        } else {
            if (pass.length() > 16) {
                errorMessages.add("Le mot de passe ne peut pas dépasser 16 caractères.");
            }
            if (pass.contains(" ")) {
                errorMessages.add("Le mot de passe ne peut pas contenir d'espace.");
            }
        }

        // Si des erreurs sont détectées, afficher les messages d'erreur
        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    String.join("\n", errorMessages),
                    "Erreurs :",
                    JOptionPane.ERROR_MESSAGE
            );
            enleverCharacteres(); // Effacer les champs si erreur
            return false;
        }

        return true; // Les informations sont valides
    }

    // Fonction pour effacer les champs de texte
    private void enleverCharacteres() {
        utilisateur.setText("");
        motDePasse.setText("");
    }
}
