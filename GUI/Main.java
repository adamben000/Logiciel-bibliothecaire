package GUI;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Librairie-Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        cardPanel.add(new ConnexionGUI(cardLayout, cardPanel), "Connexion");
        cardPanel.add(new RegistreGUI(cardLayout, cardPanel), "Registre");
        cardPanel.add(new AdminGUI_optionStack(cardLayout, cardPanel), "AdminOptionStack");
        cardPanel.add(new AdminUtilisateur(cardLayout, cardPanel), "AdminUtilisateur");

        frame.add(cardPanel);
        frame.setVisible(true);

        cardLayout.show(cardPanel, "Connexion");
    }
}