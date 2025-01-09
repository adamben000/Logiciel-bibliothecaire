package GUI;
import LMS.Database;
import LMS.Livre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class AdminLivres extends JPanel implements ActionListener {
    // Déclaration des panneaux
    JPanel panelLivres = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    // Panneau 4 : pour supprimer un livre définitivement
    JLabel livreIDL = new JLabel("Livre ID:");
    JTextField livreIDF = new JTextField("", 10);
    JButton supprimerDefB = new JButton("Supprimer définitivement");

    // Panneau 2 : pour retirer ou ajouter une quantité de livre
    JLabel livreL = new JLabel("Livre ID:");
    JTextField livreF = new JTextField("", 10);
    JLabel livreQuantiterL = new JLabel("Quantité:");
    JTextField livreQuantiterF = new JTextField("", 10);
    JButton retirerB = new JButton("Retirer");
    JButton ajouterQB = new JButton("Ajouter");
    JButton retourB = new JButton("Retour");

    // Panneau 3 : pour ajouter un nouveau livre
    JLabel livreCL = new JLabel("Nom du livre:");
    JTextField livreCF = new JTextField("", 10);
    JLabel auteurL = new JLabel("Auteur:");
    JTextField auteurF = new JTextField("", 10);
    JLabel genreL = new JLabel("Genre:");
    JTextField genreF = new JTextField("", 10);
    JLabel quantiteL = new JLabel("Quantité:");
    JTextField quantiteF = new JTextField("", 10);
    JButton ajouterB = new JButton("Ajouter livre");

    JTable j;

    private Database db = new Database();

    public AdminLivres(CardLayout cardLayout, JPanel cardPanel, JFrame frame) {
        setLayout(new BorderLayout());

        // Chargement des données des livres depuis le fichier
        String[][] data = loadDataFromLivres();
        String[] columnNames = {"Livres", "Auteur", "Genre", "Quantité", "Disponibles", "ID"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Les cellules ne sont pas éditables
            }
        };

        j = new JTable(model); // Création du tableau
        j.getTableHeader().setReorderingAllowed(false); // Désactivation du réarrangement des colonnes
        j.setRowSelectionAllowed(false); // Désactivation de la sélection des lignes
        j.setColumnSelectionAllowed(false); // Désactivation de la sélection des colonnes
        j.setFocusable(false); // Le tableau ne prend pas le focus
        j.getTableHeader().setResizingAllowed(false); // Désactivation du redimensionnement des colonnes
        JScrollPane sp = new JScrollPane(j); // Ajout du tableau dans un panneau scrollable
        sp.setPreferredSize(new Dimension(800, 300)); // Taille préférée pour le panneau
        panelLivres.setLayout(new BorderLayout());
        panelLivres.add(sp, BorderLayout.CENTER); // Ajout du tableau au panneau

        // Panneau du bas avec trois sous-panneaux pour les actions
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

        // Configuration du panneau 2 avec une bordure
        panel2.setLayout(new GridBagLayout());
        panel2.setBorder(new LineBorder(Color.BLACK, 1)); // Bordure noire
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre les composants

        // Ajout des composants au panneau 2
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel2.add(livreL, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel2.add(livreF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel2.add(livreQuantiterL, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel2.add(livreQuantiterF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel2.add(retirerB, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel2.add(ajouterQB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel2.add(retourB, gbc);

        // Configuration du panneau 3 avec une bordure
        panel3.setLayout(new GridBagLayout());
        panel3.setBorder(new LineBorder(Color.BLACK,1));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

        // Ajout des composants au panneau 3
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        panel3.add(livreCL, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        panel3.add(livreCF, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 1;
        panel3.add(auteurL, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 1;
        panel3.add(auteurF, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 2;
        panel3.add(genreL, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 2;
        panel3.add(genreF, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 3;
        panel3.add(quantiteL, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 3;
        panel3.add(quantiteF, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 4;
        gbc1.gridwidth = 2;
        panel3.add(ajouterB, gbc1);

        // Configuration du panneau 4 avec une bordure
        panel4.setLayout(new GridBagLayout());
        panel4.setBorder(new LineBorder(Color.BLACK,1));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(10, 10, 10, 10);

        // Ajout des composants au panneau 4
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        panel4.add(livreIDL, gbc2);
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        panel4.add(livreIDF, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.gridwidth = 2;
        panel4.add(supprimerDefB, gbc2);

        // Ajout des panneaux au panneau principal
        bottomPanel.add(panel2);
        bottomPanel.add(panel3);
        bottomPanel.add(panel4);

        add(panelLivres, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        // Ajout des écouteurs d'événements pour les boutons
        ajouterB.addActionListener(this);
        supprimerDefB.addActionListener(this);
        ajouterQB.addActionListener(this);
        retirerB.addActionListener(this);

        // Action pour le bouton retour
        retourB.addActionListener(e -> {
            frame.setSize(600, 400);
            frame.setTitle("Librairie-Management");
            frame.setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "AdminOptionStack");
        });

        setVisible(true); // Rendre le panneau visible
    }

    // Fonction pour charger les données des livres depuis le fichier
    private String[][] loadDataFromLivres() {
        ArrayList<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("db/livres.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                dataList.add(rowData);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des livres. Vérifiez le fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return dataList.toArray(new String[0][]);
    }

    // Fonction pour rafraîchir le tableau après modification
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
        String[][] data = loadDataFromLivres();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    // Vérification de la validité des informations saisies
    public boolean verification() {
        String livreTitre = livreCF.getText();
        String auteur = auteurF.getText();
        String genre = genreF.getText();
        String quantite = quantiteF.getText();

        // Vérification des virgules dans les champs
        if (livreTitre.contains(",")) {
            JOptionPane.showMessageDialog(this,
                    "Le titre du livre ne doit pas contenir de virgules.",
                    "Erreur de format",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (auteur.contains(",")) {
            JOptionPane.showMessageDialog(this,
                    "Le nom de l'auteur ne doit pas contenir de virgules.",
                    "Erreur de format",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (genre.contains(",")) {
            JOptionPane.showMessageDialog(this,
                    "Le genre du livre ne doit pas contenir de virgules.",
                    "Erreur de format",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (quantite.contains(",")) {
            JOptionPane.showMessageDialog(this,
                    "La quantité ne doit pas contenir de virgules.",
                    "Erreur de format",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Gestion des actions des boutons
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        switch (command) {
            case "Retirer":
                retirerLivre();
                break;
            case "Ajouter":
                ajouterQuantite();
                break;
            case "Ajouter livre":
                ajouterNouveauLivre();
                break;
            case "Supprimer définitivement":
                supprimerLivreDefinitivement();
                break;
        }
    }

    // Fonction pour retirer un livre
    private void retirerLivre() {
        try {
            String livreId = livreF.getText();
            String quantiteStr = livreQuantiterF.getText();

            if (livreId.isEmpty() || quantiteStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir tous les champs pour retirer un livre.",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantite = Integer.parseInt(quantiteStr);
                if (quantite <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "La quantité doit être un nombre positif.",
                            "Erreur de valeur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!db.livreExiste(livreId)) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : Le livre n'existe pas.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!db.livreDisponible(livreId, quantite)) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : Quantité insuffisante pour ce livre.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                db.mettreAJourQuantiteLivre(livreId, -quantite); // Mettre à jour la quantité
                refreshTable(); // Rafraîchir le tableau
                livreF.setText(""); // Réinitialiser les champs
                livreQuantiterF.setText("");
                JOptionPane.showMessageDialog(this,
                        quantite + " exemplaire(s) retiré(s) avec succès.",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "La quantité doit être un nombre entier.",
                        "Erreur de format",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la mise à jour des données.",
                    "Erreur système",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Fonction pour ajouter une quantité de livre
    private void ajouterQuantite() {
        try {
            String livreId = livreF.getText();
            String quantiteStr = livreQuantiterF.getText();

            if (livreId.isEmpty() || quantiteStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir tous les champs pour ajouter une quantité.",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantite = Integer.parseInt(quantiteStr);
                if (quantite <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "La quantité doit être un nombre positif.",
                            "Erreur de valeur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!db.livreExiste(livreId)) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : Le livre n'existe pas.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                db.mettreAJourQuantiteLivre(livreId, quantite); // Mettre à jour la quantité
                refreshTable(); // Rafraîchir le tableau
                livreF.setText(""); // Réinitialiser les champs
                livreQuantiterF.setText("");
                JOptionPane.showMessageDialog(this,
                        quantite + " exemplaire(s) ajouté(s) avec succès.",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "La quantité doit être un nombre entier.",
                        "Erreur de format",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la mise à jour des données.",
                    "Erreur système",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    // Fonction pour ajouter un nouveau livre
    private void ajouterNouveauLivre() {
        try {
            if (verification()) {
                String livreTitre = livreCF.getText();
                String auteur = auteurF.getText();
                String genre = genreF.getText();
                try {
                    int quantite = Integer.parseInt(quantiteF.getText());
                    Livre livre = new Livre(livreTitre, auteur, genre, quantite);
                    if (quantite <= 0) {
                        JOptionPane.showMessageDialog(this,
                                "La quantité doit être un nombre positif.",
                                "Erreur de valeur",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!db.livreExiste(livre)) {
                        db.ajouterLivre(livre); // Ajouter le livre à la base de données
                        refreshTable(); // Rafraîchir le tableau
                        livreCF.setText(""); // Réinitialiser les champs
                        auteurF.setText("");
                        genreF.setText("");
                        quantiteF.setText("");
                        JOptionPane.showMessageDialog(
                                AdminLivres.this,
                                "Livre ajouté avec succès!",
                                "Succès",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                AdminLivres.this,
                                "Le livre existe déjà.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                            "La quantité doit être un nombre entier.",
                            "Erreur de format",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'ajout du livre.",
                    "Erreur système",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Fonction pour supprimer un livre définitivement
    private void supprimerLivreDefinitivement() {
        try {
            String livreId = livreIDF.getText();
            if (livreId.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez entrer un ID de livre.",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (db.livreExiste(livreId)) {
                db.supprimerLivre(livreId); // Supprimer le livre de la base de données
                refreshTable(); // Rafraîchir le tableau
                livreIDF.setText(""); // Réinitialiser les champs
                JOptionPane.showMessageDialog(this,
                        "Le livre a été supprimé avec succès.",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Le livre avec l'ID donné n'existe pas.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la suppression du livre.",
                    "Erreur système",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
