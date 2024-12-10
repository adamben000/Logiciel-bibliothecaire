package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ConnexionGUI extends JPanel implements ActionListener{
    JLabel titre = new JLabel("Page de connexion");
    JLabel  utilisateurTitre = new JLabel("Nom d'utilisateur:");
    JTextField utilisateur = new JTextField("", 10);
    JLabel  motDePasseTitre = new JLabel("Mot de passe:");
    JPasswordField motDePasse = new JPasswordField("", 8);
    JButton seConnecterBouton = new JButton("Connexion");
    JButton creationDeCompteBouton = new JButton("Creation de compte");
    JLabel creationDeCompteLabel = new JLabel("Pas de compte?");


    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    CardLayout cardLayout;
    JPanel cardPanel;


    public ConnexionGUI(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(gridLayout);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);


        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        titre.setFont(new Font("Arial", Font.BOLD, 24));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        add(titre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        add(utilisateurTitre, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        add(utilisateur, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(motDePasseTitre, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        add(motDePasse, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        seConnecterBouton.setPreferredSize(new Dimension(200, 40));
        add(seConnecterBouton, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.LINE_END;
        add(creationDeCompteLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_START;
        add(creationDeCompteBouton, gbc);

        seConnecterBouton.setBackground(Color.black);
        seConnecterBouton.setForeground(Color.white);

        creationDeCompteBouton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));

    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
    }


}

