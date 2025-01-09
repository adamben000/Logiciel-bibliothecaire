package GUI;
import LMS.Database;
import LMS.Utilisateur;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class AdminUtilisateur extends JPanel implements ActionListener {
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    GridLayout laGrid = new GridLayout(2, 1);

    // Panel 2 pour la suppression d'un utilisateur
    JLabel utilisateur = new JLabel("Utilisateur:");
    JTextField utilisateurF = new JTextField("", 10);
    JButton supprimerB = new JButton("Supprimer");

    // Panel 3 pour la création d'un utilisateur
    JLabel utilisateurLCreate = new JLabel("Utilisateur:");
    JTextField utilisateurFCreate = new JTextField("", 10);

    JLabel motDePasseLCreate = new JLabel("Mot de passe:");
    JTextField motDePasseFCreate = new JTextField("", 10);
    JButton ajouterB = new JButton("Ajouter");
    JButton retourB = new JButton("Retour");

    // Déclaration des composants pour l'accès à la base de données
    GridBagConstraints gbc = new GridBagConstraints();
    private Database db = new Database();

    // Panneau de navigation et layout
    CardLayout cardLayout;
    JPanel cardPanel;

    // Tableau des utilisateurs
    JTable j;

    public AdminUtilisateur(CardLayout cardLayout, JPanel cardPanel, JFrame cadre) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setSize(800, 600);
        setLayout(laGrid);

        // Charger les utilisateurs depuis un fichier
        String[][] data = chargerLesNomsDUtilisateurAPartirDuFichier();
        String[] columnNames = {"Utilisateurs"};

        // Modèle de table avec des données non modifiables
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche la modification des noms d'utilisateurs
            }
        };

        // Initialisation du tableau
        j = new JTable(model);
        j.setBounds(30, 40, 200, 300);

        // Centrage des données dans la table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setVerticalAlignment(JLabel.CENTER);
        for (int i = 0; i < j.getColumnCount(); i++) {
            j.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Paramètres pour empêcher la sélection de lignes ou de colonnes
        j.getTableHeader().setReorderingAllowed(false);
        j.setRowSelectionAllowed(false);
        j.setColumnSelectionAllowed(false);
        j.setFocusable(false);
        j.getTableHeader().setResizingAllowed(false);

        JScrollPane sp = new JScrollPane(j);
        panel1.setLayout(new BorderLayout());
        panel1.add(sp, BorderLayout.CENTER);

        // Panneau de boutons en bas
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // Panel pour la suppression d'un utilisateur
        panel2.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel2.add(utilisateur, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel2.add(utilisateurF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel2.add(supprimerB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel2.add(retourB, gbc);

        // Panel pour la création d'un utilisateur
        panel3.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

        gbc1.gridx = 0;
        gbc1.gridy = 0;
        panel3.add(utilisateurLCreate, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        panel3.add(utilisateurFCreate, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 1;
        panel3.add(motDePasseLCreate, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 1;
        panel3.add(motDePasseFCreate, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 3;
        gbc1.gridwidth = 2;
        panel3.add(ajouterB, gbc1);

        // Ajouter les panneaux à la fenêtre principale
        add(panel1, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.add(panel2);
        bottomPanel.add(panel3);
        panel2.setBorder(new LineBorder(Color.BLACK, 1));
        panel3.setBorder(new LineBorder(Color.BLACK, 1));

        // Ajout des écouteurs d'événements
        supprimerB.addActionListener(this);
        ajouterB.addActionListener(this);
        retourB.addActionListener(e -> {
            cadre.setSize(600, 400);
            cadre.setTitle("Librairie-Management");
            cadre.setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "AdminOptionStack");
        });

        setVisible(true);
    }

    private boolean verification() {
        // Vérifie que les champs de création sont valides
        String nom = utilisateurFCreate.getText();
        String pass = motDePasseFCreate.getText();

        List<String> errorMessages = new ArrayList<>();

        if (nom.isEmpty()) {
            errorMessages.add("Le champ \"Nom d'utilisateur\" est requis.");
        } else {
            if (nom.length() > 10) {
                errorMessages.add("Le nom d'utilisateur ne peut pas dépasser 10 caractères.");
            }
            if (nom.contains(" ") || nom.contains(",")) {
                errorMessages.add("Le nom d'utilisateur ne peut contenir ni espaces ni virgules.");
            }
        }

        if (pass.isEmpty()) {
            errorMessages.add("Le champ \"Mot de passe\" est requis.");
        } else {
            if (pass.length() > 16) {
                errorMessages.add("Le mot de passe ne peut pas dépasser 16 caractères.");
            }
            if (pass.length() < 4) {
                errorMessages.add("Le mot de passe doit avoir un minimum de 4 caractères.");
            }
            if (pass.contains(" ") || pass.contains(",")) {
                errorMessages.add("Le mot de passe ne peut contenir ni espaces ni virgules.");
            }
        }

        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez corriger les erreurs suivantes :\n" + String.join("\n", errorMessages),
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE
            );
            enleverCharacteres();
            return false;
        }

        return true;
    }

    public void enleverCharacteres() {
        // Vide les champs de saisie
        utilisateurF.setText("");
        utilisateurFCreate.setText("");
        motDePasseFCreate.setText("");
    }

    public void actualiserLeTableau() {
        // Rafraîchit la table des utilisateurs
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);

        String[][] data = chargerLesNomsDUtilisateurAPartirDuFichier();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        // Gère les actions de suppression et d'ajout
        String command = actionEvent.getActionCommand();

        if (command.equals("Supprimer")) {
            String nom = utilisateurF.getText();
            if (db.aEmprunt(nom)){
                JOptionPane.showMessageDialog(
                        this,
                        "Impossible de supprimer le compte car a un emprunt en cours!",
                        "Erreur:",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try {
                if (db.utilisateurExisteSansMotDePasse(nom)) {
                    db.supprimerUtilisateur(nom);
                    actualiserLeTableau();
                    enleverCharacteres();
                    JOptionPane.showMessageDialog(
                            this,
                            "Compte supprimé avec succès!",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Ce compte n'existe pas.",
                            "Erreur:",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur système lors de la suppression.",
                        "Erreur:",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        if (command.equals("Ajouter")) {
            String nom = utilisateurFCreate.getText();
            String pass = motDePasseFCreate.getText();
            Utilisateur utilisateur1 = new Utilisateur(nom, pass);

            if (verification()) {
                try {
                    if (nom.equalsIgnoreCase("admin")) {
                        JOptionPane.showMessageDialog(this,
                                "Le nom d'utilisateur 'admin' est interdit!",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!db.utilisateurExisteSansMotDePasse(nom)) {
                        db.ajouterUtilisateur(utilisateur1);
                        actualiserLeTableau();
                        enleverCharacteres();
                        JOptionPane.showMessageDialog(
                                this,
                                "Le compte a été ajouté avec succès!",
                                "Succès",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Impossible d'ajouter ce compte car un utilisateur avec le même nom existe déjà.",
                                "Erreur:",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this,
                            "Une erreur est survenue lors de l'ajout du compte.",
                            "Erreur système:",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }
    }

    private String[][] chargerLesNomsDUtilisateurAPartirDuFichier () {
        // Charge les utilisateurs depuis un fichier
        ArrayList<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("db/utilisateurs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] rowData = line.split(",");
                if (rowData.length > 0) {
                    dataList.add(new String[]{rowData[0]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs. Fichier manquant ou inaccessible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        String[][] data = new String[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }
        return data;
    }
}
