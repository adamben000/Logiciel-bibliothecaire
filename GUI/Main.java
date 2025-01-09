package GUI;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Créer la fenêtre principale de l'application
        JFrame frame = new JFrame("Connexion-Librairie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application lorsque la fenêtre est fermée
        frame.setSize(600, 400); // Définir la taille de la fenêtre
        frame.setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        frame.setResizable(false); // Empêcher le redimensionnement de la fenêtre

        // Configurer le CardLayout pour gérer les différentes vues/panneaux
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout); // Panneau qui contiendra les différents cartes (vues)

        // Ajouter différents panneaux (vues) au cardPanel avec des noms associés
        cardPanel.add(new ConnexionGUI(cardLayout, cardPanel, frame), "Connexion");
        cardPanel.add(new RegistreGUI(cardLayout, cardPanel, frame), "Registre");
        cardPanel.add(new AdminGUI_optionStack(cardLayout, cardPanel, frame), "AdminOptionStack");
        cardPanel.add(new AdminUtilisateur(cardLayout, cardPanel, frame), "AdminUtilisateur");
        cardPanel.add(new AdminLivres(cardLayout, cardPanel, frame), "AdminLivres");
        cardPanel.add(new AdminEmprunts(cardLayout, cardPanel, frame), "AdminEmprunts");
        cardPanel.add(new UtilisateurGUI(cardLayout, cardPanel, frame, null), "Utilisateur");

        // Ajouter le cardPanel à la fenêtre principale
        frame.add(cardPanel);
        frame.setVisible(true); // Rendre la fenêtre visible

        // Afficher initialement le panneau "Connexion"
        cardLayout.show(cardPanel, "Connexion");
    }
}
