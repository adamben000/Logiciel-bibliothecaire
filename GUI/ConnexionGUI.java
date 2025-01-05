package GUI;

import LMS.Database;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnexionGUI extends JPanel implements ActionListener {
    JLabel titre = new JLabel("Page de connexion");
    JLabel utilisateurTitre = new JLabel("Nom d'utilisateur:");
    JTextField utilisateur = new JTextField("", 10);
    JLabel motDePasseTitre = new JLabel("Mot de passe:");
    JPasswordField motDePasse = new JPasswordField("", 8);
    JButton seConnecterBouton = new JButton("Connexion");
    JButton creationDeCompteBouton = new JButton("Creation de compte");
    JLabel creationDeCompteLabel = new JLabel("Pas de compte?");

    //sauvegarder le nom
    public String nomUtilisateur;

    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    CardLayout cardLayout;
    JPanel cardPanel;


    private Database db = new Database();

    public ConnexionGUI(CardLayout cardLayout, JPanel cardPanel, JFrame frame) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setLayout(gridLayout);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        add(titre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(utilisateurTitre, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(utilisateur, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(motDePasseTitre, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(motDePasse, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        seConnecterBouton.setPreferredSize(new Dimension(200, 40));
        add(seConnecterBouton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(creationDeCompteLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(creationDeCompteBouton, gbc);

        seConnecterBouton.setBackground(Color.black);
        seConnecterBouton.setForeground(Color.white);

        seConnecterBouton.addActionListener(e -> {
            String nom = utilisateur.getText();
            String pass = new String(motDePasse.getPassword());

            if (verification()) {
                try {
                    if (db.utilisateurExiste(nom, pass)) {
                        enleverCharacteres();
                        nomUtilisateur = nom;
                        Component[] components = cardPanel.getComponents();
                        for (Component component : components) {
                            if (component instanceof Utilisateur) {
                                cardPanel.remove(component);
                            }
                        }

                        cardPanel.add(new Utilisateur(cardLayout, cardPanel, frame, nomUtilisateur), "Utilisateur");
                        frame.setSize(1003, 600);
                        frame.setTitle("Utilisateurs-Librairie");
                        frame.setLocationRelativeTo(null);
                        cardLayout.show(cardPanel, "Utilisateur");

                        Component[] components1 = cardPanel.getComponents();
                        for (Component component1 : components1) {
                            if (component1 instanceof Utilisateur) {
                                Utilisateur Utilisateur = (Utilisateur) component1;
                                Utilisateur.refreshTable();
                                Utilisateur.empruntDetection();
                            }
                        }


                    } else if (db.verifierAdmin(nom, pass)) {
                        enleverCharacteres();
                        cardLayout.show(cardPanel, "AdminOptionStack");
                    } else {
                        JOptionPane.showMessageDialog(
                                ConnexionGUI.this,
                                "Utilisateur ou mot de passe incorrecte!",
                                "Erreur:",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (IOException e1) {
                    System.out.println("erreur");
                }
            }
        });
        creationDeCompteBouton.addActionListener(e -> {
            frame.setTitle("Registre-Librairie");
            cardLayout.show(cardPanel, "Registre");
        });
    }

    private boolean verification() {
        String nom = utilisateur.getText();
        String pass = new String(motDePasse.getPassword());

        List<String> errorMessages = new ArrayList<>();

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

        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    String.join("\n", errorMessages),
                    "Erreurs :",
                    JOptionPane.ERROR_MESSAGE
            );
            enleverCharacteres();
            return false;
        }

        return true;
    }

    private void enleverCharacteres() {
        utilisateur.setText("");
        motDePasse.setText("");
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (command.equals("Connexion")) {
            String nom = utilisateur.getText();
            String pass = new String(motDePasse.getPassword());

            if (verification()) {
                try {
                    if (db.verifierAdmin(nom, pass)) {
                        enleverCharacteres();
                        JOptionPane.showMessageDialog(
                                this,
                                "Connexion réussie en tant qu'administrateur.",
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        cardLayout.show(cardPanel, "AdminOptionStack");
                    } else if (db.utilisateurExiste(nom, pass)) {
                        enleverCharacteres();
                        nomUtilisateur = nom;
                        JOptionPane.showMessageDialog(
                                this,
                                "Connexion réussie en tant qu'utilisateur.",
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        cardLayout.show(cardPanel, "Utilisateur");
                    } else if (!db.utilisateurExisteSansMotDePasse(nom)) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Aucun compte trouvé pour cet utilisateur. Veuillez vérifier le nom.",
                                "Erreur de connexion",
                                JOptionPane.ERROR_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Le mot de passe est incorrect. Veuillez réessayer.",
                                "Erreur de connexion",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Erreur système lors de la tentative de connexion.",
                            "Erreur système",
                            JOptionPane.ERROR_MESSAGE
                    );
                    e.printStackTrace();
                }
            }
        }
    }
}