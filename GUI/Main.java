package GUI;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Créer la fenêtre principale de l'application
        JFrame cadre = new JFrame("Connexion-Librairie");
        cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application lorsque la fenêtre est fermée
        cadre.setSize(600, 400); // Définir la taille de la fenêtre
        cadre.setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        cadre.setResizable(false); // Empêcher le redimensionnement de la fenêtre

        // Configurer le CardLayout pour gérer les différentes vues/panneaux
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout); // Panneau qui contiendra les différents cartes (vues)

        // Ajouter différents panneaux (vues) au cardPanel avec des noms associés
        cardPanel.add(new ConnexionGUI(cardLayout, cardPanel, cadre), "Connexion");
        cardPanel.add(new RegistreGUI(cardLayout, cardPanel, cadre), "Registre");
        cardPanel.add(new AdminGUI_optionStack(cardLayout, cardPanel, cadre), "AdminOptionStack");
        cardPanel.add(new AdminUtilisateur(cardLayout, cardPanel, cadre), "AdminUtilisateur");
        cardPanel.add(new AdminLivres(cardLayout, cardPanel, cadre), "AdminLivres");
        cardPanel.add(new AdminEmprunts(cardLayout, cardPanel, cadre), "AdminEmprunts");
        cardPanel.add(new UtilisateurGUI(cardLayout, cardPanel, cadre, null), "Utilisateur");

        // Ajouter le cardPanel à la fenêtre principale
        cadre.add(cardPanel);
        cadre.setVisible(true); // Rendre la fenêtre visible

        // Afficher initialement le panneau "Connexion"
        cardLayout.show(cardPanel, "Connexion");
    }
}
