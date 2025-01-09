package GUI;
import LMS.Database;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class AdminEmprunts extends JPanel implements ActionListener {
    // Déclaration des panels
    JPanel panel1 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    GridLayout laGrid = new GridLayout(2,1);

    // Panel 2 - Champs de texte et boutons pour retourner un livre
    JLabel utilisateurL = new JLabel("Utilisateur:");
    JTextField utilisateurF = new JTextField("", 10);
    JButton retournerLeLivreB = new JButton("Retourner le livre");
    JButton retourB = new JButton("Retour");

    // Panel 3 - Champs de texte et boutons pour emprunter un livre
    JLabel utilisateurEmpruntL = new JLabel("Utilisateur:");
    JTextField utilisateurEmpruntF = new JTextField("", 10);
    JLabel LivreEmprunterL = new JLabel("Livre ID:");
    JTextField LivreEmprunterF = new JTextField("", 10);
    JButton emprunteB = new JButton("Emprunté");

    GridBagConstraints gbc = new GridBagConstraints();

    // Déclaration des tableaux pour afficher les informations
    JTable j;
    JTable j1;
    JTable j2;
    private Database db = new Database();

    public AdminEmprunts(CardLayout cardLayout, JPanel cardPanel, JFrame cadre) {
        setLayout(laGrid);

        // Charger les données des emprunts, livres et utilisateurs
        String[][] Donnees = loadDataEmprunts();
        String[] nomsDesColonnes = {"Utilisateur", "Livre emprunté", "ID", "Date d'Emprunt", "Date de retour"};
        DefaultTableModel modele = new DefaultTableModel(Donnees, nomsDesColonnes) {
            @Override
            public boolean isCellEditable(int rangee, int column) {
                return false; // Rendre les cellules non éditables
            }
        };

        // Charger les livres disponibles
        String[][] Donnees1 = chargerLesDonneesDesLivres();
        String[] nomsDesColonnes1 = {"Nom des livres", "ID", "Quantité", "Disponibles"};
        DefaultTableModel modele2 = new DefaultTableModel(Donnees1, nomsDesColonnes1) {
            @Override
            public boolean isCellEditable(int rangee, int column) {
                return false;
            }
        };

        // Charger les utilisateurs enregistrés
        String[][] Donnees2 = chargerLesNomsDUtilisateurAPartirDuFichier();
        String[] nomsDesColonnes2 = {"Utilisateurs"};
        DefaultTableModel modele3 = new DefaultTableModel(Donnees2, nomsDesColonnes2) {
            @Override
            public boolean isCellEditable(int rangee, int column) {
                return false;
            }
        };

        // Créer les tables pour afficher les données
        j = new JTable(modele);
        j1 = new JTable(modele2);
        j2 = new JTable(modele3);

        // Centrer les données des utilisateurs
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setVerticalAlignment(JLabel.CENTER);
        for (int i = 0; i < j2.getColumnCount(); i++) {
            j2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ajouter les tables dans des panneaux de défilement
        JScrollPane sp = new JScrollPane(j);
        JScrollPane sp1 = new JScrollPane(j1);
        JScrollPane sp2 = new JScrollPane(j2);

        panel1.setLayout(new BorderLayout());
        panel1.add(sp, BorderLayout.CENTER);

        panel4.setLayout(new GridLayout(2, 1));
        j1.setFillsViewportHeight(true);
        j2.setFillsViewportHeight(true);

        // Ajouter les panneaux de livres et utilisateurs dans panel4
        panel4.add(sp1);
        panel4.add(sp2);
        sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Désactiver la possibilité de réordonner et de sélectionner des cellules
        for (JTable table : new JTable[]{j, j1, j2}) {
            table.getTableHeader().setReorderingAllowed(false);
            table.setRowSelectionAllowed(false);
            table.setColumnSelectionAllowed(false);
            table.setFocusable(false);
            table.getTableHeader().setResizingAllowed(false);
        }

        // Configuration des panels pour l'interface
        JPanel topPanel = new JPanel(new GridLayout(1,2));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Ajouter les éléments dans panel2 (pour retourner un livre)
        gbc.gridx = 0; gbc.gridy = 0;
        panel2.add(utilisateurL, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panel2.add(utilisateurF, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel2.add(retournerLeLivreB, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel2.add(retourB, gbc);

        // Ajouter les éléments dans panel3 (pour emprunter un livre)
        panel3.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

        gbc1.gridx = 0; gbc1.gridy = 0;
        panel3.add(utilisateurEmpruntL, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 0;
        panel3.add(utilisateurEmpruntF, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 1;
        panel3.add(LivreEmprunterL, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 1;
        panel3.add(LivreEmprunterF, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 3; gbc1.gridwidth = 2;
        panel3.add(emprunteB, gbc1);

        // Ajouter les panels dans topPanel et bottomPanel
        add(topPanel);
        add(bottomPanel);
        topPanel.add(panel1);
        topPanel.add(panel4);
        bottomPanel.add(panel2);
        bottomPanel.add(panel3);

        // Ajouter les écouteurs d'événements pour les boutons
        retournerLeLivreB.addActionListener(this);
        emprunteB.addActionListener(this);
        retourB.addActionListener(e -> {
            cadre.setSize(600, 400);
            cadre.setTitle("Librairie-Management");
            cadre.setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "AdminOptionStack");
        });

        // Appliquer une bordure autour des panels
        panel2.setBorder(new LineBorder(Color.BLACK, 1));
        panel3.setBorder(new LineBorder(Color.BLACK, 1));
        setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (command.equals("Retourner le livre")) {
            String utilisateur = utilisateurF.getText().trim();

            if (utilisateur.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir le champ \"Utilisateur:\".",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (!db.utilisateurExisteSansMotDePasse(utilisateur)) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : L'utilisateur spécifié n'existe pas.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int joursRetard = db.enRetard(utilisateur);
                if (db.retournerLivre(utilisateur)) {
                    actualiserLeTableau();
                    actualiserLeTableau2();
                    utilisateurF.setText("");
                    LivreEmprunterF.setText("");

                    if (joursRetard > 0) {
                        JOptionPane.showMessageDialog(this,
                                "Livre retourné en retard de " + joursRetard + " jours",
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Livre retourné avec succès !",
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : Aucun emprunt trouvé pour cet utilisateur.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur système lors du retour du livre.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        else if (command.equals("Emprunté")) {
            String utilisateur = utilisateurEmpruntF.getText().trim();
            String livreId = LivreEmprunterF.getText().trim();

            if (utilisateur.isEmpty() || livreId.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir tous les champs : Utilisateur et Livre ID.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (!db.utilisateurExisteSansMotDePasse(utilisateur)) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : L'utilisateur spécifié n'existe pas.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (db.aEmprunt(utilisateur)) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : Cet utilisateur a déjà un emprunt actif.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!db.livreExiste(livreId)) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : Le livre spécifié n'existe pas.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!db.livreDisponible(livreId, 1)) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : Le livre spécifié n'est pas disponible.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (db.emprunterLivre(utilisateur, livreId)) {
                    actualiserLeTableau();
                    actualiserLeTableau2();
                    utilisateurEmpruntF.setText("");
                    LivreEmprunterF.setText("");
                    JOptionPane.showMessageDialog(this,
                            "Livre emprunté avec succès !",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erreur inattendue lors de l'emprunt.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur système lors de l'emprunt.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Charger les données des emprunts depuis un fichier
    private String[][] loadDataEmprunts() {
        ArrayList<String[]> listeDesDonnes = new ArrayList<>();
        try (BufferedReader lecteur = new BufferedReader(new FileReader("db/emprunts.txt"))) {
            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
                String[] donneesDeLigne = ligne.split(",");
                String[] empruntData = {donneesDeLigne[0], donneesDeLigne[1], donneesDeLigne[2], donneesDeLigne[3], donneesDeLigne[4]};
                listeDesDonnes.add(empruntData);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs. Fichier manquant ou inaccessible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        String[][] Donnees = new String[listeDesDonnes.size()][];
        for (int i = 0; i < listeDesDonnes.size(); i++) {
            Donnees[i] = listeDesDonnes.get(i);
        }
        return Donnees;
    }

    // Charger les livres depuis un fichier
    private String[][] chargerLesDonneesDesLivres() {
        ArrayList<String[]> listeDesDonnes = new ArrayList<>();
        try (BufferedReader lecteur = new BufferedReader(new FileReader("db/livres.txt"))) {
            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
                String[] donneesDeLigne = ligne.split(",");
                String[] donneesLivres = {donneesDeLigne[0], donneesDeLigne[5],donneesDeLigne[3],donneesDeLigne[4]};
                listeDesDonnes.add(donneesLivres);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs. Fichier manquant ou inaccessible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        String[][] Donnees = new String[listeDesDonnes.size()][];
        for (int i = 0; i < listeDesDonnes.size(); i++) {
            Donnees[i] = listeDesDonnes.get(i);
        }
        return Donnees;
    }

    // Charger les noms des utilisateurs depuis un fichier
    private String[][] chargerLesNomsDUtilisateurAPartirDuFichier() {
        ArrayList<String[]> listeDesDonnes = new ArrayList<>();
        try (BufferedReader lecteur = new BufferedReader(new FileReader("db/utilisateurs.txt"))) {
            String ligne;
            while ((ligne = lecteur.readLine()) != null) {

                String[] donneesDeLigne = ligne.split(",");
                if (donneesDeLigne.length > 0) {
                    listeDesDonnes.add(new String[]{donneesDeLigne[0]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs. Fichier manquant ou inaccessible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }


        String[][] Donnees = new String[listeDesDonnes.size()][1];
        for (int i = 0; i < listeDesDonnes.size(); i++) {
            Donnees[i] = listeDesDonnes.get(i);
        }
        return Donnees;
    }

    // Actualiser les tables pour afficher les dernières données
    public void actualiserLeTableau() {
        DefaultTableModel modele = (DefaultTableModel) j.getModel();
        modele.setRowCount(0);

        String[][] Donnees = loadDataEmprunts();
        for (String[] rangee : Donnees) {
            modele.addRow(rangee);
        }
    }

    public void actualiserLeTableau2() {
        DefaultTableModel modele2 = (DefaultTableModel) j1.getModel();
        modele2.setRowCount(0);

        String[][] Donnees1 = chargerLesDonneesDesLivres();
        for (String[] rangee : Donnees1) {
            modele2.addRow(rangee);
        }
    }

    public void actualiserLeTableau3() {
        DefaultTableModel modele2 = (DefaultTableModel) j2.getModel();
        modele2.setRowCount(0);

        String[][] Donnees1 = chargerLesNomsDUtilisateurAPartirDuFichier();
        for (String[] rangee : Donnees1) {
            modele2.addRow(rangee);
        }
    }

}