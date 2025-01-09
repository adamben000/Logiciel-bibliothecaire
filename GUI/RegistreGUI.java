package GUI;

import LMS.Database;
import LMS.Utilisateur;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistreGUI extends JPanel implements ActionListener {
    // Déclaration des composants de l'interface utilisateur
    JLabel titre = new JLabel("Registre");
    JLabel utilisateurTitre = new JLabel("Nom d'utilisateur:");
    JLabel motDePasseTitre = new JLabel("Mot de passe:");
    JLabel comfirmMotDePasseTitre = new JLabel("Confirmez votre mot de passe:");

    JTextField utilisateur = new JTextField("", 10);
    JPasswordField motDePasse = new JPasswordField("", 10);
    JPasswordField comfirmMotDePasse = new JPasswordField("", 10);

    JButton enregistrer = new JButton("Enregistrer");
    JButton retour = new JButton("Retour");

    // Mise en place de la disposition (layout) pour organiser les composants
    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    CardLayout cardLayout; // Pour basculer entre différents panneaux
    JPanel cardPanel;

    private Database db = new Database(); // Référence à la base de données

    // Constructeur pour initialiser l'interface graphique
    public RegistreGUI(CardLayout cardLayout, JPanel cardPanel, JFrame frame) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setLayout(gridLayout); // Utilisation du GridBagLayout
        gbc.fill = GridBagConstraints.HORIZONTAL; // Composants s'étendent horizontalement
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants

        // Ajout du titre
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        add(titre, gbc);

        // Ajout du champ pour le nom d'utilisateur
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(utilisateurTitre, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        add(utilisateur, gbc);

        // Ajout du champ pour le mot de passe
        gbc.gridx = 0; gbc.gridy = 2;
        add(motDePasseTitre, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        add(motDePasse, gbc);

        // Ajout du champ pour la confirmation du mot de passe
        gbc.gridx = 0; gbc.gridy = 3;
        add(comfirmMotDePasseTitre, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        add(comfirmMotDePasse, gbc);

        // Bouton "Enregistrer"
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        enregistrer.setPreferredSize(new Dimension(200, 40));
        add(enregistrer, gbc);
        enregistrer.setBackground(Color.black);
        enregistrer.setForeground(Color.white);

        // Bouton "Retour"
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(retour, gbc);

        // Ajout des actions pour les boutons
        enregistrer.addActionListener(this);
        retour.addActionListener(e -> {
            frame.setTitle("Connexion-Librairie");
            cardLayout.show(cardPanel, "Connexion");
        });
    }

    // Méthode pour effacer les champs de mot de passe
    private void enleverCharacteres() {
        motDePasse.setText("");
        comfirmMotDePasse.setText("");
    }

    // Méthode pour vérifier les données saisies
    private boolean verification() {
        String nom = utilisateur.getText();
        String pass = new String(motDePasse.getPassword());
        String confirmerPass = new String(comfirmMotDePasse.getPassword());

        List<String> errorMessages = new ArrayList<>();

        // Validation du nom d'utilisateur
        if (nom.isEmpty()) {
            errorMessages.add("Le champ \"Nom d'utilisateur\" ne peut pas être vide !");
        } else {
            if (nom.length() > 10) {
                errorMessages.add("Le nom d'utilisateur ne peut pas dépasser 10 caractères.");
            }
            if (nom.contains(" ")) {
                errorMessages.add("Le nom d'utilisateur ne peut pas contenir d'espace.");
            }
            if (nom.contains(",")) {
                errorMessages.add("Le nom d'utilisateur ne peut pas contenir de virgule.");
            }
        }

        // Validation du mot de passe
        if (pass.isEmpty()) {
            errorMessages.add("Le champ \"Mot de passe\" ne peut pas être vide !");
        } else {
            if (pass.length() > 16) {
                errorMessages.add("Le mot de passe ne peut pas dépasser 16 caractères.");
            }
            if (pass.length() < 4) {
                errorMessages.add("Le mot de passe doit avoir un minimum de 4 caractères.");
            }
            if (pass.contains(" ")) {
                errorMessages.add("Le mot de passe ne peut pas contenir d'espace.");
            }
            if (pass.contains(",")) {
                errorMessages.add("Le mot de passe ne peut pas contenir de virgule.");
            }
        }

        // Vérification de la correspondance entre mot de passe et confirmation
        if (!pass.equals(confirmerPass)) {
            errorMessages.add("Le mot de passe et sa confirmation ne correspondent pas.");
        }

        // Affichage des erreurs, s'il y en a
        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    String.join("\n", errorMessages),
                    "Erreurs :",
                    JOptionPane.ERROR_MESSAGE
            );
            enleverCharacteres(); // Effacer les champs de mot de passe
            return false;
        }

        return true;
    }

    // Gestion des actions des boutons
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (command.equals("Enregistrer")) {
            String nom = utilisateur.getText();
            String pass = new String(motDePasse.getPassword());
            Utilisateur utilisateurCree = new Utilisateur(nom, pass);

            // Vérification des données et ajout à la base de données
            if (verification()) {
                try {
                    if (db.verifierNomUtilisateur(nom)) {
                        db.ajouterUtilisateur(utilisateurCree);
                        enleverCharacteres();
                        utilisateur.setText("");
                        JOptionPane.showMessageDialog(
                                RegistreGUI.this,
                                "Succès, compte créé !",
                                "Info :",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                RegistreGUI.this,
                                "Ce nom d'utilisateur est déjà pris. Veuillez en choisir un autre.",
                                "Erreur :",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}