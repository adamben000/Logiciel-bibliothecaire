package GUI;

import LMS.Database;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    private Database db = new Database();

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

        seConnecterBouton.addActionListener(this);

        creationDeCompteBouton.addActionListener(e -> cardLayout.show(cardPanel, "Registre"));

    }
    private boolean verification(){
        String nom = utilisateur.getText();
        String pass = new String(motDePasse.getPassword());

        List<String> errorMessages = new ArrayList<>();

        if (nom.isEmpty()){
            errorMessages.add("Le champ \"Nom d'utilisateur\" ne peut pas etre vide!");
        } else if (nom.length()>10){
            errorMessages.add("Ne peut pas entrer plus de 10 characteres pour votre nom d'utilisateur.");
        } else if (nom.contains(" ")) {
            errorMessages.add("Ne peut pas entrer d'espace pour votre nom d'utilisateur!");
        }

        if (pass.isEmpty()){
            errorMessages.add("Le champ \"Mot de passe\" ne peut pas etre vide!");
        } else if (pass.length()>16){
            errorMessages.add("Ne peut pas entrer plus de 16 characteres pour votre mot de passe.");
        } else if (pass.contains(" ")) {
            errorMessages.add("Ne peut pas entrer d'espace pour votre mot de passe!");
        }

        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(
                    ConnexionGUI.this,
                    String.join("\n", errorMessages),
                    "Erreurs:",
                    JOptionPane.ERROR_MESSAGE
            );
            enleverCharacteres();
            return false;
        } else {
            return true;
        }
    }

    private void enleverCharacteres(){
        utilisateur.setText("");
        motDePasse.setText("");
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (command.equals("Connexion")){
            String nom = utilisateur.getText();
            String pass = new String(motDePasse.getPassword());

            if(verification()){
                try {
                    if (db.utilisateurExiste(nom, pass)){
                        JOptionPane.showMessageDialog(
                                ConnexionGUI.this,
                                "Connexion succes!",
                                "Info:",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        enleverCharacteres();
                    } else if (db.checkAdmin(nom, pass)) {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
