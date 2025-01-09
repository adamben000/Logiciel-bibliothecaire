package GUI;
import javax.swing.*;
import java.awt.*;

public class AdminGUI_optionStack extends JPanel {
    // Déclaration des composants graphiques
    JLabel titre = new JLabel("Page Administrateur");
    JButton utilisateurs_Page = new JButton("Utilisateurs");
    JButton livres_Page = new JButton("Livres");
    JButton emprunts_Page = new JButton("Emprunts");
    JButton deconnexion = new JButton("Deconnexion");

    // Layout et contraintes pour organiser les composants
    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    // Constructeur de la classe AdminGUI_optionStack
    public AdminGUI_optionStack(CardLayout cardLayout, JPanel cardPanel, JFrame cadre) {
        // Définir le layout de la page
        setLayout(gridLayout);

        // Paramétrage des contraintes pour la disposition des composants
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement autour des composants

        // Titre de la page administrateur
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        titre.setFont(new Font("Arial", Font.BOLD, 24)); // Police et taille du titre
        titre.setHorizontalAlignment(SwingConstants.CENTER); // Centrer le titre
        add(titre, gbc);

        // Ajout des boutons sur la page
        gbc.gridx = 0; gbc.gridy = 1;
        add(utilisateurs_Page, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(livres_Page, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(emprunts_Page, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(deconnexion, gbc);

        // Action pour le bouton de déconnexion
        deconnexion.addActionListener(e -> {
            cardLayout.show(cardPanel, "Connexion"); // Retour à la page de connexion
        });

        // Action pour le bouton "Utilisateurs"
        utilisateurs_Page.addActionListener(e -> {
            // Rafraîchir la table des utilisateurs avant de passer à la vue AdminUtilisateur
            Component[] components = cardPanel.getComponents();
            for (Component component : components) {
                if (component instanceof AdminUtilisateur) {
                    AdminUtilisateur adminUtilisateur = (AdminUtilisateur) component;
                    adminUtilisateur.actualiserLeTableau(); // Rafraîchir la table des utilisateurs
                }
            }
            // Modifier la taille et le titre de la fenêtre avant de montrer la page AdminUtilisateur
            cadre.setSize(800,600);
            cadre.setTitle("Utilisateurs-Librairie-Management");
            cadre.setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "AdminUtilisateur"); // Afficher la page AdminUtilisateur
        });

        // Action pour le bouton "Livres"
        livres_Page.addActionListener(e -> {
            // Rafraîchir la table des livres avant de passer à la vue AdminLivres
            Component[] components = cardPanel.getComponents();
            for (Component component : components) {
                if (component instanceof AdminLivres) {
                    AdminLivres adminLivres = (AdminLivres) component;
                    adminLivres.actualiserLeTableau(); // Rafraîchir la table des livres
                }
            }
            // Modifier la taille et le titre de la fenêtre avant de montrer la page AdminLivres
            cadre.setSize(800,600);
            cadre.setTitle("Livres-Librairie-Management");
            cadre.setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "AdminLivres"); // Afficher la page AdminLivres
        });

        // Action pour le bouton "Emprunts"
        emprunts_Page.addActionListener(e -> {
            // Rafraîchir les tables des emprunts avant de passer à la vue AdminEmprunts
            Component[] components = cardPanel.getComponents();
            for (Component component : components) {
                if (component instanceof AdminEmprunts) {
                    AdminEmprunts adminEmprunts = (AdminEmprunts) component;
                    adminEmprunts.actualiserLeTableau(); // Rafraîchir la première table des emprunts
                    adminEmprunts.actualiserLeTableau2(); // Rafraîchir la deuxième table des emprunts
                    adminEmprunts.actualiserLeTableau3(); // Rafraîchir la troisième table des emprunts
                }
            }
            // Modifier la taille et le titre de la fenêtre avant de montrer la page AdminEmprunts
            cadre.setSize(1000, 600);
            cadre.setTitle("Emprunts-Librairie-Management");
            cadre.setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "AdminEmprunts"); // Afficher la page AdminEmprunts
        });

        // Rendre le panneau visible
        setVisible(true);
    }
}
