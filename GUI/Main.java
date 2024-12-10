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

        cardPanel.add(new ConnexionGUI(cardLayout, cardPanel), "Connection");
        cardPanel.add(new RegistreGUI(cardLayout, cardPanel), "Register");

        frame.add(cardPanel);
        frame.setVisible(true);

        cardLayout.show(cardPanel, "Connection");
    }
}
