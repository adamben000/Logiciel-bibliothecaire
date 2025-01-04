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
    JLabel titre = new JLabel("Registre");
    JLabel utilisateurTitre = new JLabel("Nom d'utilisateur:");
    JLabel motDePasseTitre = new JLabel("Mot de passe:");
    JLabel comfirmMotDePasseTitre = new JLabel("Confirmez votre mot de passe:");

    JTextField utilisateur = new JTextField("", 10);
    JPasswordField motDePasse = new JPasswordField("", 10);
    JPasswordField comfirmMotDePasse = new JPasswordField("", 10);

    JButton enregistrer = new JButton("Enregistrer");

    JButton retour = new JButton("retour");


    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();

    CardLayout cardLayout;
    JPanel cardPanel;

    private Database db = new Database();

    public RegistreGUI(CardLayout cardLayout, JPanel cardPanel, JFrame frame) {
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


        gbc.gridx = 0; gbc.gridy = 3;
        add(comfirmMotDePasseTitre, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        add(comfirmMotDePasse, gbc);


        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        enregistrer.setPreferredSize(new Dimension(200, 40));
        add(enregistrer, gbc);

        enregistrer.setBackground(Color.black);
        enregistrer.setForeground(Color.white);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(retour, gbc);

        enregistrer.addActionListener(this);
        retour.addActionListener(e -> {frame.setTitle("Connexion-Librairie");cardLayout.show(cardPanel, "Connexion");});

    }
    private void enleverCharacteres(){
        motDePasse.setText("");
        comfirmMotDePasse.setText("");
    }
    private boolean verification(){
        String nom = utilisateur.getText();
        String pass = new String(motDePasse.getPassword());
        String confirmerPass = new String(comfirmMotDePasse.getPassword());

        List<String> errorMessages = new ArrayList<>();

        if (nom.contains(",")){
            errorMessages.add("Le \"Nom d'utilisateur\" ne peut pas contenir de virgule!");
        } else if (nom.isEmpty()){
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
        } else if (pass.contains(",")) {
            errorMessages.add("Le \"Mot de passe\" ne peut pas contenir de virgule!");
        } else if (!pass.equals(confirmerPass)){
            errorMessages.add("Erreur mot de passe differents!");
        }

        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
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

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (command.equals("Enregistrer")){
            String nom = utilisateur.getText();
            String pass = new String(motDePasse.getPassword());
            Utilisateur utilisateurCree = new Utilisateur(nom, pass);

            if(verification()){
                try {
                    if (db.checkUtilisateur(nom)){
                        db.ajouterUtilisateur(utilisateurCree);
                        enleverCharacteres();
                        utilisateur.setText("");
                        JOptionPane.showMessageDialog(
                                RegistreGUI.this,
                                "Succes, compte creer!",
                                "Info:",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                RegistreGUI.this,
                                "Nom d'utilisateur deja pris!",
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