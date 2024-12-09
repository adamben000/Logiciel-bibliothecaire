package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

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

    public RegistreGUI(CardLayout cardLayout, JPanel cardPanel) {
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
        retour.addActionListener(e -> cardLayout.show(cardPanel, "Connection"));

    }
    private void enleverCharacteres(){
        motDePasse.setText("");
        comfirmMotDePasse.setText("");
    }
    private void verification(){
        String nom = utilisateur.getText();
        String pass = new String(motDePasse.getPassword());
        String confirmerPass = new String(comfirmMotDePasse.getPassword());

        int nomLength = nom.length();
        int passLength = pass.length();

        if (nomLength > 10){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Ne pas entrer plus de 10 caracteres pour votre nom d'utilisateur.",
                    "Info:",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (passLength > 16){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Ne pas entrer plus de 16 caracteres pour votre mot de passe.",
                    "Info:",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (nom.equals("")){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Le champ \"Nom d'utilisateur\" ne peut pas etre vide!",
                    "Erreur:",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (pass.equals("")){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Le champ \"Mot de passe\" ne peut pas etre vide!",
                    "Erreur:",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (nom.contains(" ")){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Ne pas entrer d'espace pour votre nom d'utilisateur!",
                    "Erreur:",
                    JOptionPane.ERROR_MESSAGE);
        }

        if (pass.contains(" ")){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Ne pas entrer d'espace pour votre mot de passe!",
                    "Erreur:",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (confirmerPass.equals(pass) && nomLength <= 10 && passLength <= 16
                && !(nom.equals("")) && !(pass.equals("")) && !(nom.contains(" ")) && !(pass.contains(" ")) ){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Succes, compte creer!",
                    "Info:",
                    JOptionPane.INFORMATION_MESSAGE);
            enleverCharacteres();
            utilisateur.setText("");

        }
        if (!(confirmerPass.equals(pass))){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Erreur mot de passes different!",
                    "Erreur:",
                    JOptionPane.ERROR_MESSAGE);
            enleverCharacteres();
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (command == "Enregistrer"){
            verification();
        }
    }

}