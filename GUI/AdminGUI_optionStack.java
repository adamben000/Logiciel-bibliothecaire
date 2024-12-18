package GUI;
import javax.swing.*;
import java.awt.*;

public class AdminGUI_optionStack extends JPanel{
    JLabel titre = new JLabel ("Page Administrateur");
    JButton utilisateurs_Page = new JButton("Utilisateurs");
    JButton livres_Page = new JButton("Livres");
    JButton emprunts_Page = new JButton("Emprunts");
    JButton deconnexion = new JButton("Deconnexion");
    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    CardLayout cardLayout;
    JPanel cardPanel;


    public AdminGUI_optionStack(CardLayout cardLayout, JPanel cardPanel){

        setLayout(gridLayout);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        add(titre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(utilisateurs_Page, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(livres_Page, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(emprunts_Page, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(deconnexion, gbc);

        deconnexion.addActionListener(e -> cardLayout.show(cardPanel, "Connexion"));

        setVisible(true);

    }

}
