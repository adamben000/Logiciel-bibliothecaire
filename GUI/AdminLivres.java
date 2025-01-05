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
    JPanel panelLivres = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    // Panel 4
    JLabel livreIDL = new JLabel("Livre ID:");
    JTextField livreIDF = new JTextField("", 10);
    JButton supprimerDefB = new JButton("Supprimer définitivement");

    // Panel 2
    JLabel livreL = new JLabel("Livre ID:");
    JTextField livreF = new JTextField("", 10);
    JLabel livreQuantiterL = new JLabel("Quantité:");
    JTextField livreQuantiterF = new JTextField("", 10);
    JButton retirerB = new JButton("Retirer");
    JButton ajouterQB = new JButton("Ajouter");
    JButton retourB = new JButton("Retour");

    // Panel 3
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

        String[][] data = loadDataFromLivres();
        String[] columnNames = {"Livres", "Auteur", "Genre", "Quantité", "Disponibles", "ID"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        j = new JTable(model);
        j.getTableHeader().setReorderingAllowed(false);
        j.setRowSelectionAllowed(false);
        j.setColumnSelectionAllowed(false);
        j.setFocusable(false);
        j.getTableHeader().setResizingAllowed(false);
        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(800, 300));
        panelLivres.setLayout(new BorderLayout());
        panelLivres.add(sp, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

        // Panel 2 setup with border
        panel2.setLayout(new GridBagLayout());
        panel2.setBorder(new LineBorder(Color.BLACK, 1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

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

        // Panel 3 setup with border
        panel3.setLayout(new GridBagLayout());
        panel3.setBorder(new LineBorder(Color.BLACK,1));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

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

        // Panel 4 setup with border
        panel4.setLayout(new GridBagLayout());
        panel4.setBorder(new LineBorder(Color.BLACK,1));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(10, 10, 10, 10);

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

        bottomPanel.add(panel2);
        bottomPanel.add(panel3);
        bottomPanel.add(panel4);

        add(panelLivres, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        ajouterB.addActionListener(this);
        supprimerDefB.addActionListener(this);
        ajouterQB.addActionListener(this);
        retirerB.addActionListener(this);

        retourB.addActionListener(e -> {
            frame.setSize(600, 400);
            frame.setTitle("Librairie-Management");
            frame.setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "AdminOptionStack");
        });

        setVisible(true);
    }

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

    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);
        String[][] data = loadDataFromLivres();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    public boolean verification() {
        String livreTitre = livreCF.getText();
        String auteur = auteurF.getText();
        String genre = genreF.getText();
        String quantite = quantiteF.getText();

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

                db.mettreAJourQuantiteLivre(livreId, -quantite);
                refreshTable();
                livreF.setText("");
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

                db.mettreAJourQuantiteLivre(livreId, quantite);
                refreshTable();
                livreF.setText("");
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


    private void ajouterNouveauLivre() {
        try {
            if (verification()) {
                String livreTitre = livreCF.getText();
                String auteur = auteurF.getText();
                String genre = genreF.getText();
                try {
                    int quantite = Integer.parseInt(quantiteF.getText());
                    Livre livre = new Livre(livreTitre, auteur, genre, quantite);
                    if (!db.livreExiste(livre)) {
                        db.ajouterLivre(livre);
                        refreshTable();
                        livreCF.setText("");
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

    private void supprimerLivreDefinitivement() {
        try {
            String livreId = livreIDF.getText();
            if (livreId.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir le champ \"Livre ID:\".",
                        "Erreur de saisie",
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

            if (!db.supprimerLivreDefinitivementVerification(livreId)) {
                JOptionPane.showMessageDialog(this,
                        "Erreur : Ce livre ne peut pas être supprimé. Assurez-vous qu'il n'est pas en cours d'emprunt et que sa quantité est nulle.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            db.supprimerLivre(livreId);
            refreshTable();
            livreIDF.setText("");
            JOptionPane.showMessageDialog(this,
                    "Le livre a été supprimé avec succès.",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la suppression du livre.",
                    "Erreur système",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}