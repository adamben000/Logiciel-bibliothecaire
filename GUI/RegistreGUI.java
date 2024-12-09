package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import  java.awt.Container;

public class RegistreGUI extends JFrame implements ActionListener {
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

    public RegistreGUI() {
        super("Librairie-Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(gridLayout);
        setLocationRelativeTo(null);
        setResizable(false);


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
        setVisible(true);
    }

    public void verification(){
        String nom = utilisateur.getText();
        String pass = new String(motDePasse.getPassword());

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

        boolean espaceNom = nom.contains(" ");
        if (espaceNom){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Ne pas entrer d'espace pour votre nom d'utilisateur!",
                    "Erreur:",
                    JOptionPane.ERROR_MESSAGE);
        }

        boolean espacePass = pass.contains(" ");
        if (espacePass){
            JOptionPane.showMessageDialog(
                    RegistreGUI.this,
                    "Ne pas entrer d'espace pour votre mot de passe!",
                    "Erreur:",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (command == "Enregistrer"){
            verification();
        }
    }

    public static void main(String[] args) {
        RegistreGUI SomatifGUIRun = new RegistreGUI();
    }
}